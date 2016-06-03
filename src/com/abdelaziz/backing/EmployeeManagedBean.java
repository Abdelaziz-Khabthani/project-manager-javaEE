package com.abdelaziz.backing;

import java.io.Serializable;
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
import org.primefaces.model.UploadedFile;

import com.abdelaziz.model.Employee;
import com.abdelaziz.model.JobPosition;
import com.abdelaziz.model.Project;
import com.abdelaziz.service.EmployeeService;
import com.abdelaziz.service.JobPositionService;

public class EmployeeManagedBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private UploadedFile employeePhoto;
	private List<Employee> tmpFiltredEmployees;
	private EmployeeService employeeService;
	private JobPositionService jobPositionService;
	private Employee selectedEmployee;
	private Project selectedProject;
	private String idEmployee;
	private String nameEmployee;
	private String lastNameEmployee;
	private Date birthdayEmployee;
	private String emailEmployee;
	private String jobPositionEmployeeId;
	private List<Project> listProject;
	private List<Employee> listEmployees;
	private List<JobPosition> jobPositions;
	private List<Project> filtredProjects;
	private Map<String, String> criterias = new HashMap<String, String>();
	private String criteria;
	private String keyWord;
	private Date keyWordDate;
	private boolean skip;

	@PostConstruct
	public void init() {
		System.out
				.println("**********************EMPLOYEEMANAGEDBEAN CONSTRUCT************************");
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/employee/listemployee.xhtml")
				|| FacesContext.getCurrentInstance().getViewRoot().getViewId()
						.equals("/signup.xhtml")) {
			jobPositions = jobPositionService.findAllJobPositions();
			criterias.put("Name", "name");
			criterias.put("Id", "id");
			criterias.put("Birthdate", "birthdate");
			criterias.put("Job Position", "job position");
			criteria = "id";
			this.filterEmployees();
		} else if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/project/addproject.xhtml")) {
			this.filterEmployees();
		}
		System.out
				.println("**********************EMPLOYEEMANAGEDBEAN END CONSTRUCT************************");
	}

	public void removeProjectFromEmployee(Employee employee, Project project) {
		try {
			System.out
					.println("**********************REMOVE PROJECT************************");
			Employee tmpEmployee = employeeService.findEmplyeeById(employee
					.getEmployeeCin());
			String projectName = project.getProjectName();
			tmpEmployee.getProjects().remove(project);
			employeeService.updateEmployee(tmpEmployee);
			this.filterEmployees();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, employee
							.getEmployeeName()
							+ " is no more working on "
							+ projectName + " project.", ""));
			System.out
					.println("**********************END REMOVE PROJECT************************");

		} catch (Exception e) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Uknowon Error while deleting project from employee.",
									""));
		}
	}

	public void removeEmployee(Employee employee) {
		try {
			System.out
					.println("**********************REMOVE EMPLOYEE************************");
			String employeeName = employee.getEmployeeName();
			employeeService.deleteEmployee(employee);
			this.filterEmployees();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, employeeName
							+ " was Removed.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while deleting the employee", ""));
		}
		System.out
				.println("**********************END REMOVE EMPLOYEE************************");
	}

	public void onCriteriaChange() {
		System.out
				.println("**********************CRITERIA CHANGED***********************");
		this.keyWordDate = null;
		this.keyWord = null;
		this.filterEmployees();
		System.out
				.println("**********************END CRITERIA CHANGE***********************");
	}

	public String jobPositionLabel() {
		System.out
				.println("**********************GETTING JOB POSITION LABEL***********************");
		String label = jobPositionService.findJobPositionById(1)
				.getJobPositonLabel();
		try {
			label = jobPositionService.findJobPositionById(
					Long.parseLong(jobPositionEmployeeId)).getJobPositonLabel();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Loading JobPosition", ""));
		}
		System.out
				.println("**********************END GETTING JOB POSITION LABEL***********************");
		return label;
	}

	public void updateEmployee() {
		System.out
				.println("**********************EDIT************************");
		try {
			JobPosition jp = jobPositionService.findJobPositionById(Long
					.parseLong(jobPositionEmployeeId));
			selectedEmployee.setJobPosition(jp);
			employeeService.updateEmployee(selectedEmployee);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Employee Updated!", ""));
			RequestContext.getCurrentInstance().execute("PF('ew').hide()");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while updating the employee", ""));
		}
		System.out
				.println("**********************END ROW EDIT************************");
	}

	public String handleWizardFlow(FlowEvent event) {
		if (isSkip()) {
			System.out
					.println("**********************TO COMFIRMATION************************");
			skip = false;
			return "confirmation";
		} else {
			System.out
					.println("**********************NEXT WINDOW************************");
			return event.getNewStep();
		}
	}

	public String addEmployee() {
		try {
			System.out
					.println("**********************ADD EMPLOYEE************************");
			Set<Project> setProjects = new HashSet<>(listProject);
			JobPosition jp;
			try {
				jp = jobPositionService.findJobPositionById(Long
						.parseLong(jobPositionEmployeeId));
			} catch (Exception e) {
				return "error";
			}

			Employee employee = new Employee(idEmployee, jp, nameEmployee,
					lastNameEmployee, birthdayEmployee, emailEmployee,
					setProjects);
			Flash flash = FacesContext.getCurrentInstance()
					.getExternalContext().getFlash();
			listProject.clear();
			employeeService.addEmployee(employee);
			flash.put("employeeMessage",
					"An Employee named " + employee.getEmployeeName()
							+ " Has Been Added succefuly.");
			flash.put("addAnotherEmployee", "Add Another Employee.");
			System.out
					.println("**********************END ADD EMPLOYEE************************");
			return "sucess";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public void employeeAddedMessage() {
		System.out
				.println("**********************CHECKING EMPLOYEE ADDED MESSAGE************************");
		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		if (!flash.isEmpty()) {
			String flashMessage = flash.get("employeeMessage").toString();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, flashMessage,
							""));
		}
	}

	private List<Employee> listAllEmployees() {
		try {
			System.out
					.println("**********************LIST ALL************************");
			List<Employee> employees = employeeService.findAllEmplyees();
			for (Employee employee : employees) {
				List<Project> list = new ArrayList<Project>(
						employee.getProjects());
				Collections.sort(list);
				Set<Project> setProjects = new HashSet<>(list);
				Collections.reverse(list);
				employee.setProjects(setProjects);
			}
			return employees;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while loading the employees", ""));
			return null;
		}
	}

	public void filterEmployees() {
		try {
			if ((keyWord != null) && (keyWord != "")
					|| (this.keyWordDate != null)) {
				System.out
						.println("**********************FILTRING EMPLOYEES************************");
				if (criteria.equals("id")) {
					Employee filtredEmployee = employeeService
							.findEmplyeeById(this.keyWord);
					if (filtredEmployee != null) {
						List<Employee> tmpListEmployee = new ArrayList<Employee>();
						tmpListEmployee.clear();
						tmpListEmployee.add(filtredEmployee);
						this.listEmployees = tmpListEmployee;
					} else
						this.listEmployees = null;
				} else if (criteria.equals("name")) {
					if (listEmployees != null)
						this.listEmployees.clear();
					this.listEmployees = employeeService
							.findByEmployeeName(this.keyWord);
				} else if (criteria.equals("birthdate")) {
					if (listEmployees != null)
						this.listEmployees.clear();
					this.listEmployees = employeeService
							.findByEmployeeBirthDate(this.keyWordDate);
				} else if (criteria.equals("job position")) {
					if (listEmployees != null)
						this.listEmployees.clear();
					this.listEmployees = employeeService
							.findEmployeeByJobPositionLabel(this.keyWord);
				}
				System.out
						.println("**********************END FILTRING EMPLOYEES************************");
			} else {
				if (listEmployees != null)
					this.listEmployees.clear();
				this.listEmployees = listAllEmployees();
			}

		} catch (NumberFormatException e) {
			this.listEmployees = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while filtring the employee", ""));
			this.listEmployees = null;
		}
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
				System.out.println(value.toString());
				FacesMessage message = new FacesMessage(
						"The ID you entred is not valid, Must be only numeriques.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			} else if (value.toString().length() != 8) {
				FacesMessage message = new FacesMessage(
						"The ID must be 8 Digits.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			} else if (employeeService.findEmplyeeById(value.toString()) != null) {
				FacesMessage message = new FacesMessage(
						"Employee with this ID already Exist, Please Try Again");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}
		} finally {
			System.out
					.println("**********************END CHECKING PRIMARY KEY************************");
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

	public Date getBirthdayEmployee() {
		return birthdayEmployee;
	}

	public void setBirthdayEmployee(Date birthdayEmployee) {
		this.birthdayEmployee = birthdayEmployee;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public Map<String, String> getCriterias() {
		return criterias;
	}

	public void setCriterias(Map<String, String> criterias) {
		this.criterias = criterias;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public Date getKeyWordDate() {
		return keyWordDate;
	}

	public void setKeyWordDate(Date keyWordDate) {
		this.keyWordDate = keyWordDate;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public List<Project> getListProject() {
		return listProject;
	}

	public void setListProject(List<Project> listProject) {
		this.listProject = listProject;
	}

	public List<Employee> getListEmployees() {
		return listEmployees;
	}

	public void setListEmployees(List<Employee> listEmployees) {
		this.listEmployees = listEmployees;
	}

	public String getEmailEmployee() {
		return emailEmployee;
	}

	public void setEmailEmployee(String emailEmployee) {
		this.emailEmployee = emailEmployee;
	}

	public List<JobPosition> getJobPositions() {
		return jobPositions;
	}

	public void setJobPositions(List<JobPosition> jobPositions) {
		this.jobPositions = jobPositions;
	}

	public JobPositionService getJobPositionService() {
		return jobPositionService;
	}

	public void setJobPositionService(JobPositionService jobPositionService) {
		this.jobPositionService = jobPositionService;
	}

	public String getLastNameEmployee() {
		return lastNameEmployee;
	}

	public void setLastNameEmployee(String lastNameEmployee) {
		this.lastNameEmployee = lastNameEmployee;
	}

	public String getJobPositionEmployeeId() {
		return jobPositionEmployeeId;
	}

	public void setJobPositionEmployeeId(String jobPositionEmployeeId) {
		this.jobPositionEmployeeId = jobPositionEmployeeId;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}

	public List<Project> getFiltredProjects() {
		return filtredProjects;
	}

	public void setFiltredProjects(List<Project> filtredProjects) {
		this.filtredProjects = filtredProjects;
	}

	public List<Employee> getTmpFiltredEmployees() {
		return tmpFiltredEmployees;
	}

	public void setTmpFiltredEmployees(List<Employee> tmpFiltredEmployees) {
		this.tmpFiltredEmployees = tmpFiltredEmployees;
	}

	public UploadedFile getEmployeePhoto() {
		return employeePhoto;
	}

	public void setEmployeePhoto(UploadedFile employeePhoto) {
		this.employeePhoto = employeePhoto;
	}
}
