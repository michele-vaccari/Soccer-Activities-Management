package com.sam.webapi.controller;

import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.dto.TeamDto;
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

import java.util.Optional;

@RestController
@Tag(name = "Teams", description = "Teams information retrieval, modification")
public class TeamController {

	private TeamService teamService;
	private JwtService jwtService;

	@Autowired
	public TeamController(TeamService teamService, JwtService jwtService) {
		this.teamService = teamService;
		this.jwtService = jwtService;
	}

	@Operation(summary = "Get the list of teams")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the teams",
					content = { @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = TeamDto.class))) }),
	})
	@RequestMapping(value = "/teams",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<TeamDto> getTeams() {
		return teamService.getTeams();
	}

	@Operation(summary = "Get a team by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the team",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = TeamDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Team not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/teams/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public Optional<TeamDto> getTeam(@PathVariable Integer id) {

		var team = teamService.getTeam(id);

		if (!team.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return team;
	}

	@Operation(summary = "Update an existing team by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Team updated"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only the team manager can perform this operation"),
			@ApiResponse(responseCode = "404", description = "Team not found") })
	@RequestMapping(value = "/teams/{id}",
			method = RequestMethod.PATCH,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateTeam(@RequestHeader("Authorization") String authorization,
						   @PathVariable Integer id,
						   @RequestBody TeamDto teamDto) {
		var email = jwtService.getEmail(authorization);

		try {
			teamService.updateTeam(id, email, teamDto);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (TeamNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found", ex);
		}
	}

	@Operation(summary = "Get the list of team players")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the players",
					content = { @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PlayerDto.class))) }),
	})
	@RequestMapping(value = "/teams/{id}/players",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<PlayerDto> getPlayers(@PathVariable Integer id) {

		try {
			return teamService.getPlayersOfTeam(id);
		}
		catch (TeamNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found", ex);
		}

	}

	@Operation(summary = "Add a team player", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Player added"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only the team manager can perform this operation"),
			@ApiResponse(responseCode = "406", description = "Not acceptable, you have reached the maximum limit of active players per team") })
	@RequestMapping(value = "/teams/{id}/players",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void addTeamPlayer(@RequestHeader("Authorization") String authorization,
							  @PathVariable Integer id,
							  @RequestBody PlayerDto playerDto) {
		var userEmail = jwtService.getEmail(authorization);

		try {
			teamService.createPlayerOfTeam(id, userEmail, playerDto);
		}
		catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request", ex);
		}
		catch (TeamNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found", ex);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized", ex);
		}
		catch (MaxPlayersInTeamException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not acceptable", ex);
		}
	}

}
