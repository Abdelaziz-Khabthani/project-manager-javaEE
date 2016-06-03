package com.abdelaziz.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.abdelaziz.dao.EmployeeDao;
import com.abdelaziz.model.Employee;
import com.abdelaziz.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService, Serializable {

	private static final long serialVersionUID = 1L;
	private EmployeeDao employeeDao;

	public void setEmployeDao(EmployeeDao employeDao) {
		this.employeeDao = employeDao;
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void addEmployee(Employee employee) {
		employeeDao.create(employee);
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void updateEmployee(Employee employee) {
		employeeDao.update(employee);
	}

	@Override
	public Employee findEmplyeeById(String id) {
		return (Employee) employeeDao.findById(id);
	}

	@Override
	public List<Employee> findAllEmplyees() {
		return employeeDao.findAll();
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void deleteEmployee(Employee employee) {
		employeeDao.delete(employee);
	}

	@Override
	@PreAuthorize(value = "hasRole('ADMIN_ROLE')")
	public void deleteEmployeeById(String id) {
		employeeDao.deleteById(id);
	}

	@Override
	public List<Employee> findByEmployeeName(String name) {
		return employeeDao.findByName(name);
	}

	@Override
	public List<Employee> findByEmployeeBirthDate(Date date) {
		return employeeDao.findByBirthDate(date);
	}

	@Override
	public List<Employee> findEmployeeByJobPositionLabel(String label) {
		return employeeDao.findByJobPositionLabel(label);
	}
}