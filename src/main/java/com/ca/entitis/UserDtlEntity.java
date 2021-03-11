package com.ca.entitis;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Entity
@Table(name = "USER_DTL")
public class UserDtlEntity {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String username;
	
	private String password;
	
	private boolean isEnabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE",
	joinColumns = @JoinColumn(name="USER_ID"),
	inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
	private List<Roles> roles=new ArrayList<Roles>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private VerificationToken verificationToken;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private PasswordResetToken passwordResetToken;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void addRoles(Roles roles) {
		this.roles.add(roles);
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public VerificationToken getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
	}

	public PasswordResetToken getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	
	
}
