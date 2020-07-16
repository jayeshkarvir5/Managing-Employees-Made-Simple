package com.EmployeeManagementSystem.demo.dao;

import com.EmployeeManagementSystem.demo.entity.Employee;
import com.EmployeeManagementSystem.demo.entity.EmployeeMapper;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("EmployeeMapperDAO")
public class EmployeeMapperDAOImpl implements EmployeeMapperDAO {

    private EntityManager entityManager;

    @Autowired
    public EmployeeMapperDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Map<Integer,List<Integer>> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<EmployeeMapper> query = currentSession.createQuery("from EmployeeMapper", EmployeeMapper.class);

        List<EmployeeMapper> employeeMappers = query.getResultList();
        currentSession.flush();
        currentSession.clear();
        Map<Integer,List<Integer>> ans = mapUtility(employeeMappers);

        return ans;
    }

    @Override
    public Map<Integer,List<Integer>> getRelation(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<EmployeeMapper> query = currentSession.createQuery("from EmployeeMapper where employee.id = :theId",
                                EmployeeMapper.class);
        query.setParameter("theId", theId);

        List<EmployeeMapper> employeeMappers = query.getResultList();
        currentSession.flush();
        currentSession.clear();
        Map<Integer,List<Integer>> ans = mapUtility(employeeMappers);

        return ans;
    }

    @Override
    public List<Integer>  getById(int theId) {
        Session currentSession = entityManager.unwrap(Session.class);

        EmployeeMapper employeeMapper = currentSession.get(EmployeeMapper.class, theId);
        List<Integer> ans = new ArrayList<Integer>();
        ans.add(employeeMapper.getEmployee().getId());
        ans.add(employeeMapper.getManager().getId());
        return ans;
    }

    @Override
    public void save(Employee employee, EmployeeMapper theEmployeeMapping) {
        Session currentSession = entityManager.unwrap(Session.class);

        List<EmployeeMapper> managers = employee.getEmployeeMappers();
        if(managers == null){
            managers = new ArrayList<EmployeeMapper>();
        }
        managers.add(theEmployeeMapping);
        employee.setEmployeeMappers(managers);

        currentSession.saveOrUpdate(employee);
        currentSession.saveOrUpdate(theEmployeeMapping);
    }

    @Override
    public void deleteById(int employee_id, int manager_id) {
        Session currentSession = entityManager.unwrap(Session.class);

        String hql = "delete from EmployeeMapper where employee.id=:employeeId and manager.id = :managerId";

        Query query = currentSession.createQuery(hql);

        query.setParameter("employeeId",employee_id);
        query.setParameter("managerId",manager_id);
        query.executeUpdate();
    }

    @Override
    public void deleteAllById(int employee_id) {
        Session currentSession = entityManager.unwrap(Session.class);
        String hql = "delete from EmployeeMapper where employee.id=:employeeId or manager.id = :employeeId";

        Query query = currentSession.createQuery(hql);

        query.setParameter("employeeId",employee_id);

        query.executeUpdate();
    }

    public Map<Integer,List<Integer>> mapUtility(List<EmployeeMapper> employeeMappers){

        Map<Integer,List<Integer>> ans = new HashMap<Integer, List<Integer>>();
        for(int i=0;i<employeeMappers.size();i++){
            int employee = employeeMappers.get(i).getEmployee().getId();
            int manager = employeeMappers.get(i).getManager().getId();
            List<Integer> list;
            if(!ans.containsKey(employee)){
                list = new ArrayList<Integer>();
            }else{
                list = ans.get(employee);
            }
            list.add(manager);
            ans.put(employee, list);
        }

        System.out.println("\n***********************\n" + ans + "\n**********************\n" );

        return ans;
    }
}
