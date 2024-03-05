package com.pruthvi.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleDemoController {

	@GetMapping("/Admin")
	public String getAdminScreen() {
		return "This is Admin Screen ";
	}
	
	@GetMapping("/Staff")
	public String getStaffScreen() {
		return "This is Staff Screen";
	}
	
	@GetMapping("/Employee")
	public String getEmployeeScreen() {
		return "This is Employee Screen";
	}
}
