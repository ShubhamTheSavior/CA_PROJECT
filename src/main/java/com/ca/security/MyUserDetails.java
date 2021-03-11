package com.ca.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ca.entitis.Roles;
import com.ca.entitis.UserDtlEntity;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private UserDtlEntity userDtl;
	
	public MyUserDetails(UserDtlEntity userDtl) {
		super();
		this.userDtl = userDtl;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Roles> roles=userDtl.getRoles();
		List<SimpleGrantedAuthority> authorities=
				new ArrayList<SimpleGrantedAuthority>();
		for (Roles role:roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return userDtl.getPassword();
	}

	@Override
	public String getUsername() {
		return userDtl.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return userDtl.isEnabled();
	}

	public UserDtlEntity getUserDtl() {
		return userDtl;
	}

	public void setUserDtl(UserDtlEntity userDtl) {
		this.userDtl = userDtl;
	}
	
	

}
