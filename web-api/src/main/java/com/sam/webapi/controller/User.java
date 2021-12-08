package com.sam.webapi.controller;

import com.sam.webapi.dataaccess.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class User {

	private static final Logger log = LoggerFactory.getLogger(User.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<com.sam.webapi.model.User> getUsers() {
		Iterable users = userRepository.findAll();
		return users;
	}

}