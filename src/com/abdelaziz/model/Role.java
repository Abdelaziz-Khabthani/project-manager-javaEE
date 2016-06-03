package com.abdelaziz.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ROLE", uniqueConstraints = @UniqueConstraint(columnNames = "ROLE_LABEL"))
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long roleId;
	private String roleLabel;
	private Set<Users> userses = new HashSet<Users>(0);

	public Role() {
	}

	public Role(String roleLabel) {
		this.roleLabel = roleLabel;
	}

	public Role(String roleLabel, Set<Users> userses) {
		this.roleLabel = roleLabel;
		this.userses = userses;
	}

	@Id
	@Column(name = "ROLE_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE_LABEL", unique = true, nullable = false, length = 20)
	public String getRoleLabel() {
		return this.roleLabel;
	}

	public void setRoleLabel(String roleLabel) {
		this.roleLabel = roleLabel;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

}
