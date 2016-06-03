package com.abdelaziz.service.impl;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.abdelaziz.dao.JobPositionDao;
import com.abdelaziz.model.JobPosition;
import com.abdelaziz.service.JobPositionService;

public class JobPositionServiceImpl implements JobPositionService {

	private JobPositionDao jobPositionDao;

	public void setJobPositionDao(JobPositionDao jobPositionDao) {
		this.jobPositionDao = jobPositionDao;
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void addJobPosition(JobPosition jobPosition) {
		jobPositionDao.create(jobPosition);
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void updateJobPosition(JobPosition jobPosition) {
		jobPositionDao.update(jobPosition);
	}

	@Override
	public JobPosition findJobPositionById(long id) {
		return jobPositionDao.findById(id);
	}

	@Override
	public List<JobPosition> findAllJobPositions() {
		return jobPositionDao.findAll();
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void deleteJobPosition(JobPosition jobPosition) {
		jobPositionDao.delete(jobPosition);
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void deleteJobPositionById(long id) {
		jobPositionDao.deleteById(id);
	}

	@Override
	public JobPosition findByJobPositionName(String name) {
		return jobPositionDao.findByName(name);
	}

}
