package com.pruthvi.jwt.securityConfig;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	
	private final JWTService jwtService;
	
	private final UserDetailsImpl userDtlService;
	
	public JWTAuthenticationFilter(JWTService jwtService, UserDetailsImpl userDtlService) {
		super();
		this.jwtService = jwtService;
		this.userDtlService = userDtlService;
	}


	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		String authheader = request.getHeader("Authorization");
		if(authheader==null || !authheader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
	
		System.out.println("Entered Token is ::::"+authheader);
		String token = authheader.substring(7);
		var userName = jwtService.extractUserName(token);
		System.out.println("Security Context Holder :::"+SecurityContextHolder.getContext().getAuthentication());
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetail = userDtlService.loadUserByUsername(userName);
			if(jwtService.isValidToken(token, userDetail)) {
				
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
