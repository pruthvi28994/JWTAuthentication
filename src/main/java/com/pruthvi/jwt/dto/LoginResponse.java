package com.pruthvi.jwt.dto;

import lombok.Data;

@Data
public class LoginResponse {

	private String status;
	
	private String token;
}
