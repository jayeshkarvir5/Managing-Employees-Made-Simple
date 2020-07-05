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

    /**
     * Tips on using the api.
     * 1.Get and delete apis are straight forward.
     * 2.Post must be used for creating and put for updating.
     * 3.For put and post field names should be corresponding hibernate mapping names.
     * Use following format:-
     * {    "id":id,
     *      "employee":{"id":id},
     *      "manager":{"id":id} }
     *  json data like above will work. Api will take care of
     *  saving the correct employees. Any id for post will work but
     *  it will saved according to the strategy followed. For put
     *  the correct id needs to be specified.
     *  If id does not exists for put then an error will be thrown.
     *  for e.g.
     *  {
     *      "id":10,
     *      "employee":{
     *          "id":7
     *      },
     *      "manager":{
     *          "id":5
     *      }
     *  }
     *  4.For getById method 0th index is emp and 1 index is manager.
     */

    private EmployeeService employeeservice;
    private EmployeeMapperService employeeMapperService;

    @Autowired
    public EmployeeMapperRestController(EmployeeService employeeservice, EmployeeMapperService employeeMapperService) {
        this.employeeservice = employeeservice;
        this.employeeMapperService = employeeMapperService;
    }

    @GetMapping("/employeeMappers")
    public Map<Integer,List<Integer>> getAll(){
        return employeeMapperService.getAll();
    }

    @GetMapping("/employeeMappers/{employeeId}")
    public Map<Integer,List<Integer>> getManagers(@PathVariable int employeeId){
        return employeeMapperService.getRelation(employeeId);
    }

    @GetMapping("/employeeMappersId/{Id}")
    public  List<Integer> getById(@PathVariable int Id){
        return employeeMapperService.getById(Id);
    }

    @PostMapping("/employeeMappers")
    public EmployeeMapper addEmployeeMapper(@RequestBody EmployeeMapper employeeMapper) {

        return postAndPutUtility(employeeMapper,true);

    }

    @PutMapping("/employeeMappers")
    public EmployeeMapper updateEmployeeMapper(@RequestBody EmployeeMapper employeeMapper) {
        return postAndPutUtility(employeeMapper,false);
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

    public EmployeeMapper postAndPutUtility(EmployeeMapper employeeMapper, boolean post){
        Employee employee = employeeservice.getEmployee(employeeMapper.getEmployee().getId());
        Employee manager = employeeservice.getEmployee(employeeMapper.getManager().getId());

        if (employee == null || manager == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            if(post == true){
                employeeMapper.setId(0);
            }
            employeeMapper.setEmployee(employee);
            employeeMapper.setManager(manager);
            employeeMapperService.save(employee, employeeMapper);
            return employeeMapper;
        }
    }

}
