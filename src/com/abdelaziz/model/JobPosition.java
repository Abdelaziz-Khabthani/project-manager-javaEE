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
@Table(name = "JOB_POSITION", uniqueConstraints = @UniqueConstraint(columnNames = "JOB_POSITON_LABEL"))
public class JobPosition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long jobPositionId;
	private String jobPositonLabel;
	private Set<Employee> employees = new HashSet<Employee>(0);

	public JobPosition() {
	}

	public JobPosition(String jobPositonLabel) {
		this.jobPositonLabel = jobPositonLabel;
	}

	public JobPosition(String jobPositonLabel, Set<Employee> employees) {
		this.jobPositonLabel = jobPositonLabel;
		this.employees = employees;
	}

	@Id
	@Column(name = "JOB_POSITION_ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getJobPositionId() {
		return this.jobPositionId;
	}

	public void setJobPositionId(long jobPositionId) {
		this.jobPositionId = jobPositionId;
	}

	@Column(name = "JOB_POSITON_LABEL", unique = true, nullable = false, length = 20)
	public String getJobPositonLabel() {
		return this.jobPositonLabel;
	}

	public void setJobPositonLabel(String jobPositonLabel) {
		this.jobPositonLabel = jobPositonLabel;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "jobPosition")
	public Set<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof JobPosition) ? jobPositionId == ((JobPosition) other).jobPositionId
				: false;
	}

	@Override
	public int hashCode() {
		return (int) jobPositionId;
	}

	@Override
	public String toString() {
		return String.format("JobPosition[%d, %s]", jobPositionId,
				jobPositonLabel);
	}
}
