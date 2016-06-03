package com.abdelaziz.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.abdelaziz.dao.ProjectTypeDao;
import com.abdelaziz.model.ProjectType;
import com.abdelaziz.service.ProjectTypeService;

public class ProjectTypeServiceImpl implements ProjectTypeService, Serializable {

	private static final long serialVersionUID = 1L;
	ProjectTypeDao projectTypeDao;

	public void setProjectTypeDao(ProjectTypeDao projectTypeDao) {
		this.projectTypeDao = projectTypeDao;
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void addProjectType(ProjectType projectType) {
		projectTypeDao.create(projectType);
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void updateProjectType(ProjectType projectType) {
		projectTypeDao.update(projectType);
	}

	@Override
	public ProjectType findProjectTypeById(long id) {
		return projectTypeDao.findById(id);
	}

	@Override
	public List<ProjectType> findAllProjectTypes() {
		return projectTypeDao.findAll();
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void deleteProjectType(ProjectType projectType) {
		projectTypeDao.delete(projectType);
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void deleteProjectTypeId(long id) {
		projectTypeDao.deleteById(id);
	}

	@Override
	public ProjectType findProjectTypeByNAme(String name) {
		return projectTypeDao.findByName(name);
	}
}
