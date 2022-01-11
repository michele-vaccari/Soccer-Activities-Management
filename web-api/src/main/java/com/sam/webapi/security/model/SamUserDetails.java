package com.sam.webapi.security.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface SamUserDetails extends UserDetails {
	String getRole();
}