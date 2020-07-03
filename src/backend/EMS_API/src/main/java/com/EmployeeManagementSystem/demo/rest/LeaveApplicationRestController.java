package com.EmployeeManagementSystem.demo.rest;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;
import com.EmployeeManagementSystem.demo.entity.LeaveApplication;
import com.EmployeeManagementSystem.demo.service.EmployeeService;
import com.EmployeeManagementSystem.demo.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LeaveApplicationRestController {
    private EmployeeService employeeservice;
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    public LeaveApplicationRestController(EmployeeService employeeservice, LeaveApplicationService leaveApplicationService) {
        this.employeeservice = employeeservice;
        this.leaveApplicationService = leaveApplicationService;
    }

    @GetMapping("/leaveapplications")
    public Map<Integer,List<Integer>> getAll(){
        return leaveApplicationService.getAll();
    }

    @GetMapping("/leaveapplications/{employeeId}")
    public Map<Integer,List<Integer>> getById(@PathVariable int employeeId){
        Employee employee = employeeservice.getEmployee(employeeId);
        if (employee == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            return leaveApplicationService.getById(employeeId);
        }

    }
    @PostMapping("/leaveapplications")
    public LeaveApplication saveLeaveApplication(@RequestBody LeaveApplication leaveApplication) {
        Employee employee = employeeservice.getEmployee(leaveApplication.getEmployee().getId());

        if (employee == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            leaveApplication.setId(0);
            leaveApplication.setEmployee(employee);
            leaveApplicationService.save(employee, leaveApplication);
            return leaveApplication;
        }
    }

    @PutMapping("/leaveapplications")
    public LeaveApplication updateLeaveApplication(@RequestBody LeaveApplication leaveApplication) {
        Employee employee = employeeservice.getEmployee(leaveApplication.getEmployee().getId());

        if (employee == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            leaveApplication.setEmployee(employee);
            leaveApplicationService.save(employee, leaveApplication);
            return leaveApplication;
        }
    }

    @DeleteMapping("/leaveapplications/{employeeId}")
    public String deleteById(@PathVariable int employeeId) {

        Employee tempEmployee = employeeservice.getEmployee(employeeId);

        if (tempEmployee == null) {
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            leaveApplicationService.delete(employeeId);
            return "Successfully Deleted "+employeeId;
        }
    }
}
