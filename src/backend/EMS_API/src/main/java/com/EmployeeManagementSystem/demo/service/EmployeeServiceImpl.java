package com.EmployeeManagementSystem.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public List<Integer> empLeave(int Id) {
		return this.employeeDAO.empLeave(Id);
	}
	
	@Override
	@Transactional
	public Employee getEmployee(int Id) {
		return employeeDAO.getEmployee(Id);
	}

	@Override
	@Transactional
	public Employee getEmployeeByEmail(String email) {
		return  employeeDAO.getEmployeeByEmail(email);
	}
	
	@Transactional
	@Override
	public String getPassword(String employeeId) {
		return employeeDAO.getPassword(employeeId);
	}

	@Transactional
	@Override
	public ResponseEntity<?> resetPassword(String id, String oldPassword, String newPassword) {
		return employeeDAO.resetPassword(id, oldPassword, newPassword);
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
	@Transactional
	public Map<Integer,List<Integer>> getEmployeeHierarchy(int employeeId) {
		return employeeDAO.getEmployeeHierarchy(employeeId);
	}

	@Override
	@Transactional
	public Map<Integer, List<Employee>> getFullHierarchy() {
		return employeeDAO.getFullHierarchy();
	}

	@Override
	@Transactional
	public Map<Integer, Employee> getUpHierarchy(int empId) { return employeeDAO.getUpHierarchy(empId); }
}
