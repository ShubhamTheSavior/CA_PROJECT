package com.ca.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Component;

import com.ca.entitis.UserDtlEntity;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Component
public class SessionRegistryDetails extends SessionRegistryImpl {

	
	private ConcurrentMap<String, Set<String>> sessionsUsedByUser=
			new ConcurrentHashMap<>();;
	private Map<String, SessionInformation> sessionIds1=
			new HashMap<String, SessionInformation>();

	@Override
	public List<Object> getAllPrincipals() {
		return new ArrayList<>(sessionsUsedByUser.keySet());
	}

	@Override
	public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
		MyUserDetails userPrincipal=(MyUserDetails) principal;
		UserDtlEntity userEntity=null;
		if(userPrincipal==null) {
			return Collections.emptyList();
		}
		userEntity=userPrincipal.getUserDtl();
		final Set<String> sessionsUsedByPrincipal = sessionsUsedByUser.get(userEntity.getUsername());
		
		if (sessionsUsedByPrincipal == null) {
			return Collections.emptyList();
		}
	
		List<SessionInformation> list = new ArrayList<>(
				sessionsUsedByPrincipal.size());
		
		for (String sessionId : sessionsUsedByPrincipal) {
			SessionInformation sessionInformation = getSessionInformation(sessionId);

			if (sessionInformation == null) {
				continue;
			}

			if (includeExpiredSessions || !sessionInformation.isExpired()) {
				list.add(sessionInformation);
			}
		}

		return list;
	}

	@Override
	public SessionInformation getSessionInformation(String sessionId) {
		return sessionIds1.get(sessionId);
	}

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		String sessionId = event.getId();
		removeSessionInformation(sessionId);
	}

	@Override
	public void refreshLastRequest(String sessionId) {
		SessionInformation info = getSessionInformation(sessionId);

		if (info != null) {
			info.refreshLastRequest();
		}
	}

	@Override
	public void registerNewSession(String sessionId, Object principal) {
		

		MyUserDetails userPrincipal=(MyUserDetails) principal;
		
		UserDtlEntity userEntity=null;
		if(userPrincipal!=null) {
			userEntity=userPrincipal.getUserDtl();
		}
		
		if (getSessionInformation(sessionId) != null) {
			removeSessionInformation(sessionId);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Registering session " + sessionId + ", for principal "
					+ principal);
		}
		
		sessionIds1.put(sessionId,
				new SessionInformation(principal, sessionId, new Date()));
		
		Set<String> sessionsUsedByPrincipal = sessionsUsedByPrincipal(userPrincipal);

		if (sessionsUsedByPrincipal == null && userPrincipal.getUsername()!=null) {
			sessionsUsedByPrincipal = new CopyOnWriteArraySet<>();
			Set<String> prevSessionsUsedByPrincipal = sessionsUsedByUser.putIfAbsent(
					userEntity.getUsername(),sessionsUsedByPrincipal);
			if (prevSessionsUsedByPrincipal != null) {
				sessionsUsedByPrincipal = prevSessionsUsedByPrincipal;
			}
		}
		
		sessionsUsedByPrincipal.add(sessionId);
		/*
		 * // sessionsUsedByUser.compute(principal, (key, sessionsUsedByPrincipal) -> {
		 * // if (sessionsUsedByPrincipal == null) { // sessionsUsedByPrincipal = new
		 * CopyOnWriteArraySet<>(); // } // sessionsUsedByPrincipal.add(sessionId); //
		 * // if (logger.isTraceEnabled()) { // logger.trace("Sessions used by '" +
		 * principal + "' : " // + sessionsUsedByPrincipal); // } // return
		 * sessionsUsedByPrincipal; // });
		 */
	}
	
	public Set<String> sessionsUsedByPrincipal(MyUserDetails userDetails){
		Set<String> sessionsUsedByPrincipal=null;
		if(userDetails!=null) {
			sessionsUsedByPrincipal = sessionsUsedByUser.get(userDetails.getUsername());
		}
		return sessionsUsedByPrincipal;
	}

	@Override
	public void removeSessionInformation(String sessionId) {
		SessionInformation info = getSessionInformation(sessionId);
		if (info == null) {
			return;
		}
		MyUserDetails userPrincipal=(MyUserDetails) info.getPrincipal();
		sessionIds1.remove(sessionId);
		Set<String> sessionsUsedByPrincipal = sessionsUsedByPrincipal(userPrincipal);
		if (sessionsUsedByPrincipal == null) {
			return;
		}
		sessionsUsedByPrincipal.remove(sessionId);
		if (sessionsUsedByPrincipal.isEmpty()) {
			sessionIds1.remove(userPrincipal.getUsername());
		}
	}

}
