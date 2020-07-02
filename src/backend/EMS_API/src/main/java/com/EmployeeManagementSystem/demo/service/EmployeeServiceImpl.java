package com.EmployeeManagementSystem.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EmployeeManagementSystem.demo.dao.EmployeeDAO;
import com.EmployeeManagementSystem.demo.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeDAO employeeDAO;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	@Override
	@Transactional
	public List<Employee> getAll() {
		return this.employeeDAO.getAll();	
	}
	
	@Override
	@Transactional
	public Employee getEmployee(int Id) {
		return employeeDAO.getEmployee(Id);
	}

	@Override
	@Transactional
	public void save(Employee employee) {
		employeeDAO.save(employee);
	}

	@Override
	@Transactional
	public void deleteById(int Id) {
		employeeDAO.deleteById(Id);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeByQuery(String searchQuery) {
		
		return employeeDAO.getEmployeeByQuery(searchQuery);
	}

	@Override
	public Map<Integer,List<Integer>> getEmployeeHierarchy(int employeeId) {
		return employeeDAO.getEmployeeHierarchy(employeeId);
	}
	
}
