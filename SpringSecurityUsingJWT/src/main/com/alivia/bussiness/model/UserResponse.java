package com.alivia.bussiness.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UserResponse {
	
	private String token;
	private String message;
	private String tokenExpirationTime;
	private String issuer;
	
	
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserResponse [token=");
		builder.append(token);
		builder.append(", message=");
		builder.append(message);
		builder.append(", tokenExpirationTime=");
		builder.append(tokenExpirationTime);
		builder.append(", issuer=");
		builder.append(issuer);
		builder.append("]");
		return builder.toString();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTokenExpirationTime() {
		return tokenExpirationTime;
	}
	public void setTokenExpirationTime(String tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}

}
