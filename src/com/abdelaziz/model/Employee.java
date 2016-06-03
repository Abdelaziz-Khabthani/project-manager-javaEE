package com.abdelaziz.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String employeeCin;
	private JobPosition jobPosition;
	private String employeeName;
	private String employeeLastname;
	private Date employeeBirthdate;
	private String employeeEmail;
	private String employeeProfilePicture;
	private Set<Project> projects = new HashSet<Project>(0);

	public Employee() {
	}

	public Employee(String employeeCin, String employeeName,
			String employeeLastname, Date employeeBirthdate,
			String employeeEmail) {
		this.employeeCin = employeeCin;
		this.employeeName = employeeName;
		this.employeeLastname = employeeLastname;
		this.employeeBirthdate = employeeBirthdate;
		this.employeeEmail = employeeEmail;
	}

	public Employee(String employeeCin, JobPosition jobPosition,
			String employeeName, String employeeLastname,
			Date employeeBirthdate, String employeeEmail, Set<Project> projects) {
		this.employeeCin = employeeCin;
		this.jobPosition = jobPosition;
		this.employeeName = employeeName;
		this.employeeLastname = employeeLastname;
		this.employeeBirthdate = employeeBirthdate;
		this.employeeEmail = employeeEmail;
		this.projects = projects;
	}

	@Id
	@Column(name = "EMPLOYEE_CIN", unique = true, nullable = false, length = 10)
	public String getEmployeeCin() {
		return this.employeeCin;
	}

	public void setEmployeeCin(String employeeCin) {
		this.employeeCin = employeeCin;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_JOB_POSITION")
	public JobPosition getJobPosition() {
		return this.jobPosition;
	}

	public void setJobPosition(JobPosition jobPosition) {
		this.jobPosition = jobPosition;
	}

	@Column(name = "EMPLOYEE_NAME", nullable = false, length = 20)
	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Column(name = "EMPLOYEE_LASTNAME", nullable = false, length = 20)
	public String getEmployeeLastname() {
		return this.employeeLastname;
	}

	public void setEmployeeLastname(String employeeLastname) {
		this.employeeLastname = employeeLastname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EMPLOYEE_BIRTHDATE", nullable = false, length = 7)
	public Date getEmployeeBirthdate() {
		return this.employeeBirthdate;
	}

	public void setEmployeeBirthdate(Date employeeBirthdate) {
		this.employeeBirthdate = employeeBirthdate;
	}

	@Column(name = "EMPLOYEE_EMAIL", nullable = false)
	public String getEmployeeEmail() {
		return this.employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	@Column(name = "EMPLOYEE_PROFILE_PICTURE", length = 50)
	public String getEmployeeProfilePicture() {
		return this.employeeProfilePicture;
	}

	public void setEmployeeProfilePicture(String employeeProfilePicture) {
		this.employeeProfilePicture = employeeProfilePicture;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PROJECT_ASSIGNEMENT", joinColumns = { @JoinColumn(name = "EMPLOYEE_CIN", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PROJECT_ID", nullable = false, updatable = false) })
	public Set<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Employee))
			return false;
		Employee other = (Employee) o;
		return this.employeeCin.equals(other.employeeCin);
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(employeeCin);
	}

}
