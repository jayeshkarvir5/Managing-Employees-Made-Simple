package com.EmployeeManagementSystem.demo.dao;

import java.util.List;
import java.util.Map;

import com.EmployeeManagementSystem.demo.entity.Employee;
public interface EmployeeDAO {

	public List<Employee> getAll();

	public List<Integer> empLeave(int theId);
		
	public Employee getEmployee(int theId);

	public Employee getEmployeeByEmail(String email);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);
	
	public List<Employee> getEmployeeByQuery(String searchQuery);
	
	public Map<Integer,List<Integer>> getEmployeeHierarchy(int employeeId);
	
}
