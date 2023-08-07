package com.leavemanagement.service;

import java.util.List;

import com.leavemanagement.model.Employee;


public interface EmployeeService {

	List<Employee> getAll();
	
	Boolean addEmployee(Employee employee);
	
	Boolean updateEmployee(Employee employee);
	
	Employee getEmployee(int id);
	
	Employee getEmployeeByName(String name);
	
	void deleteEmployee(int id);
	
	Employee getEmployeeByEmail(String email);
	
}
