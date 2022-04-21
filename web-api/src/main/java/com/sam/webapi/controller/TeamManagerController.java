package com.sam.webapi.controller;

import com.sam.webapi.dto.TeamManagerDto;
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
@Tag(name = "Team managers", description = "Team managers information retrieval, creation, modification, deactivation")
public class TeamManagerController {

	private TeamManagerService teamManagerService;
	private JwtService jwtService;

	@Autowired
	public TeamManagerController(TeamManagerService teamManagerService, JwtService jwtService) {
		this.teamManagerService = teamManagerService;
		this.jwtService = jwtService;
	}

	@Operation(summary = "Get the list of team managers", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the team managers",
					content = { @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = TeamManagerDto.class))) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/teammanagers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<TeamManagerDto> getTeamManagers(@RequestHeader("Authorization") String authorization) {

		if (!jwtService.hasAnAdminUser(authorization))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		return teamManagerService.getTeamManagers();
	}

	@Operation(summary = "Get a team manager by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the team manager",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = TeamManagerDto.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Team manager not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/teammanagers/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public TeamManagerDto getTeamManager(@RequestHeader("Authorization") String authorization,
												   @PathVariable Integer id) {
		if (!jwtService.hasAnAdminUser(authorization))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		try {
			return teamManagerService.getTeamManager(id);
		}
		catch (TeamManagerNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Add a new team manager", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Team manager added"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "409", description = "Email has already been used") })
	@RequestMapping(value = "/teammanagers",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void addTeamManager(@RequestHeader("Authorization") String authorization,
							   @RequestBody(required = true) TeamManagerDto teamManagerDto) {
		if (!jwtService.hasAnAdminUser(authorization))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		var adminEmail = jwtService.getEmail(authorization);

		try {
			teamManagerService.createTeamManager(adminEmail, teamManagerDto);
		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

	@Operation(summary = "Update an existing team manager by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Team manager updated"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "404", description = "Team manager not found"),
			@ApiResponse(responseCode = "409", description = "Team manager update failed. Email has already been used") })
	@RequestMapping(value = "/teammanagers/{id}",
			method = RequestMethod.PATCH,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateTeamManager(@RequestHeader("Authorization") String authorization,
								  @PathVariable Integer id,
								  @RequestBody TeamManagerDto teamManagerDto) {
		if (!jwtService.hasAnAdminUser(authorization))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		try {
			teamManagerService.updateTeamManager(id, teamManagerDto);
		}
		catch (TeamManagerNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team manager not found", ex);
		}
		catch (RegisteredUserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registered user not found", ex);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

	@Operation(summary = "Disable a team manager by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Team manager disabled"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "404", description = "Team manager not found") })
	@RequestMapping(value = "/teammanagers/{id}",
			method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteTeamManager(@RequestHeader("Authorization") String authorization,
						   		 @PathVariable Integer id) {
		if (!jwtService.hasAnAdminUser(authorization))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		try {
			teamManagerService.deleteTeamManager(id);
		}
		catch (TeamManagerNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team manager not found", ex);
		}
		catch (RegisteredUserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registered user not found", ex);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
		}
	}
}
