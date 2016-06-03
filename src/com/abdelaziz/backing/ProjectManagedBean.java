package com.abdelaziz.backing;

//////////////////////////////////////////////////////////////////////////////////////

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import com.abdelaziz.model.Employee;
import com.abdelaziz.model.Project;
import com.abdelaziz.model.ProjectType;
import com.abdelaziz.service.ProjectService;
import com.abdelaziz.service.ProjectTypeService;

//////////////////////////////////////////////////////////////////////////////////////

//TODO project: satus, estimated, client 
//TODO employee: photo, send email
//TODO user enabled signup
//TODO facelet: login, charts, integration
public class ProjectManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Project project;
	private String ProjectId;
	private boolean onlyLiveProjects;
	private ProjectService projectService;
	private Project selectedProject;
	private Employee selectedEmployee;
	private ProjectTypeService projectTypeService;
	private List<ProjectType> projectTypes;
	private List<Project> listAllPendingProjects;
	private List<Project> listProject;
	private List<Employee> listEmployee;
	private String projectTypeId;
	private Map<String, String> criterias = new HashMap<String, String>();
	private String criteria;
	private String keyWord;
	private Date keyWordDate;
	private boolean skip;

	// ////////////////////////////////////////////////////////////////////////////////////

	@PostConstruct
	public void init() {
		System.out
				.println("**********************PROJECTMANAGEDBEAN CONSTRUCT************************");
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/employee/addemployee.xhtml")) {
			listAllPendingProjects = new ArrayList<Project>();
			listAllPendingProjects.clear();
			listAllPendingProjects = listPendingProjects();
		} else if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/project/addproject.xhtml")) {
			setProject(new Project());
		} else if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/project/listproject.xhtml")) {
			projectTypes = projectTypeService.findAllProjectTypes();
			criterias.put("Name", "name");
			criterias.put("Id", "id");
			criterias.put("Start Date", "startdate");
			criterias.put("End Date", "enddate");
			criterias.put("Project Type", "projecttype");
			criteria = "id";
			this.filterProject();
		}
		System.out
				.println("**********************END PROJECTMANAGEDBEAN CONSTRUCT************************");
	}

	public List<Project> listPendingProjects() {
		System.out
				.println("**********************LIST PENDING PROJECTS************************");
		List<Project> listProjects = new ArrayList<Project>();
		listProjects.clear();
		try {
			listProjects = projectService.getCurrentProjects();
			Collections.sort(listProjects);
			Collections.reverse(listProjects);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while loading the Pending Projects",
							""));
		}
		System.out
				.println("**********************END LIST PENDING PROJECTS************************");
		return listProjects;
	}

	public List<Project> listAllProjects() {
		System.out
				.println("**********************LIST ALL PROJECTS************************");
		List<Project> listProjects = null;
		try {
			listProjects = projectService.findAllProjects();
			Collections.sort(listProjects);
			Collections.reverse(listProjects);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while loading the Projects", ""));
		}
		System.out
				.println("**********************END LIST ALL PROJECTS************************");
		return listProjects;
	}

	public String handleWizardFlow(FlowEvent event) {
		if (isSkip()) {
			System.out
					.println("**********************TO COMFIRMATION************************");
			setSkip(false);
			return "confirmation";
		} else {
			System.out
					.println("**********************NEXT WINDOW************************");
			return event.getNewStep();
		}
	}

	public String projectTypeLabel() {
		System.out
				.println("**********************GETTING PROJECT TYPE LABEL***********************");
		String label = null;
		try {
			System.out.println(projectTypeId);
			System.out.println(Long.parseLong(projectTypeId));
			label = projectTypeService.findProjectTypeById(
					Long.parseLong(projectTypeId)).getProjectTypeLabel();
			System.out.println(label);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Loading Project Type", ""));
		}
		System.out
				.println("**********************END GETTING Project Type LABEL***********************");
		return label;
	}

	public void validatePrimaryKey(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		try {
			System.out
					.println("**********************CHEKING PRIMARY KEY************************");
			if ((value == null) || (value.toString() == "")) {
				FacesMessage message = new FacesMessage(
						"The ID Should not be Empty.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			} else if (!value.toString().matches("^([0-9][0-9]*)$")) {
				FacesMessage message = new FacesMessage(
						"The ID you entred is not valid, Must be only numeriques.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			} else if (value.toString().length() > 10) {
				FacesMessage message = new FacesMessage(
						"The ID must be maximum 10 Digits.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			} else if (projectService.findProjectById(Long.parseLong(value
					.toString())) != null) {
				FacesMessage message = new FacesMessage(
						"Project with this ID already Exist, Please Try Again");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		} finally {
			System.out
					.println("**********************END CHECKING PRIMARY KEY************************");
		}
	}

	public void validateStarDate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (value != null) {
			String dateStr = value.toString();
			DateFormat readFormat = new SimpleDateFormat(
					"EEE MMM dd hh:mm:ss z yyyy");
			Date date = null;
			try {
				date = readFormat.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.equals("/project/listproject.xhtml")) {
				if (date.compareTo(selectedProject.getProjectStartDate()) < 0) {
					FacesMessage message = new FacesMessage(
							"The Date Must Be Greater than Initial Project Date.");
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(message);
				}
			} else {
				if (date.compareTo(new Date()) < 0) {
					FacesMessage message = new FacesMessage(
							"The Date Must Be Greater than Today's Date");
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(message);
				}
			}
		} else {
			FacesMessage message = new FacesMessage("Please Enter A Date.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	public String addProject() {
		try {
			System.out
					.println("**********************ADD PROJECT************************");
			Set<Employee> setEmployees = new HashSet<>(listEmployee);
			ProjectType pt;
			try {
				pt = projectTypeService.findProjectTypeById(Long
						.parseLong(projectTypeId));
			} catch (Exception e) {
				return "error";
			}

			project.setProjectId(Long.parseLong(ProjectId));
			project.setProjectType(pt);
			project.setEmployees(setEmployees);

			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			listEmployee.clear();
			projectService.addProject(project);
			flash.put("projectMessage",
					"A Project named " + project.getProjectName()
							+ " Has Been Added succefuly.");
			flash.put("addAnotherProject", "Add Another Project.");
			System.out
					.println("**********************END ADD EMPLOYEE************************");
			return "sucess";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public void filterProject() {
		try {
			if ((keyWord != null) && (keyWord != "")
					|| (this.keyWordDate != null)) {
				System.out
						.println("**********************FILTRING ProjectS************************");
				if (criteria.equals("id")) {
					Project filtredProject;
					try {
						filtredProject = projectService.findProjectById(Long
								.parseLong(this.keyWord));
					} catch (NumberFormatException e) {
						filtredProject = null;
					}
					if (filtredProject != null) {
						List<Project> tmpListProject = new ArrayList<Project>();
						tmpListProject.clear();
						tmpListProject.add(filtredProject);
						this.listProject = tmpListProject;
					} else
						this.listProject = null;
				} else if (criteria.equals("name")) {
					if (listProject != null)
						this.listProject.clear();
					this.listProject = projectService.findProjectByName(
							this.keyWord, onlyLiveProjects);
				} else if (criteria.equals("startdate")) {
					if (listProject != null)
						this.listProject.clear();
					this.listProject = projectService.findProjectByStarDate(
							this.keyWordDate, onlyLiveProjects);
				} else if (criteria.equals("enddate")) {
					if (listProject != null)
						this.listProject.clear();
					this.listProject = projectService.findProjectByEndDate(
							this.keyWordDate, onlyLiveProjects);
				} else if (criteria.equals("projecttype")) {
					if (listProject != null)
						this.listProject.clear();
					this.listProject = projectService
							.findProjectByProjectTypeLabel(this.keyWord,
									onlyLiveProjects);
				}
				if (this.listProject != null && !this.listProject.isEmpty()) {
					Collections.sort(this.listProject);
					Collections.reverse(this.listProject);
				}
				System.out
						.println("**********************END FILTRING EMPLOYEES************************");
			} else {
				if (listProject != null)
					this.listProject.clear();
				if (isOnlyLiveProjects())
					this.listProject = listPendingProjects();
				else
					this.listProject = listAllProjects();
			}

		} catch (NumberFormatException e) {
			this.listProject = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while filtring the projects", ""));
			this.listProject = null;
		}
	}

	public void removeEmployeeFromProject(Employee employee, Project project) {
		try {
			System.out
					.println("**********************REMOVE Employee************************");
			Project tmpProject = projectService.findProjectById(project
					.getProjectId());
			String employeeName = employee.getEmployeeName();
			tmpProject.getEmployees().remove(employee);
			projectService.updateProject(tmpProject);
			this.filterProject();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, employeeName
							+ " is no more working on "
							+ project.getProjectName() + " project.", ""));
			System.out
					.println("**********************END REMOVE Employee************************");

		} catch (Exception e) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Uknowon Error while deleting employee from project.",
									""));
		}
	}

	public void removeProject(Project project) {
		try {
			System.out
					.println("**********************REMOVE Project************************");
			String projectName = project.getProjectName();
			projectService.deleteProject(project);
			this.filterProject();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, projectName
							+ " was Removed.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while deleting the project", ""));
		}
		System.out
				.println("**********************END REMOVE Project************************");
	}

	public void onCriteriaChange() {
		System.out
				.println("**********************CRITERIA CHANGED***********************");
		this.keyWordDate = null;
		this.keyWord = null;
		this.filterProject();
		System.out
				.println("**********************END CRITERIA CHANGE***********************");
	}

	public void updateProject() {
		System.out
				.println("**********************EDIT************************");
		try {
			ProjectType pt = projectTypeService.findProjectTypeById(Long
					.parseLong(projectTypeId));
			selectedProject.setProjectType(pt);
			projectService.updateProject(selectedProject);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Project Updated!", ""));
			RequestContext.getCurrentInstance().execute("PF('pw').hide()");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while updating the project", ""));
		}
		System.out
				.println("**********************END ROW EDIT************************");
	}

	public void projectAddedMessage() {
		System.out
				.println("**********************CHECKING PROJECT ADDED MESSAGE************************");
		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		if (!flash.isEmpty()) {
			String flashMessage = flash.get("projectMessage").toString();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, flashMessage,
							""));
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public List<Project> getListAllPendingProjects() {
		return listAllPendingProjects;
	}

	public void setListAllPendingProjects(List<Project> listAllPendingProjects) {
		this.listAllPendingProjects = listAllPendingProjects;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public ProjectTypeService getProjectTypeService() {
		return projectTypeService;
	}

	public void setProjectTypeService(ProjectTypeService projectTypeService) {
		this.projectTypeService = projectTypeService;
	}

	public String getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(String projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public List<Employee> getListEmployee() {
		return listEmployee;
	}

	public void setListEmployee(List<Employee> listEmployee) {
		this.listEmployee = listEmployee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectId() {
		return ProjectId;
	}

	public void setProjectId(String projectId) {
		ProjectId = projectId;
	}

	public Map<String, String> getCriterias() {
		return criterias;
	}

	public void setCriterias(Map<String, String> criterias) {
		this.criterias = criterias;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Date getKeyWordDate() {
		return keyWordDate;
	}

	public void setKeyWordDate(Date keyWordDate) {
		this.keyWordDate = keyWordDate;
	}

	public List<Project> getListProject() {
		return listProject;
	}

	public void setListProject(List<Project> listProject) {
		this.listProject = listProject;
	}

	public List<ProjectType> getProjectTypes() {
		return projectTypes;
	}

	public void setProjectTypes(List<ProjectType> projectTypes) {
		this.projectTypes = projectTypes;
	}

	public boolean isOnlyLiveProjects() {
		return onlyLiveProjects;
	}

	public void setOnlyLiveProjects(boolean onlyLiveProjects) {
		this.onlyLiveProjects = onlyLiveProjects;
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}
}