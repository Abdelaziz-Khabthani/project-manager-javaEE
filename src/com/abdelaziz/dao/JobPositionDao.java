package com.abdelaziz.dao;

import com.abdelaziz.model.JobPosition;

public interface JobPositionDao extends GenericDao<JobPosition, Long> {
	public JobPosition findByName(String name);
}
