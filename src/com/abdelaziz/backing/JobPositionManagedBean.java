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

import com.abdelaziz.model.Employee;
import com.abdelaziz.model.JobPosition;
import com.abdelaziz.service.JobPositionService;

public class JobPositionManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<JobPosition> filtredJobPositions;
	private JobPositionService jobPositionService;
	private JobPosition jobPosition;
	private List<JobPosition> jobPositions;
	private Employee selectedEmployee;

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@PostConstruct
	public void init() {
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/employee/listjobpositions.xhtml")) {
			setJobPosition(new JobPosition());
			getAllJobPositions();
		} else if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/employee/addemployee.xhtml")) {
			setJobPosition(new JobPosition());
			getAllJobPositions();
		}
	}

	public void addJobPosition() {
		try {
			jobPositionService.addJobPosition(jobPosition);
			this.getAllJobPositions();

			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.equals("/employee/addemployee.xhtml")) {
				RequestContext.getCurrentInstance().execute("PF('jpw').hide()");
			} else if (FacesContext.getCurrentInstance().getViewRoot()
					.getViewId().equals("/employee/listjobpositions.xhtml")) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								jobPosition.getJobPositonLabel()
										+ " Job Position Added.", ""));
			}
			jobPosition.setJobPositonLabel(null);
			jobPosition.setEmployees(null);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Adding Job Positions.", ""));
		}
	}

	public void deleteJobPosition(JobPosition jobPositionPar) {
		try {
			if (jobPositionPar.getEmployees() == null
					|| jobPositionPar.getEmployees().size() == 0) {
				String jobPositionName = jobPositionPar.getJobPositonLabel();
				jobPositionService.deleteJobPosition(jobPositionPar);
				this.getAllJobPositions();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								jobPositionName + " Job Position was Removed.",
								""));
			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_FATAL,
										jobPositionPar.getJobPositonLabel()
												+ " Can't Be Removed, There are employees with this Job Position.",
										""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Deleting Job Positions.", ""));
		}
	}

	public void getAllJobPositions() {
		try {
			System.out
					.println("**********************GET ALL JOB POSITIONS************************");
			jobPositions = jobPositionService.findAllJobPositions();
			System.out
					.println("**********************END GET ALL JOB POSITIONS************************");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Listing Job Positions.", ""));
		}
	}

	public void updateJobPosition(CellEditEvent event) {
		try {
			String newJobPositionLabel = (String) event.getNewValue();
			String oldJobPositionLabel = (String) event.getOldValue();
			DataTable source = (DataTable) event.getSource();
			JobPosition newJobPosition = (JobPosition) source.getRowData();
			newJobPosition.setJobPositonLabel(newJobPositionLabel);
			jobPositionService.updateJobPosition(newJobPosition);
			this.getAllJobPositions();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							oldJobPositionLabel + " Job Position Updated."
									+ "to " + newJobPositionLabel, ""));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Uknowon Error while Updating Job Positions.", ""));
		}
	}

	public void validateJobPositionLabel(FacesContext context,
			UIComponent component, Object value) throws ValidatorException {
		if ((value == null) || (value.toString() == "")) {
			FacesMessage message = new FacesMessage(
					"The Job Position Should not be Empty.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		} else if (!value.toString().matches("\\w(\\w| (?! )){1,1000}\\w")) {
			System.out.println(value.toString());
			FacesMessage message = new FacesMessage(
					"The Job Position you entred is not valid, Must be only Alphanumeriques with only one space between words.");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		} else if (jobPositionService.findByJobPositionName(value.toString()) != null) {
			FacesMessage message = new FacesMessage(
					"The Job Position you entred already exist");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public JobPositionService getJobPositionService() {
		return jobPositionService;
	}

	public void setJobPositionService(JobPositionService jobPositionService) {
		this.jobPositionService = jobPositionService;
	}

	public JobPosition getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(JobPosition jobPosition) {
		this.jobPosition = jobPosition;
	}

	public List<JobPosition> getJobPositions() {
		return jobPositions;
	}

	public void setJobPositions(List<JobPosition> jobPositions) {
		this.jobPositions = jobPositions;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public List<JobPosition> getFiltredJobPositions() {
		return filtredJobPositions;
	}

	public void setFiltredJobPositions(List<JobPosition> filtredJobPositions) {
		this.filtredJobPositions = filtredJobPositions;
	}
}
