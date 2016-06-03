package com.abdelaziz.dao;

import java.util.Date;
import java.util.List;

import com.abdelaziz.model.Project;

public interface ProjectDao extends GenericDao<Project, Long> {
	
	public List<Project> getCurrentProjects();

	public List<Project> findByName(String name, boolean onlyLiveProjects);

	public List<Project> findByStartDate(Date date, boolean onlyLiveProjects);

	public List<Project> findByEndDate(Date date, boolean onlyLiveProjects);

	public List<Project> findByProjectTypeLabel(String label, boolean onlyLiveProjects);
}
