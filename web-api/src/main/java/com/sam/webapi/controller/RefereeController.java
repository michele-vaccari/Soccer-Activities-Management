package com.sam.webapi.controller;

import com.sam.webapi.dto.RefereeDto;
import com.sam.webapi.dto.ShortReportDto;
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
@Tag(name = "Referees", description = "Referees information retrieval, creation, modification, deactivation")
public class RefereeController {

	private RefereeService refereeService;
	private JwtService jwtService;

	@Autowired
	public RefereeController(RefereeService refereeService, JwtService jwtService) {
		this.refereeService = refereeService;
		this.jwtService = jwtService;
	}

	@Operation(summary = "Get the list of referees", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the referees",
					content = { @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = RefereeDto.class))) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/referees",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<RefereeDto> getReferees(@RequestHeader("Authorization") String authorization) {
		var adminEmail = jwtService.getEmail(authorization);
		try {
			return refereeService.getReferees(adminEmail);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Get a referee by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the referee",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = RefereeDto.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Referee not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/referees/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public RefereeDto getReferee(@RequestHeader("Authorization") String authorization,
								 @PathVariable Integer id) {
		var adminEmail = jwtService.getEmail(authorization);
		try {
			return refereeService.getReferee(id, adminEmail);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (RefereeNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get referee reports by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the reports",
					content = { @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = ShortReportDto.class))) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Referee users can perform this operation", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Referee not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/referees/{id}/reports",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ShortReportDto> getRefereeReports(@RequestHeader("Authorization") String authorization,
													  @PathVariable Integer id) {
		var refereeEmail = jwtService.getEmail(authorization);
		try {
			return refereeService.getReportsOfReferee(id, refereeEmail);
		}
		catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (RefereeNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Add a new referee", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Referee added"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "409", description = "Email has already been used") })
	@RequestMapping(value = "/referees",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void addReferee(@RequestHeader("Authorization") String authorization,
						   @RequestBody(required = true) RefereeDto referee) {
		var adminEmail = jwtService.getEmail(authorization);
		try {
			refereeService.createReferee(adminEmail, referee);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

	@Operation(summary = "Update an existing referee by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Referee updated"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "404", description = "Referee not found"),
			@ApiResponse(responseCode = "409", description = "Referee update failed. Email has already been used") })
	@RequestMapping(value = "/referees/{id}",
			method = RequestMethod.PATCH,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateReferee(@RequestHeader("Authorization") String authorization,
						   @PathVariable Integer id,
						   @RequestBody RefereeDto referee) {
		var adminEmail = jwtService.getEmail(authorization);
		try {
			refereeService.updateReferee(id, referee, adminEmail);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (RefereeNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Referee not found", ex);
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

	@Operation(summary = "Disable a referee by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Referee disabled"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "404", description = "Referee not found") })
	@RequestMapping(value = "/referees/{id}",
			method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteReferee(@RequestHeader("Authorization") String authorization,
						   @PathVariable Integer id) {
		var adminEmail = jwtService.getEmail(authorization);
		try {
			refereeService.deleteReferee(id, adminEmail);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (RefereeNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Referee not found", ex);
		}
		catch (RegisteredUserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registered user not found", ex);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
		}
	}
}
