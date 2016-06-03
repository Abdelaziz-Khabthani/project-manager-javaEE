package com.abdelaziz.service;

import java.util.Date;
import java.util.List;

import com.abdelaziz.model.Project;

public interface ProjectService {

	public void addProject(Project project);

	public void updateProject(Project project);

	public Project findProjectById(long id);

	public List<Project> findAllProjects();

	public void deleteProject(Project project);

	public void deleteProjectById(long id);

	public List<Project> getCurrentProjects();

	public List<Project> findProjectByName(String name,boolean onlyLiveProjects);

	public List<Project> findProjectByStarDate(Date date, boolean onlyLiveProjects);

	public List<Project> findProjectByEndDate(Date date, boolean onlyLiveProjects);

	public List<Project> findProjectByProjectTypeLabel(String label, boolean onlyLiveProjects);
}
