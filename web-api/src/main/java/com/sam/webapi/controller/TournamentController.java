package com.sam.webapi.controller;

import com.sam.webapi.dto.AdminDto;
import com.sam.webapi.dto.ShortTournamentDto;
import com.sam.webapi.dto.TournamentDto;
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
@Tag(name = "Tournaments", description = "Tournaments information retrieval, creation")
public class TournamentController {

	private TournamentService tournamentService;
	private JwtService jwtService;

	@Autowired
	public TournamentController(TournamentService tournamentService, JwtService jwtService) {
		this.tournamentService = tournamentService;
		this.jwtService = jwtService;
	}

	@Operation(summary = "Get the list of tournaments")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the tournaments",
						 content = { @Content(mediaType = "application/json",
						 array = @ArraySchema(schema = @Schema(implementation = ShortTournamentDto.class))) })
	})
	@RequestMapping(value = "/tournaments",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<ShortTournamentDto> getTournaments() {
		return tournamentService.getTournaments();
	}

	@Operation(summary = "Get the list of tournaments by team id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the tournaments",
					content = {@Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = ShortTournamentDto.class)))}),
			@ApiResponse(responseCode = "404", description = "Team not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/tournaments/teams/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ShortTournamentDto> getTournamentsByTeamId(@PathVariable Integer id) {

		try {
			return tournamentService.getTournaments(id);
		}
		catch (TeamNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get a tournament by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the tournament",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = AdminDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Tournament not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/tournaments/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public TournamentDto getTournament(@PathVariable Integer id) {

		try {
			return tournamentService.getTournament(id);
		}
		catch (TournamentNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Add a new tournament", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Tournament added"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "409", description = "Email has already been used") })
	@RequestMapping(value = "/tournaments",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void addTournament(@RequestHeader("Authorization") String authorization,
						 @RequestBody(required = true) TournamentDto tournament) {

		var adminEmail = jwtService.getEmail(authorization);

		try {
			tournamentService.createTournament(adminEmail, tournament);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (BadRequestException ex) {

		}
		catch (SingleEmailConstraintException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email has already been used", ex);
		}
	}

}
