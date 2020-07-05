package com.EmployeeManagementSystem.demo.rest;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.LeaveApplication;
import com.EmployeeManagementSystem.demo.entity.Project;
import com.EmployeeManagementSystem.demo.service.EmployeeService;
import com.EmployeeManagementSystem.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectRestController {
    /**
     * Tips on using the api.
     * 1.Get and delete apis are straight forward.
     * 2.Post must be used for creating and put for updating.
     * 3.For put and post field names should be corresponding hibernate mapping names.
     * Use following format:-
     * {    "id":id,
     *      "name":name,
     *      "description":description,
     *      "client_name":client_name,
     *      "duration":duration,
     *      "employees":[{"id":id},..] }
     *  json data like above will work. Api will take care of
     *  saving the correct employee. If there are no employees yet
     *  assigned to this project then you may pass "employees":[].
     *  Any id for post will work but it will saved according
     *  to the strategy followed. For put the correct id needs
     *  to be specified.
     *  If id does not exists for put then an error will be thrown.
     *  for e.g.
     *      {
     *          "id": 1,
     *          "name": "DSA Class",
     *          "description": "Researching DS",
     *          "client_name": "Google",
     *          "duration": 50,
     *          "employees":[{"id":1}]
     *
     *      }
     */
    private ProjectService projectService;
    private EmployeeService employeeservice;

    @Autowired
    public ProjectRestController(ProjectService projectService, EmployeeService employeeservice) {
        this.projectService = projectService;
        this.employeeservice = employeeservice;
    }

    @GetMapping("/projects")
    public List<Project> getAll(){
        return projectService.getAll();
    }

    @GetMapping("/projects/{projectId}")
    public Project getById(@PathVariable int projectId){
        return projectService.getProject(projectId);
    }

    @PostMapping("/projects")
    public Project saveProject(@RequestBody Project project) {
        return postAndPutUtility(project,true);
    }

    @PutMapping("/projects")
    public Project updateProject(@RequestBody Project project) {
        return postAndPutUtility(project,false);
    }

    @DeleteMapping("/projects/{projectId}")
    public String deleteById(@PathVariable int projectId) {

        Project tempProject = projectService.getProject(projectId);

        if (tempProject == null) {
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            projectService.deleteById(projectId);
            return "Successfully Deleted " + projectId;
        }
    }

    public Project postAndPutUtility(Project project, boolean post){
        List<Employee> employees = project.getEmployees();
        System.out.println("Employees are "+employees.size());
        boolean flag = true;
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employeeservice.getEmployee(employees.get(i).getId());
            if (employee == null) {
                flag = false;
                break;
            }
            employees.set(i, employee);
        }

        if (flag==false) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nEmployee not found\n****************");
            return null;
        }else{
            if(post == true) project.setId(0);
            project.setEmployees(employees);
            projectService.save(project);
            return project;
        }
    }
}
