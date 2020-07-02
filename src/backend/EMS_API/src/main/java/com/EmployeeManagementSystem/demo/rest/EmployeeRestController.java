package com.EmployeeManagementSystem.demo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EmployeeManagementSystem.demo.dao.EmployeeDAO;
import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.service.EmployeeService;

@RestController
public class EmployeeRestController {

	private EmployeeService employeeservice;

	@Autowired
	public EmployeeRestController(EmployeeService employeeservice) {
		this.employeeservice = employeeservice;
	}

	@GetMapping("/employees")
	public List<Employee> getAll(){
		return employeeservice.getAll();
	}

	@GetMapping("/employeesleave/{managerId}")
	public List<Integer> empLeave(@PathVariable int managerId){
		return employeeservice.empLeave(managerId);
	}
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		
		Employee employee = employeeservice.getEmployee(employeeId);
		
		if (employee == null) {
			throw new RuntimeException("Not found - " + employeeId);
		}
		
		return employee;
	}
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee employee) {
		
		employee.setId(0);
		
		employeeservice.save(employee);
		
		return employee;
	}
	
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee employee) {
		
		employeeservice.save(employee);
		
		return employee;
	}
	
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		
		Employee tempEmployee = employeeservice.getEmployee(employeeId);
		
		if (tempEmployee == null) {
			throw new RuntimeException("Not found - " + employeeId);
		}
		
		employeeservice.deleteById(employeeId);
		
		return "Successfully Deleted "+employeeId;
	}
	
	@RequestMapping(value="employees/search", method = RequestMethod.GET)
	public List<Employee> searchEmployees(@RequestParam("q") String query) {
		
		return employeeservice.getEmployeeByQuery(query);
	}
	
	@GetMapping("employees/{employeeId}/hierarchy")
	public Map<Integer,List<Integer>> getEmployeeHierarchy(@PathVariable int employeeId){
		return employeeservice.getEmployeeHierarchy(employeeId);
	}
	
}
