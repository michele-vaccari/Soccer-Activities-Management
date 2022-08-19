package com.sam.webapi.controller;

import com.sam.webapi.dto.AdminDto;
import com.sam.webapi.security.service.JwtService;
import com.sam.webapi.service.*;
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

@RestController
@Tag(name = "Admins", description = "Admins information retrieval, creation, modification, deactivation")
public class AdminController {

	private AdminService adminService;
	private JwtService jwtService;

	@Autowired
	public AdminController(AdminService adminService, JwtService jwtService) {
		this.adminService = adminService;
		this.jwtService = jwtService;
	}

	@Operation(summary = "Get a admin by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the admin",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = AdminDto.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Admin not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/admins/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public AdminDto getAdmin(@RequestHeader("Authorization") String authorization,
									   @PathVariable Integer id) {
		var adminEmail = jwtService.getEmail(authorization);

		try {
			return adminService.getAdmin(id, adminEmail);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (AdminUserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Update an existing admin by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Admin updated"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "404", description = "Admin not found"),
			@ApiResponse(responseCode = "409", description = "Admin update failed. Email has already been used") })
	@RequestMapping(value = "/admins/{id}",
			method = RequestMethod.PATCH,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateAdmin(@RequestHeader("Authorization") String authorization,
							@PathVariable Integer id,
							@RequestBody AdminDto admin) {
		var adminEmail = jwtService.getEmail(authorization);

		try {
			adminService.updateAdmin(id, admin, adminEmail);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (AdminUserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found", ex);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

}
