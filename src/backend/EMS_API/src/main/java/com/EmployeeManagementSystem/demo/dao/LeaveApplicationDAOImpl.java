package com.EmployeeManagementSystem.demo.dao;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;
import com.EmployeeManagementSystem.demo.entity.LeaveApplication;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("LeaveApplicationDAO")
public class LeaveApplicationDAOImpl implements LeaveApplicationDAO {
    private EntityManager entityManager;

    @Autowired
    public LeaveApplicationDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<LeaveApplication> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<LeaveApplication> query = currentSession.createQuery("from LeaveApplication", LeaveApplication.class);

        List<LeaveApplication> leaves = query.getResultList();
//        Map<Integer,List<Integer>> ans = mapUtility(leaves);
        for(int i=0;i<leaves.size();i++){
            Employee e = leaves.get(i).getEmployee();
            e.setProjects(null);
            e.setLeaveApplications(null);
            e.setEmployeeMappers(null);
            e.setPassword("");
            LeaveApplication la = new LeaveApplication();
            la.setEmployee(e);
            la.setId(leaves.get(i).getId());
            la.setDays(leaves.get(i).getDays());
            la.setApproved(leaves.get(i).getApproved());
            leaves.set(i,la);
        }
        System.out.println(leaves);
        return leaves;
    }

    @Override
    public Map<Integer,List<Integer>> getById(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<LeaveApplication> query = currentSession.createQuery("from LeaveApplication where employee.id = :theId", LeaveApplication.class);
        query.setParameter("theId", theId);

        List<LeaveApplication> leaves = query.getResultList();
        Map<Integer,List<Integer>> ans = mapUtility(leaves);

        return ans;
    }

    @Override
    public void save(Employee employee, LeaveApplication leaveApplication) {
        Session currentSession = entityManager.unwrap(Session.class);

        List<LeaveApplication> leaves = employee.getLeaveApplications();
        if(leaves == null){
            leaves = new ArrayList<LeaveApplication>();
        }
        leaves.add(leaveApplication);
        employee.setLeaveApplications(leaves);
        employee.setLeaveApp(true);
        currentSession.saveOrUpdate(employee);
        currentSession.saveOrUpdate(leaveApplication);
    }

    @Override
    public void delete(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "delete from LeaveApplication where employee.id= :theId";

        Query query = currentSession.createQuery(hql);

        query.setParameter("theId",theId);
        Employee employee = currentSession.get(Employee.class,theId);
        employee.setLeaveApp(false);
        currentSession.saveOrUpdate(employee);
        query.executeUpdate();
    }

    public Map<Integer,List<Integer>> mapUtility(List<LeaveApplication> leaveApplications){

        Map<Integer,List<Integer>> ans = new HashMap<Integer, List<Integer>>();
        for(int i=0;i<leaveApplications.size();i++){
            int employee = leaveApplications.get(i).getEmployee().getId();
            int days = leaveApplications.get(i).getDays();
            List<Integer> list;
            if(!ans.containsKey(employee)){
                list = new ArrayList<Integer>();
            }else{
                list = ans.get(employee);
            }
            list.add(days);
            ans.put(employee, list);
        }

        System.out.println("\n***********************\n" + ans + "\n**********************\n" );

        return ans;
    }
}
