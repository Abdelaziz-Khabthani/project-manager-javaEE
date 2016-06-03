package com.abdelaziz.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.abdelaziz.model.Employee;
import com.abdelaziz.model.Project;
import com.abdelaziz.service.EmployeeService;
import com.abdelaziz.service.ProjectService;

public class AddEmployeeToProjectBacking implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ProjectService projectService;
	private EmployeeService employeeService;
	private boolean buttonState;
	private List<Employee> listEmployees;
	private List<Employee> remainingEmployees;
	private Project ProjectToUpdate;

	@PostConstruct
	public void init() {
		setButtonState(true);
	}

	public List<Employee> lisRemainingEmployees() {
		try {
			System.out
					.println("*******************LIST REMAINING EMPLOYEES*********************");
			List<Employee> allEmployee = employeeService.findAllEmplyees();
			Set<Employee> tmpSet = projectService.findProjectById(
					ProjectToUpdate.getProjectId()).getEmployees();
			List<Employee> projectEmployees = new ArrayList<Employee>(tmpSet);
			if ((allEmployee != null) && (projectEmployees != null)) {
				allEmployee.removeAll(projectEmployees);
				System.out
						.println("*******************END LIST REMAINING EMPLOYEES*********************");
				return allEmployee;
			} else {
				System.out
						.println("*******************END LIST REMAINING EMPLOYEES*********************");
				return allEmployee;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while listing employees.", ""));
			return null;
		}
	}

	public void controlButton() {
		if (this.listEmployees == null) {
			buttonState = true;
		} else if (this.listEmployees.size() == 0) {
			this.buttonState = true;
		} else {
			this.buttonState = false;
		}
	}

	public void openNewDialogue(Project project) {
		System.out
				.println("**********************OPEN************************");
		this.cleanBean();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("contentHeight", 400);
		options.put("contentWidth", 1010);
		options.put("width", 1040);
		RequestContext.getCurrentInstance().openDialog("addemployeetoaproject",
				options, null);
		ProjectToUpdate = project;
		remainingEmployees = lisRemainingEmployees();
		System.out
				.println("**********************END OPEN************************");
	}

	public void addEmployeesToProject() {
		try {
			System.out
					.println("**********************ASSOCIATE************************");
			Set<Employee> setEmployees = new HashSet<>(listEmployees);
			ProjectToUpdate.getEmployees().addAll(setEmployees);
			projectService.updateProject(ProjectToUpdate);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							ProjectToUpdate.getProjectName()
									+ "'s Projects List is Updated. ", ""));
			System.out
					.println("**********************END ASSOCIATE************************");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while updating project.", ""));
		}
	}

	public void cleanBean() {
		System.out
				.println("**********************CLEAN SESSION************************");
		remainingEmployees = null;
		listEmployees = null;
		ProjectToUpdate = null;
		buttonState = true;
		System.out
				.println("**********************END CLEAN SESSION************************");
	}

	public void closeDialoge() {
		System.out
				.println("**********************CLOSE************************");
		RequestContext.getCurrentInstance().closeDialog(null);
		System.out
				.println("**********************END CLOSE************************");
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public boolean isButtonState() {
		return buttonState;
	}

	public void setButtonState(boolean buttonState) {
		this.buttonState = buttonState;
	}

	public List<Employee> getListEmployees() {
		return listEmployees;
	}

	public void setListEmployees(List<Employee> listEmployees) {
		this.listEmployees = listEmployees;
	}

	public List<Employee> getRemainingEmployees() {
		return remainingEmployees;
	}

	public void setRemainingEmployees(List<Employee> remainingEmployees) {
		this.remainingEmployees = remainingEmployees;
	}

	public Project getProjectToUpdate() {
		return ProjectToUpdate;
	}

	public void setProjectToUpdate(Project projectToUpdate) {
		ProjectToUpdate = projectToUpdate;
	}
}
