package com.EmployeeManagementSystem.demo.service;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;

import java.util.List;
import java.util.Map;

public interface EmployeeMapperService {
    public Map<Integer,List<Integer>> getAll();

    public Map<Integer,List<Integer>> getRelation(int theId);

    public void save(Employee employee, EmployeeMapper theEmployeeMapping);

    public void deleteById(int employee_id, int manager_id);

    public void deleteAllById(int employee_id);
}
