package com.EmployeeManagementSystem.demo.dao;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.LeaveApplication;

import java.util.List;
import java.util.Map;

public interface LeaveApplicationDAO {
    public List<LeaveApplication> getAll();

    public List<LeaveApplication> empLeave(int theId);

    public List<LeaveApplication> getById(int theId);

    public LeaveApplication getLeaveById(int theId);

    public List<LeaveApplication> getLeaveApplicationByQuery(String searchQuery);

    public void save(LeaveApplication leaveApplication);

    public void delete(int id);
}
