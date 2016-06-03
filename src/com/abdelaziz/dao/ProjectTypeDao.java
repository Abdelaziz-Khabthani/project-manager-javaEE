package com.abdelaziz.dao;

import com.abdelaziz.model.ProjectType;

public interface ProjectTypeDao extends GenericDao<ProjectType, Long> {

	public ProjectType findByName(String name);
}
