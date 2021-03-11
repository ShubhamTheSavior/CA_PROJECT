package com.ca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ca.entitis.PersistentLogins;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Repository
public interface PersistentLoginsRepo extends JpaRepository<PersistentLogins, String>{

	void deleteByUsername(String username);
	
}
