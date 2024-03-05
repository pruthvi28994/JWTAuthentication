package com.pruthvi.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pruthvi.jwt.dto.EmployeeResponse;
import com.pruthvi.jwt.dto.LoginResponse;
import com.pruthvi.jwt.entity.Employee;

@Validated
public interface SpringSecurityController {

	@PostMapping(path = "/register", consumes = "application/json")
	public ResponseEntity<EmployeeResponse> putEmployeeRegistration(@RequestBody Employee employee);

	@PostMapping(path="/login")
	public ResponseEntity<LoginResponse> loginUser(@RequestBody Employee request);
	
	@GetMapping(path = "/{employeeId}/employee")
	public ResponseEntity<Employee> getEmployeeDetail(@PathVariable Integer employeeId);
}
