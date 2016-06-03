package com.abdelaziz.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.abdelaziz.dao.ProjectDao;
import com.abdelaziz.model.Project;
import com.abdelaziz.service.ProjectService;

public class ProjectServiceImpl implements ProjectService, Serializable {

	private static final long serialVersionUID = 1L;
	private ProjectDao projectDao;

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void addProject(Project project) {
		projectDao.create(project);
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void updateProject(Project project) {
		projectDao.update(project);
	}

	@Override
	public Project findProjectById(long id) {
		return (Project) projectDao.findById(id);
	}

	@Override
	public List<Project> findAllProjects() {
		return projectDao.findAll();
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void deleteProject(Project project) {
		projectDao.delete(project);
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void deleteProjectById(long id) {
		projectDao.deleteById(id);
	}

	@Override
	public List<Project> getCurrentProjects() {
		return projectDao.getCurrentProjects();
	}

	@Override
	public List<Project> findProjectByName(String name, boolean onlyLiveProjects) {
		return projectDao.findByName(name, onlyLiveProjects);
	}

	@Override
	public List<Project> findProjectByStarDate(Date date,
			boolean onlyLiveProjects) {
		return projectDao.findByStartDate(date, onlyLiveProjects);
	}

	@Override
	public List<Project> findProjectByEndDate(Date date,
			boolean onlyLiveProjects) {
		return projectDao.findByEndDate(date, onlyLiveProjects);
	}

	@Override
	public List<Project> findProjectByProjectTypeLabel(String label,
			boolean onlyLiveProjects) {
		return projectDao.findByProjectTypeLabel(label, onlyLiveProjects);
	}
}
