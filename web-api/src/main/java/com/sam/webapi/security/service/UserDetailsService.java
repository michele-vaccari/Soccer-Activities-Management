package com.sam.webapi.security.service;

import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.security.model.SamUserDetailsImpl;
import com.sam.webapi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		var userService = new UserServiceImpl(userRepository);
		var user = userService.getActiveUserByEmail(username);

		if (user == null)
			throw new UsernameNotFoundException("User not found with username: " + username);

		return new SamUserDetailsImpl(user.getEmail(), user.getPassword(), user.getRole(), new ArrayList<>());
	}
}
