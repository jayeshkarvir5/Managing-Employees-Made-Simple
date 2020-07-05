package com.EmployeeManagementSystem.demo.service;

import com.EmployeeManagementSystem.demo.dao.ProjectDAO;
import com.EmployeeManagementSystem.demo.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{
    private ProjectDAO projectDAO;

    @Autowired
    public ProjectServiceImpl(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    @Transactional
    public List<Project> getAll() {
        return projectDAO.getAll();
    }

    @Override
    @Transactional
    public Project getProject(int theId) {
        return projectDAO.getProject(theId);
    }

    @Override
    @Transactional
    public void save(Project project) {
        projectDAO.save(project);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        projectDAO.deleteById(theId);
    }
}
