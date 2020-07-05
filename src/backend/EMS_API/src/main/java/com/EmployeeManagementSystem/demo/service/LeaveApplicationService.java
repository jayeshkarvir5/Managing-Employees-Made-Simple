package com.EmployeeManagementSystem.demo.service;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.LeaveApplication;

import java.util.List;
import java.util.Map;

public interface LeaveApplicationService {
    public Map<Integer,List<Integer>> getAll();

    public Map<Integer,List<Integer>> getById(int theId);

    public void save(Employee employee, LeaveApplication leaveApplication);

    public void delete(int id);
}
