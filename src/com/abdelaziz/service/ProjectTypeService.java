package com.abdelaziz.service;

import java.util.List;

import com.abdelaziz.model.ProjectType;

public interface ProjectTypeService {
	public void addProjectType(ProjectType projectType);

	public void updateProjectType(ProjectType projectType);

	public ProjectType findProjectTypeById(long id);

	public List<ProjectType> findAllProjectTypes();

	public void deleteProjectType(ProjectType projectType);

	public void deleteProjectTypeId(long id);

	public ProjectType findProjectTypeByNAme(String name);
}
