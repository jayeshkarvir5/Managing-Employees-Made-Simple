package com.EmployeeManagementSystem.demo.service;

import com.EmployeeManagementSystem.demo.dao.EmployeeDAO;
import com.EmployeeManagementSystem.demo.dao.EmployeeMapperDAO;
import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeMapperServiceImpl implements EmployeeMapperService{

    private EmployeeMapperDAO employeeMapperDAO;

    @Autowired
    public EmployeeMapperServiceImpl(EmployeeMapperDAO employeeMapperDAO) {
        this.employeeMapperDAO = employeeMapperDAO;
    }

    @Override
    @Transactional
    public Map<Integer,List<Integer>> getAll() {
        return employeeMapperDAO.getAll();
    }

    @Override
    @Transactional
    public Map<Integer,List<Integer>> getRelation(int theId) {
        return employeeMapperDAO.getRelation(theId);
    }

    @Override
    @Transactional
    public List<Integer> getById(int theId) {
        return employeeMapperDAO.getById(theId);
    }

    @Override
    @Transactional
    public void save(Employee employee, EmployeeMapper theEmployeeMapping) {
        employeeMapperDAO.save(employee, theEmployeeMapping);
    }

    @Override
    @Transactional
    public void deleteById(int employee_id, int manager_id) {
        employeeMapperDAO.deleteById(employee_id, manager_id);
    }

    @Override
    @Transactional
    public void deleteAllById(int employee_id) {
        employeeMapperDAO.deleteAllById(employee_id);
    }
}
