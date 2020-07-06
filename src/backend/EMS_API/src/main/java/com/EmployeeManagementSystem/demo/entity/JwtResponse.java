package com.EmployeeManagementSystem.demo.entity;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final Employee employee;

	public JwtResponse(String jwttoken, Employee employee) {
		this.jwttoken = jwttoken;
		this.employee = employee;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public Employee getEmployee() {
		return employee;
	}
}