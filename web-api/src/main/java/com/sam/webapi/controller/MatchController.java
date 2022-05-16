package com.sam.webapi.controller;

import com.sam.webapi.dto.MatchDto;
import com.sam.webapi.security.service.JwtService;
import com.sam.webapi.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

@RestController
@Tag(name = "Match", description = "Match information modification")
public class MatchController {

	private MatchService matchService;
	private JwtService jwtService;

	@Autowired
	public MatchController(MatchService matchService, JwtService jwtService) {
		this.matchService = matchService;
		this.jwtService = jwtService;
	}

	@Operation(summary = "Update an existing match by its id. Only one update can be made")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Update match",
					content = {@Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = MatchDto.class)))}),
			@ApiResponse(responseCode = "404", description = "Match not found", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "409", description = "Match update failed. A request for an update has already been made")
	})
	@RequestMapping(value = "/matches/{id}",
			method = RequestMethod.PATCH,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public void updateMatchById(@RequestHeader("Authorization") String authorization,
								@PathVariable Integer id,
								@RequestBody MatchDto matchDto) {
		var adminEmail = jwtService.getEmail(authorization);

		try {
			matchService.updateMatch(id, matchDto, adminEmail);
		}
		catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		catch (MatchNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		catch (MatchUpdateException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}

}
