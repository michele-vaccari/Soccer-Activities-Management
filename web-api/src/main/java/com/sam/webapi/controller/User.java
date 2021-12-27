package com.sam.webapi.controller;

import com.sam.webapi.service.SingleEmailConstraintException;
import com.sam.webapi.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class User {

	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<com.sam.webapi.model.User> getUsers() {
		return userService.getUsers();
	}

	@RequestMapping(value = "/users/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Optional<com.sam.webapi.model.User> getUser(@PathVariable Integer id) {
		var user = userService.getUser(id);

		if (user.isPresent())
			return user;
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist");
	}

	@RequestMapping(value = "/users",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void addUser(@RequestBody com.sam.webapi.model.User user) {
		try {
			userService.createUser(user);
		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

	@RequestMapping(value = "/users",
			method = RequestMethod.PATCH,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(@RequestBody com.sam.webapi.model.User user) {
		try {
			userService.updateUser(user);
		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

	@RequestMapping(value = "/users/{id}",
			method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
	}

}
