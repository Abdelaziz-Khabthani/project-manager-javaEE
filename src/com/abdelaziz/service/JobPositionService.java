package com.abdelaziz.service;

import java.util.List;

import com.abdelaziz.model.JobPosition;

public interface JobPositionService {
	public void addJobPosition(JobPosition jobPosition);

	public void updateJobPosition(JobPosition jobPosition);

	public JobPosition findJobPositionById(long id);

	public List<JobPosition> findAllJobPositions();

	public void deleteJobPosition(JobPosition jobPosition);

	public void deleteJobPositionById(long id);

	public JobPosition findByJobPositionName(String name);
}
