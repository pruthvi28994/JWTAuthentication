package com.pruthvi.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pruthvi.jwt.dto.EmployeeResponse;
import com.pruthvi.jwt.dto.LoginResponse;
import com.pruthvi.jwt.entity.Employee;
import com.pruthvi.jwt.repository.EmployeeRepository;
import com.pruthvi.jwt.securityConfig.JWTService;

@Service
public class SpringSecurityService {

	EmployeeRepository repo;
	
	PasswordEncoder encoder;
	
	JWTService jwtService;

	AuthenticationManager authManager;
	
	public SpringSecurityService(EmployeeRepository repo,PasswordEncoder encoder,JWTService jwtService,AuthenticationManager authManager) {
		super();
		this.repo = repo;
		this.encoder = encoder;
		this.jwtService = jwtService;
		this.authManager = authManager;
	}

	public EmployeeResponse putEmployeeRegistration(Employee employee) {
		var response = new EmployeeResponse();
		employee.setPassword(encoder.encode(employee.getPassword()));
		repo.save(employee);
		
		var token = jwtService.generateToken(employee);
		
		response.setStatus("Success");
		response.setToken(token);
		response.setMessage("Employee Save Successfully");
		response.setEmployeeDtl(employee);
		return response;
	}

	public Employee getEmployee(Integer employeeId) {
		return repo.findById(employeeId).get();
	}

	public LoginResponse login(Employee request) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		
		var userDtl = repo.findByEmail(request.getEmail());
		var token = jwtService.generateToken(userDtl);
		var authrReponse = new LoginResponse();
		authrReponse.setStatus("Success");
		authrReponse.setToken(token);
		return authrReponse;
	}
	
}
