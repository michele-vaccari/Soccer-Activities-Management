package com.sam.webapi.controller;

import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.security.service.JwtService;
import com.sam.webapi.service.*;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Players", description = "Players information retrieval, modification, deactivation")
public class PlayerController {

	private PlayerService playerService;
	private JwtService jwtService;

	@Autowired
	public PlayerController(PlayerService playerService, JwtService jwtService) {
		this.playerService = playerService;
		this.jwtService = jwtService;
	}

	@Operation(summary = "Get a player by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Player found",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = PlayerDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Player not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/players/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public PlayerDto getPlayer(@PathVariable Integer id) {

		try {
			return playerService.getPlayer(id);
		}
		catch (PlayerNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Update an existing player by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Player updated"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only the team manager can perform this operation"),
			@ApiResponse(responseCode = "404", description = "Player not found") })
	@RequestMapping(value = "/players/{id}",
			method = RequestMethod.PATCH,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updatePlayer(@RequestHeader("Authorization") String authorization,
						   	 @PathVariable Integer id,
						   	 @RequestBody PlayerDto teamDto) {
		var userEmail = jwtService.getEmail(authorization);

		try {
			playerService.updatePlayer(id, userEmail, teamDto);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		catch (PlayerNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found", ex);
		}
	}

	@Operation(summary = "Disable a player by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Player disabled"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation"),
			@ApiResponse(responseCode = "404", description = "Player not found") })
	@RequestMapping(value = "/players/{id}",
			method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deletePlayer(@RequestHeader("Authorization") String authorization,
							 @PathVariable Integer id) {
		var userEmail = jwtService.getEmail(authorization);
		try {
			playerService.deletePlayer(id, userEmail);
		}
		catch (PlayerNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found", ex);
		}
		catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

}
