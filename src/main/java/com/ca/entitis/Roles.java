package com.ca.entitis;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@Entity
@Table(name = "ROLES")
public class Roles {

	@Id
	@GeneratedValue
	private Integer roleId;
	
	private String roleName;
	
	@ManyToMany(mappedBy = "roles")
	private List<UserDtlEntity> ueres=new ArrayList<UserDtlEntity>();

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<UserDtlEntity> getUeres() {
		return ueres;
	}

	public void addUeres(UserDtlEntity ueres) {
		this.ueres.add(ueres);
	}

}
