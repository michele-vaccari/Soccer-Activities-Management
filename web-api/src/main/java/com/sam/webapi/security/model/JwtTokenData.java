package com.sam.webapi.security.model;

public class JwtTokenData {
	private String email;
	private Integer id;
	private String role;
	private String name;
	private String surname;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() { return id; }

	public void setId(Integer id) { this.id = id; }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
