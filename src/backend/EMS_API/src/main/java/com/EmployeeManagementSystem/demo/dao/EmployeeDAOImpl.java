package com.EmployeeManagementSystem.demo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Override
	public List<Employee> getAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Employee> query = currentSession.createQuery("from Employee", Employee.class);
		
		List<Employee> employees = query.getResultList();
				
		return employees;
	}


	@Override
	public Employee getEmployee(int Id) {

		Session currentSession = entityManager.unwrap(Session.class);
		
		Employee employee = currentSession.get(Employee.class, Id);
		
		return employee;
	}


	@Override
	public void save(Employee employee) {

		Session currentSession = entityManager.unwrap(Session.class);
	
		currentSession.saveOrUpdate(employee);
	}


	@Override
	public void deleteById(int Id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
				
		Query query = currentSession.createQuery("delete from Employee where id=:employeeId");
		
		query.setParameter("employeeId", Id);
		
		query.executeUpdate();
	}


	@Override
	public List<Employee> getEmployeeByQuery(String searchQuery) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Employee> query = currentSession.createQuery("from Employee where first_name like :searchQuery or last_name like :searchQuery",Employee.class);
		
		query.setParameter("searchQuery", "%"+searchQuery+"%");
		
		List<Employee> employees = query.getResultList();
		
		return employees;
		
	}
	
	@Override
	public Map<Integer,List<Integer>> getEmployeeHeirarchy(int employeeId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Map<Integer,List<Integer>> mappings = new HashMap<Integer,List<Integer>>();
		
		mappings = getHeirarchy(currentSession,mappings,employeeId);
		System.out.println(mappings);
		
		return mappings;
	}
	
	public Map<Integer,List<Integer>> getHeirarchy(Session currentSession, Map<Integer,List<Integer>> mappings,int empId) {
	
		Query<EmployeeMapper> query = currentSession.createQuery("from EmployeeMapper where emp_id like :empId",EmployeeMapper.class);
		query.setParameter("empId", empId);
		List<EmployeeMapper> employeeMapper = query.getResultList();
		
		for(int i=0;i<employeeMapper.size();i++) {
			int managerId = employeeMapper.get(i).getManager_id();
			if(!mappings.containsKey(empId)) {
				mappings.put(empId,new ArrayList<Integer>());
			}
			mappings.get(empId).add(managerId);
			System.out.println("here in else "+mappings);
				
			getHeirarchy(currentSession,mappings,managerId);
		}
		
		return mappings;
	}
	
}
