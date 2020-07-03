package com.EmployeeManagementSystem.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
//	@Column(name="employee_id")
//	private int employee_id;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	private String email;

	@Column(name = "leaveapp")
	private boolean leaveApp;

//	@JsonInclude()
	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<EmployeeMapper> employeeMappers;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<LeaveApplication> leaveApplications;

	public Employee() {
		
	}

	public Employee(int id, String designation, String firstName, String lastName, String address, String email, boolean leaveApp) {
		this.id = id;
		this.designation = designation;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.leaveApp = leaveApp;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLeaveApp() {
		return leaveApp;
	}

	public void setLeaveApp(boolean leaveApp) {
		this.leaveApp = leaveApp;
	}

	public List<EmployeeMapper> getEmployeeMappers() {
		return employeeMappers;
	}

	public void setEmployeeMappers(List<EmployeeMapper> employeeMappers) {
		this.employeeMappers = employeeMappers;
	}

	public List<LeaveApplication> getLeaveApplications() {
		return leaveApplications;
	}

	public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
		this.leaveApplications = leaveApplications;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", designation='" + designation + '\'' +
				", firstName='" + firstName + '\'' +
				'}';
	}
}










