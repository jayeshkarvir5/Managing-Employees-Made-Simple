package com.EmployeeManagementSystem.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
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

	@Column(name = "password")
	private String password;

	@Column(name = "leaveapp")
	private boolean leaveApp;

	@Column(name = "experience")
	private int experience;

	@Column(name = "techstack")
	private String techstack;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<EmployeeMapper> employeeMappers;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<LeaveApplication> leaveApplications;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "employee_project",
			joinColumns = @JoinColumn(name = "emp_id"),
			inverseJoinColumns = @JoinColumn(name = "project_id"))
	@JsonIgnoreProperties("employees")
	private List<Project> projects;

	public Employee() {
		
	}

	public Employee(int id, String designation, String firstName, String lastName, String address, String email, String password, boolean leaveApp, int experience, String techstack, List<EmployeeMapper> employeeMappers, List<LeaveApplication> leaveApplications, List<Project> projects) {
		this.id = id;
		this.designation = designation;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.password = password;
		this.leaveApp = leaveApp;
		this.experience = experience;
		this.techstack = techstack;
		this.employeeMappers = employeeMappers;
		this.leaveApplications = leaveApplications;
		this.projects = projects;
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

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getTechstack() {
		return techstack;
	}

	public void setTechstack(String techstack) {
		this.techstack = techstack;
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

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project project){
		if(projects == null){
			projects = new ArrayList<Project>();
		}
		projects.add(project);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	@Override
//	public String toString() {
//		return "Employee{" +
//				"id=" + id +
//				", designation='" + designation + '\'' +
//				", firstName='" + firstName + '\'' +
//				", lastName='" + lastName + '\'' +
//				", address='" + address + '\'' +
//				", email='" + email + '\'' +
//				", password='" + password + '\'' +
//				", leaveApp=" + leaveApp +
//				", experience=" + experience +
//				", techstack='" + techstack + '\'' +
//				", employeeMappers=" + employeeMappers +
//				", leaveApplications=" + leaveApplications +
//				", projects=" + projects +
//				'}';
//	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", designation='" + designation + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}










