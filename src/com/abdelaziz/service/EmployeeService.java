package com.abdelaziz.service;

import java.util.Date;
import java.util.List;

import com.abdelaziz.model.Employee;

public interface EmployeeService {
	public void addEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public Employee findEmplyeeById(String id);

	public List<Employee> findAllEmplyees();

	public void deleteEmployee(Employee employee);

	public void deleteEmployeeById(String id);

	public List<Employee> findByEmployeeName(String name);

	public List<Employee> findByEmployeeBirthDate(Date date);

	List<Employee> findEmployeeByJobPositionLabel(String label);
}
