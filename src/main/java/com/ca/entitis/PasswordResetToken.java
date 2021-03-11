package com.ca.entitis;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Entity
public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = UserDtlEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private UserDtlEntity user;
	
	private Date expiryDate;
	
	
	
	
	public PasswordResetToken() {
		super();
	}

	public PasswordResetToken(String token, UserDtlEntity user) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDtlEntity getUser() {
		return user;
	}

	public void setUser(UserDtlEntity user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
