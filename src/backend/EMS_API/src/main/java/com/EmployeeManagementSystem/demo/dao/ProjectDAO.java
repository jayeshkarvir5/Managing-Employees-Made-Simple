package com.EmployeeManagementSystem.demo.dao;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.Project;

import java.util.List;
import java.util.Map;

public interface ProjectDAO {
    public List<Project> getAll();

    public Project getProject(int theId);

    public void save(Project project);

    public void deleteById(int theId);
}
