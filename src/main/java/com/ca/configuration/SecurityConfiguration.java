package com.ca.configuration;

import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.ca.filter.JwtFilter;
import com.ca.filter.SessionFilter;
import com.ca.security.ActiveUserStore;
import com.ca.security.LoggedUser;
import com.ca.service.CustomUserDetailService;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	
	String[] requestNotToBeAuthenticated=new String[] {
			"/authentication","/home","/login","/h2-console/**","/user/registration",
			"/user/regitrationConfirm","/user/resetPassword","/user/changePassword","/user/savePassword",
			"/user/resendRegistrationToken","/js/**","/css/**"
	};
	
	private AntPathRequestMatcher[] requestMatchers = {
			new AntPathRequestMatcher("/authentication"),new AntPathRequestMatcher("/home"),
			new AntPathRequestMatcher("/login"),new AntPathRequestMatcher("/h2-console/**")
			
	};
	
	private Pattern allowedMethods = Pattern.compile("^GET$");
	
	@Autowired
	private CustomUserDetailService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private SessionFilter sessionFilter;
	
	@Autowired
	private SessionRegistryImpl sessionRegistryDetails;
	
	@Autowired
	private PersistentTokenRepository persistentTokenRepository;
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ActiveUserStore activeUserStore(){
	    return new ActiveUserStore();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Autowired
    private ActiveUserStore activeUserStore;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.headers().frameOptions().disable().and()
			.addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class)
			.csrf().requireCsrfProtectionMatcher(csrfProtestionMatcher)
			.and()
				.authorizeRequests().antMatchers(requestNotToBeAuthenticated).permitAll()
				.anyRequest().authenticated()	
			.and()
				.formLogin()
					.loginPage("/home")
					.loginProcessingUrl("/login")
					.successHandler(authSuccess)
					.failureHandler(authFailure)
			.and()
				.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessHandler(logoutSuccessHandeller)
				.logoutSuccessUrl("/logout-success")
				.deleteCookies("JSESSIONID")
			.and()
				.rememberMe().rememberMeParameter("remember-me").tokenValiditySeconds(86400)
				.tokenRepository(persistentTokenRepository).userDetailsService(userDetailsService)
			.and()
				.sessionManagement()
					.sessionFixation().migrateSession()
					.invalidSessionUrl("/home") 
					.maximumSessions(1)
					.maxSessionsPreventsLogin(true)
					.expiredUrl("/home")
					.sessionRegistry(sessionRegistryDetails);
		
	}
	
	RequestMatcher csrfProtestionMatcher=(request)->{
		if (allowedMethods.matcher(request.getMethod()).matches()) {
		      return false;
		    }
		// If the request match one url the CSFR protection will be disabled
		 for (AntPathRequestMatcher rm : requestMatchers) {
	          if (rm.matches(request)) { 
	        	  return false; 
	        	  }
	        }
		 return true;
	};
	
	AuthenticationFailureHandler authFailure= (request,response, exception)->{
				
		        if (exception.getMessage().equalsIgnoreCase("blocked")) {
		        	 System.out.println("blocked");
		        }else if(exception instanceof SessionAuthenticationException) {
		        	System.out.println("maximum 1 session allowed");
		        }else if(exception instanceof DisabledException) {
		        	System.out.println("User is disabled");
		        }
		        
				exception.printStackTrace();
			};
			
	AuthenticationSuccessHandler authSuccess=(request,response,authentication)->{
				
				HttpSession session = request.getSession(false);
		        if (session != null) {
		            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
		            session.setAttribute("user", user);
		        }
				response.sendRedirect(request.getContextPath()+"/");
			};
			
	LogoutSuccessHandler logoutSuccessHandeller=(request,response,authentication)->{
				
				HttpSession session = request.getSession();
		        if (session != null){
		            session.removeAttribute("user");
		        }
		        
				System.out.println("logout success");
				response.sendRedirect(request.getContextPath()+"/logout-success");
			};
}
