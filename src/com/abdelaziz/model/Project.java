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
@Table(name = "PROJECT")
public class Project implements java.io.Serializable, Comparable<Project> {

	private static final long serialVersionUID = 1L;
	private long projectId;
	private ProjectType projectType;
	private String projectName;
	private String projectDescription;
	private Date projectStartDate;
	private Date projectEndDate;
	private Set<Employee> employees = new HashSet<Employee>(0);

	public Project() {
	}

	public Project(long projectId, ProjectType projectType, String projectName) {
		this.projectId = projectId;
		this.projectType = projectType;
		this.projectName = projectName;
	}

	public Project(long projectId, ProjectType projectType, String projectName,
			String projectDescription, Date projectStartDate,
			Date projectEndDate, Set<Employee> employees) {
		this.projectId = projectId;
		this.projectType = projectType;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.employees = employees;
	}

	@Id
	@Column(name = "PROJECT_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROJECT_TYPE", nullable = false)
	public ProjectType getProjectType() {
		return this.projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	@Column(name = "PROJECT_NAME", nullable = false, length = 50)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "PROJECT_DESCRIPTION")
	public String getProjectDescription() {
		return this.projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PROJECT_START_DATE", length = 7)
	public Date getProjectStartDate() {
		return this.projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PROJECT_END_DATE", length = 7)
	public Date getProjectEndDate() {
		return this.projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PROJECT_ASSIGNEMENT", joinColumns = { @JoinColumn(name = "PROJECT_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "EMPLOYEE_CIN", nullable = false, updatable = false) })
	public Set<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Project))
			return false;
		Project other = (Project) o;
		return this.projectId == other.projectId;
	}

	@Override
	public int hashCode() {
		return (int) projectId;
	}

	@Override
	public int compareTo(Project o) {
		if (getProjectEndDate() == null || o.getProjectEndDate() == null)
			return 0;
		return getProjectEndDate().compareTo(o.getProjectEndDate());
	}

}
