package com.ca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ca.entitis.Roles;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {

	Roles findByRoleName(String role);
	
}
