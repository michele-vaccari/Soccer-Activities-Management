package com.sam.webapi.model;

public class JWT {
	private String token;

	public JWT(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}
