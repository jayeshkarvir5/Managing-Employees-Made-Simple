package com.EmployeeManagementSystem.demo.entity;


import javax.persistence.*;

@Entity
@Table(name="empmapper")
public class EmployeeMapper{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="emp_id")
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="mang_id")
	private Employee manager;

	public EmployeeMapper() { }

	public EmployeeMapper(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "EmployeeMapper{" +
				"id=" + id +
				", employee=" + employee +
				", manager=" + manager +
				'}';
	}

//	@Override
//	public String toString() {
//		return "id=" + id;
//	}
}
