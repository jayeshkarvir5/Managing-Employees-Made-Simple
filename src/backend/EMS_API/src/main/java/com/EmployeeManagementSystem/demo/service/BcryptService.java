package com.EmployeeManagementSystem.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptService {
	
	public static BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
}
