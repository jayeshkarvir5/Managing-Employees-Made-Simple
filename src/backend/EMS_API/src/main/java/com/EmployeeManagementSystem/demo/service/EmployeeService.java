package com.EmployeeManagementSystem.demo.service;

import java.util.List;
import java.util.Map;

import com.EmployeeManagementSystem.demo.entity.Employee;

public interface EmployeeService {

	public List<Employee> getAll();

	public List<Integer> empLeave(int theId);
	
	public Employee getEmployee(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);
	
	public List<Employee> getEmployeeByQuery(String searchQuery);
	
	public Map<Integer,List<Integer>> getEmployeeHierarchy(int employeeId);
	
}
