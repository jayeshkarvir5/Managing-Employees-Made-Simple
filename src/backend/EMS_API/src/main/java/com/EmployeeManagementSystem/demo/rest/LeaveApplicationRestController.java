package com.EmployeeManagementSystem.demo.rest;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;
import com.EmployeeManagementSystem.demo.entity.LeaveApplication;
import com.EmployeeManagementSystem.demo.service.EmployeeService;
import com.EmployeeManagementSystem.demo.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class LeaveApplicationRestController {
    /**
     * Important Note: Use apis for inserting or deleting from leave table.
     * Tips on using the api.
     * 1.Get and delete apis are straight forward.
     * 2.Post must be used for creating and put for updating.
     * 3.For put and post field names should be corresponding hibernate mapping names.
     * Use following format:-
     * {    "id":id,
     *      "employee":{"id":id},
     *      "days":days
     *      "approved":approved }
     *  json data like above will work. Api will take care of
     *  saving the correct employee.Any id for post will work but
     *  it will saved according to the strategy followed. For put
     *  the correct id needs to be specified.
     *  If id does not exists for put then an error will be thrown.
     *  for e.g.
     *      {
     *          "id":10,
     *          "employee":{
     *              "id":4
     *          },
     *          "days":2,
     *          "approved":false
     *      }
     */
    private EmployeeService employeeservice;
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    public LeaveApplicationRestController(EmployeeService employeeservice, LeaveApplicationService leaveApplicationService) {
        this.employeeservice = employeeservice;
        this.leaveApplicationService = leaveApplicationService;
    }

    @GetMapping("/leaveapplications")
    public List<LeaveApplication> getAll(){
        return leaveApplicationService.getAll();
    }

    @GetMapping("/leaveapplications/{employeeId}")
    public List<LeaveApplication> getById(@PathVariable int employeeId){
        Employee employee = employeeservice.getEmployee(employeeId);
        if (employee == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            return leaveApplicationService.getById(employeeId);
        }

    }

    @GetMapping("/leaveapplications/{employeeId}/leave")
    public List<LeaveApplication> empLeave(@PathVariable int employeeId){
        Employee employee = employeeservice.getEmployee(employeeId);
        if (employee == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            return leaveApplicationService.empLeave(employeeId);
        }

    }

    @GetMapping("/leaveapplicationsbyid/{id}")
    public LeaveApplication getLeave(@PathVariable int id){
        return leaveApplicationService.getLeaveById(id);
    }

    @RequestMapping(value="leaveapplications/search", method = RequestMethod.GET)
    public List<LeaveApplication> searchLeaves(@RequestParam("q") String query) {

        return leaveApplicationService.getLeaveApplicationByQuery(query);
    }

    @PostMapping("/leaveapplications")
    public LeaveApplication saveLeaveApplication(@RequestBody LeaveApplication leaveApplication) {
        return postAndPutUtility(leaveApplication, true);
    }

    @PutMapping("/leaveapplications")
    public LeaveApplication updateLeaveApplication(@RequestBody LeaveApplication leaveApplication) {
        return postAndPutUtility(leaveApplication, false);
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

    public LeaveApplication postAndPutUtility(LeaveApplication leaveApplication, boolean post){
        Employee employee = employeeservice.getEmployee(leaveApplication.getEmployee().getId());

        if (employee == null) {
//            throw new RuntimeException("Not found - ");
            System.out.println("****************\nNot found\n****************");
            return null;
        }else{
            if(post == true){
                leaveApplication.setId(0);
                List<LeaveApplication> leaves = employee.getLeaveApplications();
                if(leaves == null){
                    leaves = new ArrayList<LeaveApplication>();
                }
                leaves.add(leaveApplication);
                employee.setLeaveApplications(leaves);
                employee.setLeaveApp(true);
                employeeservice.save(employee);
                leaveApplication.setEmployee(employee);
            }
            System.out.println("Inside controller Employee password is "+employee.getPassword());
            leaveApplicationService.save(leaveApplication);
            return leaveApplication;
        }
    }
}
