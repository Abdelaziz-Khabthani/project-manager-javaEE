package com.abdelaziz.backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import com.abdelaziz.model.Project;
import com.abdelaziz.model.ProjectType;
import com.abdelaziz.service.ProjectTypeService;

public class ProjectTypeManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ProjectType> filrredProjectTypes;
	private ProjectTypeService projectTypeService;
	private ProjectType projectType;
	private List<ProjectType> projectTypes;
	private Project selectedProject;

	// ////////////////////////////////////////////////////////////////////////////

	@PostConstruct
	public void init() {
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/project/listprojecttypes.xhtml")) {
			setProjectType(new ProjectType());
			getAllProjectTypes();
		} else if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/project/addproject.xhtml")) {
			setProjectType(new ProjectType());
			getAllProjectTypes();
		}
	}

	public void addProjectType() {
		try {
			projectTypeService.addProjectType(projectType);
			this.getAllProjectTypes();

			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.equals("/project/addproject.xhtml")) {
				RequestContext.getCurrentInstance().execute("PF('ptw').hide()");
			} else if (FacesContext.getCurrentInstance().getViewRoot()
					.getViewId().equals("/project/listprojecttypes.xhtml")) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								projectType.getProjectTypeLabel()
										+ " Project Type Added.", ""));
			}
			projectType.setProjectTypeLabel(null);
			projectType.setProjects(null);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Adding Project Type.", ""));
		}
	}

	public void getAllProjectTypes() {
		try {
			System.out
					.println("**********************GET ALL PROJECT TYPES************************");
			projectTypes = projectTypeService.findAllProjectTypes();
			System.out
					.println("**********************END GET ALL PROJECT TYPES************************");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Getting Project Types.", ""));
		}
	}

	public void deleteProjectTypes(ProjectType projectTypePar) {
		try {
			System.out
					.println("**********************DELET PROJECT TYPE************************");
			if (projectTypePar.getProjects() == null
					|| projectTypePar.getProjects().size() == 0) {
				String projectTypeName = projectTypePar.getProjectTypeLabel();
				projectTypeService.deleteProjectType(projectTypePar);
				this.getAllProjectTypes();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								projectTypeName + " Project Type was Removed.",
								""));
			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_FATAL,
										projectTypePar.getProjectTypeLabel()
												+ " Can't Be Removed, There are Projects with this Project Type.",
										""));
			}
			System.out
					.println("**********************END DELET PROJECT TYPE************************");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Deleting Project Type.", ""));
		}
	}

	public void updateProjectType(CellEditEvent event) {
		try {
			String newProjectTypeLabel = (String) event.getNewValue();
			String oldProjectTypeLabel = (String) event.getOldValue();
			DataTable source = (DataTable) event.getSource();
			ProjectType newProjectType = (ProjectType) source.getRowData();
			newProjectType.setProjectTypeLabel(newProjectTypeLabel);
			projectTypeService.updateProjectType(newProjectType);
			this.getAllProjectTypes();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							oldProjectTypeLabel + " Project Type Updated."
									+ "to " + newProjectTypeLabel, ""));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Updating Project Type.", ""));
		}
	}

	public void validateProjectTypeLabel(FacesContext context,
			UIComponent component, Object value) throws ValidatorException {
		if ((value == null) || (value.toString() == "")) {
			FacesMessage message = new FacesMessage(
					"The Project Type Should not be Empty.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		} else if (!value.toString().matches("\\w(\\w| (?! )){1,1000}\\w")) {
			System.out.println(value.toString());
			FacesMessage message = new FacesMessage(
					"The Project Type you entred is not valid, Must be only Alphanumerique with only one space between words.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		} else if (projectTypeService.findProjectTypeByNAme(value.toString()) != null) {
			FacesMessage message = new FacesMessage(
					"The Project Type you entred already exist");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	// ////////////////////////////////////////////////////////////////////////////

	public ProjectTypeService getProjectTypeService() {
		return projectTypeService;
	}

	public void setProjectTypeService(ProjectTypeService projectTypeService) {
		this.projectTypeService = projectTypeService;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public List<ProjectType> getProjectTypes() {
		return projectTypes;
	}

	public void setProjectTypes(List<ProjectType> projectTypes) {
		this.projectTypes = projectTypes;
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}

	public List<ProjectType> getFilrredProjectTypes() {
		return filrredProjectTypes;
	}

	public void setFilrredProjectTypes(List<ProjectType> filrredProjectTypes) {
		this.filrredProjectTypes = filrredProjectTypes;
	}
}
