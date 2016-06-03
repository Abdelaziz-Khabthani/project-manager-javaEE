package com.abdelaziz.dao;

import java.util.Date;
import java.util.List;

import com.abdelaziz.model.Employee;

public interface EmployeeDao extends GenericDao<Employee, String> {
	public List<Employee> findByName(String name);

	public List<Employee> findByBirthDate(Date date);

	public List<Employee> findByJobPositionLabel(String label);
}
