package com.EmployeeManagementSystem.demo.dao;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;
import com.EmployeeManagementSystem.demo.entity.LeaveApplication;
import com.EmployeeManagementSystem.demo.service.BcryptService;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("LeaveApplicationDAO")
@CrossOrigin(origins = "http://localhost:4200/")
public class LeaveApplicationDAOImpl implements LeaveApplicationDAO {
    private EntityManager entityManager;

    @Autowired
    public LeaveApplicationDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<LeaveApplication> empLeave(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);

        //reverse hierarchy
        List<LeaveApplication> ans = getReverseHierarchy(currentSession, theId);

        return ans;
    }

    public List<LeaveApplication> getReverseHierarchy(Session currentSession, int managerId) {

        String nestedQuery = "from Employee where leaveApp = true AND id in" +
                "(select employee.id from EmployeeMapper where manager.id= :mangId)";

        Query<Employee> query = currentSession.createQuery(nestedQuery, Employee.class);
        query.setParameter("mangId", managerId);

        List<Employee> employeesLeaving = query.getResultList();
        List<LeaveApplication> ans = new ArrayList<LeaveApplication>();

        for (int i = 0; i < employeesLeaving.size(); i++) {
            int empId = employeesLeaving.get(i).getId();
            Query<LeaveApplication> queryla = currentSession.createQuery("from LeaveApplication where employee.id = :theId", LeaveApplication.class);
            queryla.setParameter("theId", empId);

            List<LeaveApplication> leaves = queryla.getResultList();
            leaves = listUtility(leaves);
            ans.addAll(leaves);
        }
        return ans;
    }

    @Override
    public List<LeaveApplication> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<LeaveApplication> query = currentSession.createQuery("from LeaveApplication", LeaveApplication.class);

        List<LeaveApplication> leaves = query.getResultList();
//        Map<Integer,List<Integer>> ans = mapUtility(leaves);
        leaves = listUtility(leaves);
        return leaves;
    }

    @Override
    public List<LeaveApplication> getById(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<LeaveApplication> query = currentSession.createQuery("from LeaveApplication where employee.id = :theId", LeaveApplication.class);
        query.setParameter("theId", theId);

        List<LeaveApplication> leaves = query.getResultList();
        leaves = listUtility(leaves);

        return leaves;
    }

    @Override
    public LeaveApplication getLeaveById(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);

        LeaveApplication leave = currentSession.get(LeaveApplication.class, theId);
        Employee e = leave.getEmployee();
        e.setProjects(null);
        e.setLeaveApplications(null);
        e.setEmployeeMappers(null);
        e.setPassword("");
        leave.setEmployee(e);
        return leave;
    }

    @Override
    public List<LeaveApplication> getLeaveApplicationByQuery(String searchQuery) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<LeaveApplication> query = currentSession.createQuery("from LeaveApplication where employee.firstName like :searchQuery or employee.lastName like :searchQuery",LeaveApplication.class);

        query.setParameter("searchQuery", "%"+searchQuery+"%");

        List<LeaveApplication> leaves = query.getResultList();
        leaves = listUtility(leaves);
        return leaves;
    }

    @Override
    public void save(LeaveApplication leaveApplication) {
        Session currentSession = entityManager.unwrap(Session.class);
        int id = leaveApplication.getEmployee().getId();
        Employee employee = currentSession.get(Employee.class, id);

        List<LeaveApplication> leaves = employee.getLeaveApplications();
        if(leaves == null){
            leaves = new ArrayList<LeaveApplication>();
        }
        leaves.add(leaveApplication);
        employee.setLeaveApplications(leaves);
        employee.setLeaveApp(true);
        leaveApplication.setEmployee(employee);

        currentSession.saveOrUpdate(employee);
        currentSession.saveOrUpdate(leaveApplication);
        System.out.println("******************************\n Password inside dao " +
                            employee.getPassword() +
                            "\n******************************\n");
    }

    @Override
    public void delete(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "delete from LeaveApplication where id= :theId";

        Query query = currentSession.createQuery(hql);

        query.setParameter("theId",theId);

//        Query<LeaveApplication> query2 = currentSession.createQuery(hql, LeaveApplication.class);
//        query2.setParameter("theId", theId);
//
//        List<LeaveApplication> leaves = query2.getResultList();
//
//        if(leaves==null || leaves.size()==0){
//            Employee employee = currentSession.get(Employee.class,theId);
//
//            employee.setLeaveApp(false);
//            currentSession.saveOrUpdate(employee);
//        }

        query.executeUpdate();
    }

    public Map<Integer,List<Integer>> mapUtility(List<LeaveApplication> leaveApplications){

        Map<Integer,List<Integer>> ans = new HashMap<Integer, List<Integer>>();
        for(int i=0;i<leaveApplications.size();i++){
            int employee = leaveApplications.get(i).getEmployee().getId();
//            int days = leaveApplications.get(i).getDays();
            List<Integer> list;
            if(!ans.containsKey(employee)){
                list = new ArrayList<Integer>();
            }else{
                list = ans.get(employee);
            }
//            list.add(days);
            ans.put(employee, list);
        }

        System.out.println("\n***********************\n" + ans + "\n**********************\n" );

        return ans;
    }

    public List<LeaveApplication> listUtility(List<LeaveApplication> leaves){
        for(int i=0;i<leaves.size();i++){
            Employee e = leaves.get(i).getEmployee();
            e.setProjects(null);
            e.setLeaveApplications(null);
            e.setEmployeeMappers(null);
            e.setPassword("");
            LeaveApplication la = new LeaveApplication();
            la.setEmployee(e);
            la.setId(leaves.get(i).getId());
//            la.setDays(leaves.get(i).getDays());
            la.setStartDate(leaves.get(i).getStartDate());
            la.setEndDate(leaves.get(i).getEndDate());
            la.setApproved(leaves.get(i).getApproved());
            leaves.set(i,la);
        }
        System.out.println(leaves);
        return leaves;
    }
}
