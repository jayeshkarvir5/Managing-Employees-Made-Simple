package com.EmployeeManagementSystem.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_mapper")
public class EmployeeMapper {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="emp_id")
	private int emp_id;
	
	@Column(name="manager_id")
	private int manager_id;
	
	public EmployeeMapper() { }

	public EmployeeMapper(int emp_id, int manager_id) {
		this.emp_id = emp_id;
		this.manager_id = manager_id;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}
	
	
}
