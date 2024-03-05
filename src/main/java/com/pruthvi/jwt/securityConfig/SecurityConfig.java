package com.pruthvi.jwt.securityConfig;

import java.net.http.HttpRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsImpl userDetailService;
	
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	
	
	public SecurityConfig(UserDetailsImpl userDetailService, JWTAuthenticationFilter jwtAuthenticationFilter) {
		super();
		this.userDetailService = userDetailService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req-> req.requestMatchers("/login/**","/register/**").permitAll()
						.requestMatchers("/Admin/**").hasAuthority("ADMIN").requestMatchers("/Employee/**").hasAuthority("EMPLOYEE")
						.requestMatchers("/Staff/**").hasAuthority("STAFF")
						.anyRequest().authenticated())
						.userDetailsService(userDetailService)
					    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					    .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
					    .build();	
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
 
}
