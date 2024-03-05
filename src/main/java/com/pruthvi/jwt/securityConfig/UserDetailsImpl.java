package com.pruthvi.jwt.securityConfig;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pruthvi.jwt.repository.EmployeeRepository;

@Service
public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	EmployeeRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.of(userRepo.findByEmail(username)).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
	}

}
