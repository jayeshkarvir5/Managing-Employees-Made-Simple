package com.EmployeeManagementSystem.demo.dao;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.Project;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository("ProjectDAO")
public class ProjectDAOImpl implements ProjectDAO{
    private EntityManager entityManager;

    @Autowired
    public ProjectDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Project> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Project> query = currentSession.createQuery("from Project", Project.class);

        List<Project> projects = query.getResultList();

        return projects;
    }

    @Override
    public Project getProject(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Project project = currentSession.get(Project.class, theId);

        return project;
    }

    @Override
    public void save(Project project) {
        Session currentSession = entityManager.unwrap(Session.class);
        Project newProject = project;

        if(newProject.getEmployees() == null){
            newProject.setEmployees(new ArrayList<Employee>());
        }

        currentSession.saveOrUpdate(newProject);
    }

    @Override
    public void deleteById(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query query = currentSession.createQuery("delete from Project where id=:theId");

        query.setParameter("theId", theId);

        query.executeUpdate();
    }
}
