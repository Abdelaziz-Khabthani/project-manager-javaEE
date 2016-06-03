package com.abdelaziz.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PROJECT_TYPE", uniqueConstraints = @UniqueConstraint(columnNames = "PROJECT_TYPE_LABEL"))
public class ProjectType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long projectTypeId;
	private String projectTypeLabel;
	private Set<Project> projects = new HashSet<Project>(0);

	public ProjectType() {
	}

	public ProjectType(String projectTypeLabel, Set<Project> projects) {
		this.projectTypeLabel = projectTypeLabel;
		this.projects = projects;
	}

	public ProjectType(String projectTypeLabel) {
		this.projectTypeLabel = projectTypeLabel;
	}

	@Id
	@Column(name = "PROJECT_TYPE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getProjectTypeId() {
		return this.projectTypeId;
	}

	public void setProjectTypeId(long projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	@Column(name = "PROJECT_TYPE_LABEL", unique = true, nullable = false, length = 20)
	public String getProjectTypeLabel() {
		return this.projectTypeLabel;
	}

	public void setProjectTypeLabel(String projectTypeLabel) {
		this.projectTypeLabel = projectTypeLabel;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "projectType")
	public Set<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

}
