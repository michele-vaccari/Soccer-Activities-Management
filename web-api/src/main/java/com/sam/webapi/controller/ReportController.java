package com.sam.webapi.controller;

import com.sam.webapi.dto.LineupDto;
import com.sam.webapi.dto.TournamentDto;
import com.sam.webapi.security.service.JwtService;
import com.sam.webapi.service.*;
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

@RestController
@Tag(name = "Reports", description = "Reports information retrieval, creation, modification")
public class ReportController {

	private ReportService reportService;
	private JwtService jwtService;

	@Autowired
	public ReportController(ReportService reportService, JwtService jwtService) {
		this.reportService = reportService;
		this.jwtService = jwtService;
	}

	@Operation(summary = "Add a lineup to report by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lineup added",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = LineupDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Tournament not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/reports/{id}/lineups",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public void addLineup(@RequestHeader("Authorization") String authorization,
								   @PathVariable Integer id,
								   @RequestBody LineupDto lineup) {
		var teamManagerEmail = jwtService.getEmail(authorization);

		try {
			reportService.addLineupToReport(id, teamManagerEmail, lineup);
		} catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
