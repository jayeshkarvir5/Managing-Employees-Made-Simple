package com.EmployeeManagementSystem.demo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.EmployeeManagementSystem.demo.entity.LeaveApplication;
import com.EmployeeManagementSystem.demo.entity.Project;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;

@Repository("EmployeeDAO")
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
	public List<Integer> empLeave(int managerId) {

		Session currentSession = entityManager.unwrap(Session.class);

		//reverse hierarchy
		List<Integer> ans;

		ans = getReverseHierarchy(currentSession, managerId);

		return ans;
	}

	public List<Integer> getReverseHierarchy(Session currentSession, int managerId) {

		String nestedQuery = "from Employee where leaveApp = true AND id in" +
							 "(select employee.id from EmployeeMapper where manager.id= :mangId)";

		Query<Employee> query = currentSession.createQuery(nestedQuery, Employee.class);
		query.setParameter("mangId", managerId);

		List<Employee> employeesLeaving = query.getResultList();
		List<Integer> ans = new ArrayList<Integer>();

		for (int i = 0; i < employeesLeaving.size(); i++) {
			int empId = employeesLeaving.get(i).getId();
			ans.add(empId);
		}
		return ans;
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
		Employee newEmployee = employee;

		if(newEmployee.getEmployeeMappers() == null){
			newEmployee.setEmployeeMappers(new ArrayList<EmployeeMapper>());
		}
		if(newEmployee.getLeaveApplications() == null){
			newEmployee.setLeaveApplications(new ArrayList<LeaveApplication>());
		}
		if(newEmployee.getProjects() == null){
			newEmployee.setProjects(new ArrayList<Project>());
		}

		currentSession.saveOrUpdate(newEmployee);
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
	public Map<Integer,List<Integer>> getEmployeeHierarchy(int employeeId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Map<Integer,List<Integer>> mappings = new HashMap<Integer,List<Integer>>();
		int level = 3;

		mappings = getHierarchy(currentSession,mappings,employeeId, level);

		System.out.println(mappings);


		return mappings;
	}
	
	public Map<Integer,List<Integer>> getHierarchy(Session currentSession, Map<Integer,List<Integer>> mappings,
												   int empId, int level) {
		/**
		 * levels not over and nor already mapped
		 * call recursively for 3 levels
		 * when empid and managerid matches CEO level is reached
		 */

		if(level>0 && !mappings.containsKey(empId)) {

			Query<EmployeeMapper> query = currentSession.createQuery("from EmployeeMapper where employee.id = :empId", EmployeeMapper.class);
			query.setParameter("empId", empId);

			List<EmployeeMapper> employeeMapper = query.getResultList();

			for (int i = 0; i < employeeMapper.size(); i++) {
				int managerId = employeeMapper.get(i).getManager().getId();
				List<Integer> arr;

				if(managerId != empId) {
					if (!mappings.containsKey(empId)) {
						arr = new ArrayList<Integer>();
					}else{
						arr = mappings.get(empId);
					}

					arr.add(managerId);
					mappings.put(empId, arr);

					System.out.println("here in else " + mappings);

					getHierarchy(currentSession,mappings,managerId, level-1);
				}else{  // CEO
					getHierarchy(currentSession,mappings,managerId, 0);
				}
			}
		}
		return mappings;
	}
	
}
