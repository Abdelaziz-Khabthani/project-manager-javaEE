package com.abdelaziz.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USERS", uniqueConstraints = {
		@UniqueConstraint(columnNames = "USERS_NAME"),
		@UniqueConstraint(columnNames = "USERS_EMAIL") })
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long usersId;
	private String usersName;
	private String usersPassword;
	private String usersFullName;
	private String usersEmail;
	private Set<Role> roles = new HashSet<Role>(0);

	public Users() {
	}

	public Users(String usersName, String usersPassword, String usersFullName,
			String usersEmail) {
		this.usersName = usersName;
		this.usersPassword = usersPassword;
		this.usersFullName = usersFullName;
		this.usersEmail = usersEmail;
	}

	public Users(String usersName, String usersPassword, String usersFullName,
			String usersEmail, Set<Role> roles) {
		this.usersName = usersName;
		this.usersPassword = usersPassword;
		this.usersFullName = usersFullName;
		this.usersEmail = usersEmail;
		this.roles = roles;
	}

	@Id
	@Column(name = "USERS_ID", unique = true, nullable = false, precision = 11, scale = 0)
	public long getUsersId() {
		return this.usersId;
	}

	public void setUsersId(long usersId) {
		this.usersId = usersId;
	}

	@Column(name = "USERS_NAME", unique = true, nullable = false, length = 20)
	public String getUsersName() {
		return this.usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}

	@Column(name = "USERS_PASSWORD", nullable = false, length = 20)
	public String getUsersPassword() {
		return this.usersPassword;
	}

	public void setUsersPassword(String usersPassword) {
		this.usersPassword = usersPassword;
	}

	@Column(name = "USERS_FULL_NAME", nullable = false, length = 20)
	public String getUsersFullName() {
		return this.usersFullName;
	}

	public void setUsersFullName(String usersFullName) {
		this.usersFullName = usersFullName;
	}

	@Column(name = "USERS_EMAIL", unique = true, nullable = false, length = 20)
	public String getUsersEmail() {
		return this.usersEmail;
	}

	public void setUsersEmail(String usersEmail) {
		this.usersEmail = usersEmail;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USERS_ROLE", joinColumns = { @JoinColumn(name = "USERS_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
