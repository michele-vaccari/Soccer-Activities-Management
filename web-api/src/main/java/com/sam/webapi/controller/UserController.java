package com.sam.webapi.controller;

import com.sam.webapi.model.User;
import com.sam.webapi.service.SingleEmailConstraintException;
import com.sam.webapi.service.UserNotFoundException;
import com.sam.webapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Tag(name = "Users", description = "Users information retrieval, creation, modification, deactivation")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Get the list of users", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the users",
					content = { @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = User.class))) }) })
	@RequestMapping(value = "/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<User> getUsers() {

		return userService.getUsers();
	}

	@Operation(summary = "Get a user by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the user",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found",
					content = @Content) })
	@RequestMapping(value = "/users/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Optional<User> getUser(@PathVariable Integer id) {
		var user = userService.getUser(id);

		if (user.isPresent())
			return user;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Add a new user", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User added"),
			@ApiResponse(responseCode = "409", description = "Email has already been used") })
	@RequestMapping(value = "/users",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void addUser(@RequestBody(required = true) User user) {
		try {
			userService.createUser(user);
		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

	@Operation(summary = "Update an existing user by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User updated"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "409", description = "User update failed. Email has already been used") })
	@RequestMapping(value = "/users/{id}",
			method = RequestMethod.PATCH,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(@PathVariable Integer id,
						   @RequestBody User user) {
		try {
			userService.updateUser(id, user);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

	@Operation(summary = "Disable a user by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User disabled"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	@RequestMapping(value = "/users/{id}",
			method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable Integer id) {
		try {
			userService.deleteUser(id);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
		}
	}

}
