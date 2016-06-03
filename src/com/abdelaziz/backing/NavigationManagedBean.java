package com.abdelaziz.backing;

import java.io.Serializable;
import java.lang.String;

public class NavigationManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String processIndex() {
		return "index";
	}

	public String processAddEmployeePage() {
		return "/employee/addemployee";
	}

	public String processAddProjectPage() {
		return "/project/addproject";
	}

	public String processListEmployeePage() {
		return "/employee/listemployee";
	}

	public String processListProjectPage() {
		return "/project/listproject";
	}

	public String processJobPositionPage() {
		return "/employee/listjobpositions";
	}

	public String processProjectTypePage() {
		return "/project/listprojecttypes";
	}
}