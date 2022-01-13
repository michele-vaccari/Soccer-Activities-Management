package com.sam.webapi.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SamUserDetailsImpl extends User implements SamUserDetails {
	private String role;

	public SamUserDetailsImpl(String username, String password, String role, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.role = role;
	}

	@Override
	public String getRole() {
		return role;
	}
}