package com.pruthvi.jwt.securityConfig;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.pruthvi.jwt.entity.Employee;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	private final String SECRET_KEY = "1f66d06f45a01e2f8df35cd8e7bb2d812912b893b481170abc7b36a202bc27a5";
	
	public String extractUserName(String token) {
		return extractClamin(token, Claims::getSubject);
	}
	
	public Boolean isValidToken(String token,UserDetails user) {
		String userName = extractUserName(token);
		System.out.println("UserDetais ::"+user.getUsername()+ " ,,,,"+user);
		return (userName.equals(user.getUsername()) && !isTokenExpired(token));
	}
	
	public boolean isTokenExpired(String token) {
		var expirationTime = extractClamin(token, Claims::getExpiration);
		var isTokenValid = expirationTime.before(new Date());
		return isTokenValid;
	}

	public <T> T extractClamin(String token,Function<Claims, T> resolver) {
		Claims claimns = extractAllClamins(token);
		System.out.println("All Clamins :::: "+claimns);
		return resolver.apply(claimns);
	}
	
	public Claims extractAllClamins(String token) {
		return Jwts.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String generateToken(Employee user) {
		String token = Jwts.builder().subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))  //Issued Today
				.expiration(new Date(System.currentTimeMillis()  + (24*60*60*1000)))  //Add Expiration Time Current Day + Next Day (in this Example)
				.signWith(getSigninKey())  //Add Secret Keys
				.compact();
		
		return token;
	}

	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
