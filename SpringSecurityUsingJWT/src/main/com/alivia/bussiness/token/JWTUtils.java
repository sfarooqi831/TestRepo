package com.alivia.bussiness.token;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {

	
	
	@Value("${app.screet-key}")
	private String screetKey;

	@Value("${app.token-issuer}")
	private String issuer;

	// Generate a JWT token
	public String generateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuer(issuer).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
				.signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();
	}

	// Retrieve claims from the JWT token
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
	}

	// Get the expiration date of the token
	public Date getTokeExpiryDate(String token) {
		return getClaims(token).getExpiration();
	}

	// Get the expiration time in minutes
	public long getExpiryMinutes(String token) {
		Date expiryDate = getTokeExpiryDate(token);
		long differenceInMillis = expiryDate.getTime() - new Date(System.currentTimeMillis()).getTime();
		 return TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);	}

	// Get the subject from the token
	public <T> T getSubject(String token,Function<Claims,T> claimReslover) {
		return claimReslover.apply(getClaims(token));
	}

	// Check if the token is expired
	public boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	// Validate the token by comparing the userName (DB) and checking expiration
	public boolean validateToken(String token, String userName) {
		String tokenUserName = getSubject(token,claims->claims.getSubject());
		return (userName.equalsIgnoreCase(tokenUserName) && !isTokenExpired(token));
	}
	// Convert the base64-encoded secret key to a Key object
	private byte[] getSigningKey() {
		return Base64.getEncoder().encode(screetKey.getBytes());
	}
}
