package com.pruthvi.jwt.dto;

import com.pruthvi.jwt.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

	private String status;
	
	private String message;
	
	private String token;
	
	private Employee employeeDtl;
}
