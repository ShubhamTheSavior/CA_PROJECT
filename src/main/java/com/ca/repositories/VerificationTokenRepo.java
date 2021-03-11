package com.ca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ca.entitis.UserDtlEntity;
import com.ca.entitis.VerificationToken;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);
	 
    VerificationToken findByUser(UserDtlEntity user);

	

}
