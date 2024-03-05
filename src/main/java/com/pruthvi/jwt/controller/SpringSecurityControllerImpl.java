package com.pruthvi.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.pruthvi.jwt.dto.EmployeeResponse;
import com.pruthvi.jwt.dto.LoginResponse;
import com.pruthvi.jwt.entity.Employee;
import com.pruthvi.jwt.repository.EmployeeRepository;
import com.pruthvi.jwt.service.SpringSecurityService;

@RestController
public class SpringSecurityControllerImpl implements SpringSecurityController {
	
	public SpringSecurityService service;
	
	public SpringSecurityControllerImpl(SpringSecurityService service) {
		super();
		this.service = service;
	}

	@Override
	public ResponseEntity<EmployeeResponse> putEmployeeRegistration(Employee employee) {
		return ResponseEntity.status(HttpStatus.OK).body(service.putEmployeeRegistration(employee));
	}

	@Override
	public ResponseEntity<Employee> getEmployeeDetail(Integer employeeId) {
		return ResponseEntity.status(HttpStatus.OK).body(service.getEmployee(employeeId));
	}

	@Override
	public ResponseEntity<LoginResponse> loginUser(Employee request) {
		return ResponseEntity.status(HttpStatus.OK).body(service.login(request));
	}

}
