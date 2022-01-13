package com.sam.webapi.controller;

import com.sam.webapi.model.Authentication;
import com.sam.webapi.model.JWT;
import com.sam.webapi.security.service.JwtService;
import com.sam.webapi.security.service.JwtServiceException;
import com.sam.webapi.service.UserNotFoundException;
import com.sam.webapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Tag(name = "Authentication", description = "Authenticate a user")
public class AuthenticateController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Operation(summary = "Authenticate a user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = JWT.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error",
					content = @Content) })
	@RequestMapping(value = "/authenticate",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Optional<JWT> authenticateUser(@RequestBody(required = true) Authentication authentication) {

		try {
			var user = userService.getActiveUserByEmail(authentication.getEmail());

			if (!user.getPassword().equals(authentication.getPassword()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

			var token = jwtService.createJwt(user.getEmail(), user.getRole(), user.getName(), user.getSurname());
			return Optional.of(new JWT(token));
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
		}
		catch(JwtServiceException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
