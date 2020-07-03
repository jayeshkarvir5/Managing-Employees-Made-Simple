package com.EmployeeManagementSystem.demo.rest;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;
import com.EmployeeManagementSystem.demo.service.EmployeeMapperService;
import com.EmployeeManagementSystem.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeMapperRestController {
    private EmployeeService employeeservice;
    private EmployeeMapperService employeeMapperService;

    @Autowired
    public EmployeeMapperRestController(EmployeeService employeeservice, EmployeeMapperService employeeMapperService) {
        this.employeeservice = employeeservice;
        this.employeeMapperService = employeeMapperService;
    }

    @GetMapping("/employeeMappers")
    public Map<Integer,List<Integer>> getAllEm(){
        return employeeMapperService.getAll();
    }

    @GetMapping("/employeeMappers/{employeeId}")
    public Map<Integer,List<Integer>> getManagers(@PathVariable int employeeId){
        return employeeMapperService.getRelation(employeeId);
    }

    @PostMapping("/employeeMappers")
    public EmployeeMapper addEmployeeMapper(@RequestBody EmployeeMapper employeeMapper) {

        Employee employee = employeeservice.getEmployee(employeeMapper.getEmployee().getId());
        Employee manager = employeeservice.getEmployee(employeeMapper.getManager().getId());
        System.out.println("****************\n" + employee + "\n" + manager + "\n****************");
        if (employee == null || manager == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            employeeMapper.setId(0);
            employeeMapper.setEmployee(employee);
            employeeMapper.setManager(manager);
            employeeMapperService.save(employee, employeeMapper);
            return employeeMapper;
        }


    }

    @PutMapping("/employeeMappers")
    public EmployeeMapper updateEmployeeMapper(@RequestBody EmployeeMapper employeeMapper) {
        Employee employee = employeeservice.getEmployee(employeeMapper.getEmployee().getId());
        Employee manager = employeeservice.getEmployee(employeeMapper.getManager().getId());

        if (employee == null || manager == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            employeeMapper.setId(0);
            employeeMapper.setEmployee(employee);
            employeeMapper.setManager(manager);
            employeeMapperService.save(employee, employeeMapper);
            return employeeMapper;
        }
    }

    @DeleteMapping("/employeeMappers/{employeeId}")
    public String deleteAllEmployeeMappings(@PathVariable int employeeId) {

        Employee tempEmployee = employeeservice.getEmployee(employeeId);

        if (tempEmployee == null) {
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            employeeMapperService.deleteAllById(employeeId);
            return "Successfully Deleted "+employeeId;
        }
    }

    @DeleteMapping("/employeeMappers/{employeeId}/{managerId}")
    public String deleteEmployeeMapping(@PathVariable int employeeId, @PathVariable int managerId){

        Employee employee = employeeservice.getEmployee(employeeId);
        Employee manager = employeeservice.getEmployee(managerId);

        if (employee == null || manager == null) {
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            employeeMapperService.deleteById(employeeId, managerId);
            return "Successfully deleted mapping between "+employeeId + " " + managerId;
        }

    }


}
