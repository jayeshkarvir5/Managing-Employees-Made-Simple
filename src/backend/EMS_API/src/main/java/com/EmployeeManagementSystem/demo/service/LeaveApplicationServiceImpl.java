package com.EmployeeManagementSystem.demo.service;

import com.EmployeeManagementSystem.demo.dao.LeaveApplicationDAO;
import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.LeaveApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService{
    LeaveApplicationDAO leaveApplicationDAO;

    @Autowired
    public LeaveApplicationServiceImpl(LeaveApplicationDAO leaveApplicationDAO) {
        this.leaveApplicationDAO = leaveApplicationDAO;
    }

    @Override
    @Transactional
    public List<LeaveApplication> getAll() {
        return leaveApplicationDAO.getAll();
    }

    @Override
    @Transactional
    public List<LeaveApplication> getById(int theId) {
        return leaveApplicationDAO.getById(theId);
    }

    @Override
    public List<LeaveApplication> getLeaveApplicationByQuery(String searchQuery) {
        return leaveApplicationDAO.getLeaveApplicationByQuery(searchQuery);
    }

    @Override
    @Transactional
    public void save(Employee employee, LeaveApplication leaveApplication) {
        leaveApplicationDAO.save(employee, leaveApplication);
    }

    @Override
    @Transactional
    public void delete(int id) {
        leaveApplicationDAO.delete(id);
    }
}
