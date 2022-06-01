package com.sam.webapi.controller;

import com.sam.webapi.dto.LineupDto;
import com.sam.webapi.dto.ReportDto;
import com.sam.webapi.dto.ShortReportDto;
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

	@Operation(summary = "Get reports", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Reports found",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = ShortReportDto.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Admin users can perform this operation", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/reports",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ShortReportDto> getReports(@RequestHeader("Authorization") String authorization) {
		var adminEmail = jwtService.getEmail(authorization);

		try {
			return reportService.getReports(adminEmail);
		} catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Get report by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Report found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReportDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Report not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/reports/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public ReportDto getReport(@PathVariable Integer id) {
		try {
			return reportService.getReport(id);
		} catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} catch (ReportNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get lineups of report by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Report found",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = ReportDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Referee can perform this operation", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Report not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/reports/{id}/lineups",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseStatus(HttpStatus.OK)
	public ReportDto getLineups(@RequestHeader("Authorization") String authorization,
						  		@PathVariable Integer id) {
		var refereeEmail = jwtService.getEmail(authorization);

		try {
			return reportService.getLineups(id, refereeEmail);
		} catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} catch (ReportNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Compile report by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Report updated",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReportDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only Referee can perform this operation", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Report not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/reports/{id}",
			method = RequestMethod.PATCH)
	@ResponseStatus(HttpStatus.OK)
	public void compileReport(@RequestHeader("Authorization") String authorization,
							  @PathVariable Integer id,
							  @RequestBody ReportDto report) {
		var refereeEmail = jwtService.getEmail(authorization);

		try {
			reportService.compileReport(id, refereeEmail, report);
		} catch (BadRequestException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} catch (UnauthorizedException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} catch (ReportNotFoundException ex) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	}

	@Operation(summary = "Add a lineup to report by its id", security = { @SecurityRequirement(name = "Bearer") })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lineup added",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = LineupDto.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, only team manager can perform this operation", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Tournament not found", content = @Content(schema = @Schema(hidden = true)))
	})
	@RequestMapping(value = "/reports/{id}/lineups",
			method = RequestMethod.POST)
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
