package com.ca.dao;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import com.ca.entitis.PersistentLogins;
import com.ca.repositories.PersistentLoginsRepo;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Repository("persistentTokenRepository")
@Transactional
public class PersistentTokenDaoImp implements PersistentTokenRepository {

	@Autowired
	private PersistentLoginsRepo persistentLoginsRepo;
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		PersistentLogins logins = new PersistentLogins();
		logins.setUsername(token.getUsername());
	    logins.setSeries(token.getSeries());
	    logins.setToken(token.getTokenValue());
	    logins.setLastUsed(token.getDate());
	    persistentLoginsRepo.save(logins);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		
		Optional<PersistentLogins> login=persistentLoginsRepo.findById(series);
		PersistentLogins logins= login.get();
	    logins.setToken(tokenValue);
	    logins.setLastUsed(lastUsed);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		Optional<PersistentLogins> login=persistentLoginsRepo.findById(seriesId);
		if (login.isPresent()) {
			PersistentLogins logins= login.get();
		      return new PersistentRememberMeToken(logins.getUsername(), 
		          logins.getSeries(), logins.getToken(),logins.getLastUsed());
		    }
		 return null;
	}

	@Override
	public void removeUserTokens(String username) {
		persistentLoginsRepo.deleteByUsername(username);
	}

}
