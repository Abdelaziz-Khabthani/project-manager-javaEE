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

public class AddProjectToEmployeeBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    private EmployeeService employeeService;
    private ProjectService projectService;
    private List<Project> listProjects;
    private List<Project> remainingProjects;
    private Employee employeeToUpdate;
    private boolean buttonState;

    @PostConstruct
    public void init() {
	buttonState = true;
    }

    public List<Project> lisRemainingProjects() {
	try {
	    System.out
		    .println("*******************LIST REMAINING PROJECT*********************");
	    List<Project> allProjects = projectService.getCurrentProjects();
	    Set<Project> tmpSet = employeeService.findEmplyeeById(
		    employeeToUpdate.getEmployeeCin()).getProjects();
	    List<Project> employeeProjects = new ArrayList<Project>(tmpSet);
	    if ((allProjects != null) && (employeeProjects != null)) {
		allProjects.removeAll(employeeProjects);
		System.out
			.println("*******************END LIST REMAINING PROJECT*********************");
		return allProjects;
	    } else {
		System.out
			.println("*******************END LIST REMAINING PROJECT*********************");
		return allProjects;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    FacesContext.getCurrentInstance().addMessage(
		    null,
		    new FacesMessage(FacesMessage.SEVERITY_FATAL,
			    "Uknowon Error while listing projects.", ""));
	    return null;
	}
    }

    public void controlButton() {
	if (this.listProjects == null) {
	    buttonState = true;
	} else if (this.listProjects.size() == 0) {
	    this.buttonState = true;
	} else {
	    this.buttonState = false;
	}
    }

    public void openNewDialogue(Employee employee) {
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
	RequestContext.getCurrentInstance().openDialog(
		"addprojecttoanemployee", options, null);
	employeeToUpdate = employee;
	remainingProjects = lisRemainingProjects();
	System.out
		.println("**********************END OPEN************************");
    }

    public void addProjectsToEmployee() {
	try {
	    System.out
		    .println("**********************ASSOCIATE************************");
	    Set<Project> setProjects = new HashSet<>(listProjects);
	    employeeToUpdate.getProjects().addAll(setProjects);
	    employeeService.updateEmployee(employeeToUpdate);
	    FacesContext.getCurrentInstance().addMessage(
		    null,
		    new FacesMessage(FacesMessage.SEVERITY_INFO,
			    employeeToUpdate.getEmployeeName()
				    + "'s Projects List is Updated. ", ""));
	    System.out
		    .println("**********************END ASSOCIATE************************");
	} catch (Exception e) {
	    e.printStackTrace();
	    FacesContext.getCurrentInstance().addMessage(
		    null,
		    new FacesMessage(FacesMessage.SEVERITY_FATAL,
			    "Uknowon Error while updating employee.", ""));
	}
    }

    public void cleanBean() {
	System.out
		.println("**********************CLEAN SESSION************************");
	remainingProjects = null;
	listProjects = null;
	employeeToUpdate = null;
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

    public EmployeeService getEmployeeService() {
	return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
	this.employeeService = employeeService;
    }

    public List<Project> getListProjects() {
	return listProjects;
    }

    public void setListProjects(List<Project> listProjects) {
	this.listProjects = listProjects;
    }

    public Employee getEmployeeToUpdate() {
	return employeeToUpdate;
    }

    public void setEmployeeToUpdate(Employee employeeToUpdate) {
	this.employeeToUpdate = employeeToUpdate;
    }

    public ProjectService getProjectService() {
	return projectService;
    }

    public void setProjectService(ProjectService projectService) {
	this.projectService = projectService;
    }

    public List<Project> getRemainingProjects() {
	return remainingProjects;
    }

    public void setRemainingProjects(List<Project> remainingProjects) {
	this.remainingProjects = remainingProjects;
    }

    public boolean isButtonState() {
	return buttonState;
    }

    public void setButtonState(boolean buttonState) {
	this.buttonState = buttonState;
    }
}
