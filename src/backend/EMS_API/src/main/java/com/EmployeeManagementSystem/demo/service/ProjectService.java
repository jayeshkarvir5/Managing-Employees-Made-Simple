package com.EmployeeManagementSystem.demo.service;

import com.EmployeeManagementSystem.demo.entity.Project;

import java.util.List;

public interface ProjectService {
    public List<Project> getAll();

    public Project getProject(int theId);

    public void save(Project project);

    public void deleteById(int theId);
}
