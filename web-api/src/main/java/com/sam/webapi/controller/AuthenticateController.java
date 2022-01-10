package com.sam.webapi.controller;

import com.sam.webapi.model.Authentication;
import com.sam.webapi.model.JWT;
import com.sam.webapi.service.JwtService;
import com.sam.webapi.service.JwtServiceException;
import com.sam.webapi.service.UserNotFoundException;
import com.sam.webapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Tag(name = "Authentication", description = "Authenticate a user")
public class AuthenticateController {

	@Autowired
	private UserService userService;

	@Operation(summary = "Authenticate a user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@RequestMapping(value = "/authenticate",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public JWT authenticateUser(@RequestBody(required = true) Authentication authentication) {

		try {
			var user = userService.getActiveUserByEmail(authentication.getEmail());

			if (!user.getPassword().equals(authentication.getPassword()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

			var jwtService = JwtService.getInstance();
			var token = jwtService.createJwt(user.getEmail(), user.getRole(), user.getName(), user.getSurname());
			return new JWT(token);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
		}
		catch(JwtServiceException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
