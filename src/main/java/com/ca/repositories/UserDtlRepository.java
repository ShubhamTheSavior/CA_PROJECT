package com.ca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ca.entitis.UserDtlEntity;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Repository
public interface UserDtlRepository extends JpaRepository<UserDtlEntity, Integer>{

	UserDtlEntity findByUsername(String username);

	UserDtlEntity getUserDtlEntityByPasswordResetToken(String token);

}
