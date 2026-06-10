package com.company.books.backend.response;

public class TokenResponse {
	private String jwtToken;
	
	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	

	public TokenResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	
	
}
