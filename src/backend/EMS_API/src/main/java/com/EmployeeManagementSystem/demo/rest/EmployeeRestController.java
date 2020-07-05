package com.EmployeeManagementSystem.demo.rest;

import java.util.List;
import java.util.Map;

import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;
import com.EmployeeManagementSystem.demo.entity.Project;
import com.EmployeeManagementSystem.demo.service.EmployeeMapperService;
import com.EmployeeManagementSystem.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200/")
public class EmployeeRestController {
	/**
	 * Important Note: Use apis for inserting or deleting from leave table.
	 * Tips on using the api.
	 * 1.Get and delete apis are straight forward.
	 * 2.Post must be used for creating and put for updating.
	 * 3.For put and post field names should be corresponding hibernate mapping names.
	 * 	Use following format:-
	 * {    "id":id,
	 * 		"designation":designation,
	 *      "firstName":firstName,
	 *      "lastName":lastName,
	 *      "address":address,
	 *      "email":email,
	 *      "leaveApp":leaveApp,
	 *      "experience":experience,
	 *      "techstack":techstack,
	 *      "projects":[{"id":id},..] }
	 *  json data like above will work. Api will take care of
	 *  saving the correct employee. If there are no employees yet
	 *  assigned to this project then you may pass "employees":[].
	 *  Any id for post will work but it will saved according
	 *  to the strategy followed. For put the correct id needs
	 *  to be specified.
	 *  If id does not exists for put then an error will be thrown.
	 *  4.Techstack is comma separated field.
	 *  5.Do not provide employeeMappers and leaveApplications in put and post of employee.
	 *  Use api provided in employeeMappers and leaveApplications to create hierarchy
	 *  and leaves.
	 *  for e.g.
	 *  {
	 *     "id": 15,
	 *     "designation": "Associate",
	 *     "firstName": "Kirill",
	 *     "lastName": "Aleeksenko",
	 *     "address": "Russia",
	 *     "email": "aka@gmail.com",
	 *     "leaveApp": false,
	 *     "experience": 2,
	 *     "techstack": "java,spring,golang",
	 *     "projects": [{"id":5}]
	 * }
	 */

	private EmployeeService employeeService;
	private ProjectService projectService;

	@Autowired
	public EmployeeRestController(EmployeeService employeeService, ProjectService projectService) {
		this.employeeService = employeeService;
		this.projectService = projectService;
	}

	@GetMapping("/employees")
	@CrossOrigin(origins = "http://localhost:4200/")
	public List<Employee> getAll(){
		return employeeService.getAll();
	}

	@GetMapping("/employeesleave/{managerId}")
	public List<Integer> empLeave(@PathVariable int managerId){

		Employee employee = employeeUtility(managerId);

		return employee != null? employeeService.empLeave(managerId) : null;
	}
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		
		Employee employee = employeeUtility(employeeId);

		return employee;
	}

	@GetMapping("employees/{employeeId}/hierarchy")
	public Map<Integer,List<Integer>> getEmployeeHierarchy(@PathVariable int employeeId){

		Employee employee = employeeUtility(employeeId);

		return employee != null ? employeeService.getEmployeeHierarchy(employeeId) : null;
	}
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee employee) {
		return postAndPutUtility(employee, true);
	}
	
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee employee) {
		return postAndPutUtility(employee, false);
	}
	
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		
		Employee tempEmployee = employeeService.getEmployee(employeeId);
		
		if (tempEmployee == null) {
			throw new RuntimeException("Not found - " + employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Successfully Deleted "+employeeId;
	}
	
	@RequestMapping(value="employees/search", method = RequestMethod.GET)
	public List<Employee> searchEmployees(@RequestParam("q") String query) {
		
		return employeeService.getEmployeeByQuery(query);
	}

	public Employee employeeUtility(int id){
		Employee employee = employeeService.getEmployee(id);

		if (employee == null) {
//			throw new RuntimeException("Not found - " + employeeId);
			System.out.println("Employee not found");
			return null;
		}
		return employee;
	}

	public Employee postAndPutUtility(Employee employee, boolean post){
		List<Project> projects = employee.getProjects();
		System.out.println("Projects are "+projects.size());
		boolean flag = true;
		for (int i = 0; i < projects.size(); i++) {
			Project project = projectService.getProject(projects.get(i).getId());
			if (project == null) {
				flag = false;
				break;
			}
			projects.set(i, project);
		}

		if (flag==false) {
//            throw new RuntimeException("Not found - ");
			System.out.println("****************\nProject not found\n****************");
			return null;
		}else{
			if(post == true) employee.setId(0);
			employee.setProjects(projects);
			employeeService.save(employee);
			return employee;
		}

	}
	
}
