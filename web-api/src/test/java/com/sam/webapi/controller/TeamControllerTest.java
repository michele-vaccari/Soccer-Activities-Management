package com.sam.webapi.controller;

import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.dto.TeamDto;
import com.sam.webapi.security.service.JwtService;
import com.sam.webapi.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@Tag("UnitTest")
class TeamControllerTest {

	@Test
	@DisplayName("When get teams, then operation is successful")
	void whenGetTeams_ThenOperationIsSuccessful() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		List<TeamDto> teams = new ArrayList<>(
				Arrays.asList(
						new TeamDto(1, "Hellas Verona", "Description", "Verona", "AGSM"),
						new TeamDto(2, "SPAL", "Description", "Ferrara", "Inter Spar")
				));
		Mockito.when(teamService.getTeams()).thenReturn(teams);
		var teamController = new TeamController(teamService, jwtService);

		var result = teamController.getTeams();

		Mockito.verify(teamService, times(1)).getTeams();
		Assertions.assertEquals(teams, result);
	}

	@Test
	@DisplayName("When get team, then operation is successful")
	void whenGetTeam_ThenOperationIsSuccessful() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var team = new TeamDto(1, "Hellas Verona", "Description", "Verona", "AGSM");
		Mockito.when(teamService.getTeam(1)).thenReturn(team);
		var teamController = new TeamController(teamService, jwtService);

		var result = teamController.getTeam(1);

		Mockito.verify(teamService, times(1)).getTeam(1);
		Assertions.assertEquals(team, result);
	}

	@Test
	@DisplayName("When get team, then throw ResponseStatusException: Team not found")
	void whenGetTeam_ThenThrowResponseStatusException_TeamNotFound() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(teamService.getTeam(1)).thenThrow(TeamNotFoundException.class);
		var teamController = new TeamController(teamService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamController.getTeam(1));
		Mockito.verify(teamService, times(1)).getTeam(1);
	}

	@Test
	@DisplayName("When update team, then operation is successful")
	void whenUpdateTeam_ThenOperationIsSuccessful() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamController = new TeamController(teamService, jwtService);
		var teamDto = new TeamDto(1, "Hellas", "Description", "Verona", "AGSM");
		teamController.updateTeam(bearerToken, 1, teamDto);

		Mockito.verify(teamService, times(1)).updateTeam(1, "john.doe@sam.com", teamDto);
	}

	@Test
	@DisplayName("When update team, then throw ResponseStatusException: Unauthorized")
	void whenUpdateTeam_ThenThrowResponseStatusException_Unauthorized() {
		var teamService = Mockito.mock(TeamService.class);
		var teamDto = new TeamDto(1, "Hellas", "Description", "Verona", "AGSM");
		Mockito.doThrow(UnauthorizedException.class).when(teamService).updateTeam(1,"john.doe@sam.com", teamDto);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamController = new TeamController(teamService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamController.updateTeam(bearerToken, 1, teamDto));
	}

	@Test
	@DisplayName("When update team, then throw ResponseStatusException: Team not found")
	void whenUpdateTeam_ThenThrowResponseStatusException_TeamNotFound() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamDto = new TeamDto(1, "Hellas", "Description", "Verona", "AGSM");
		Mockito.doThrow(TeamNotFoundException.class).when(teamService).updateTeam(1,"john.doe@sam.com", teamDto);
		var teamController = new TeamController(teamService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamController.updateTeam(bearerToken, 1, teamDto));
	}

	@Test
	@DisplayName("When get team players, then operation is successful")
	void whenGetTeamPlayers_ThenOperationIsSuccessful() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		List<PlayerDto> players = new ArrayList<>(
				Arrays.asList(
						new PlayerDto(1, 2, "Y", 10, "role", "John", "Doe", "01-01-1970", "Italian", "Description", 0, 0, 0),
						new PlayerDto(2, 2, "Y", 10, "role", "John", "Doe", "01-01-1970", "Italian", "Description", 0, 0, 0)
				));
		Mockito.when(teamService.getPlayersOfTeam(2)).thenReturn(players);
		var teamController = new TeamController(teamService, jwtService);

		var result = teamController.getPlayers(2);

		Mockito.verify(teamService, times(1)).getPlayersOfTeam(2);
		Assertions.assertEquals(players, result);
	}

	@Test
	@DisplayName("When get team players, then throw ResponseStatusException: Team not found")
	void whenGetTeamPlayers_ThenThrowResponseStatusException_TeamNotFound() {
		var teamService = Mockito.mock(TeamService.class);
		Mockito.when(teamService.getPlayersOfTeam(2)).thenThrow(TeamNotFoundException.class);
		var jwtService = Mockito.mock(JwtService.class);
		var teamController = new TeamController(teamService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamController.getPlayers(2));
		Mockito.verify(teamService, times(1)).getPlayersOfTeam(2);
	}

	@Test
	@DisplayName("When add team player, then operation is successful")
	void whenAddTeamPlayer_ThenOperationIsSuccessful() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamController = new TeamController(teamService, jwtService);
		var player = new PlayerDto(1, 2, "Y", 10, "role", "John", "Doe", "01-01-1970", "Italian", "Description", 0, 0, 0);
		teamController.addTeamPlayer(bearerToken, 2, player);

		Mockito.verify(teamService, times(1)).createPlayerOfTeam(2, "john.doe@sam.com", player);
	}

	@Test
	@DisplayName("When add team player, then throw ResponseStatusException: Bad request")
	void whenAddTeamPlayer_ThenThrowResponseStatusException_BadRequest() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var player = new PlayerDto(1, 2, "Y", -1, "role", "John", "Doe", "01-01-1970", "Italian", "Description", 0, 0, 0);
		Mockito.doThrow(BadRequestException.class).when(teamService).createPlayerOfTeam(2, "john.doe@sam.com", player);
		var teamController = new TeamController(teamService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamController.addTeamPlayer(bearerToken,2, player));
	}

	@Test
	@DisplayName("When add team player, then throw ResponseStatusException: Unauthorized")
	void whenAddTeamPlayer_ThenThrowResponseStatusException_Unauthorized() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var player = new PlayerDto(1, 2, "Y", 10, "role", "John", "Doe", "01-01-1970", "Italian", "Description", 0, 0, 0);
		Mockito.doThrow(UnauthorizedException.class).when(teamService).createPlayerOfTeam( 2, "john.doe@sam.com", player);
		var teamController = new TeamController(teamService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamController.addTeamPlayer(bearerToken,2, player));
	}

	@Test
	@DisplayName("When add team player, then throw ResponseStatusException: Not acceptable")
	void whenAddTeamPlayer_ThenThrowResponseStatusException_NotAcceptable() {
		var teamService = Mockito.mock(TeamService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var player = new PlayerDto(1, 2, "Y", 1, "role", "John", "Doe", "01-01-1970", "Italian", "Description", 0, 0, 0);
		Mockito.doThrow(MaxPlayersInTeamException.class).when(teamService).createPlayerOfTeam( 2, "john.doe@sam.com", player);
		var teamController = new TeamController(teamService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamController.addTeamPlayer(bearerToken,2, player));
	}
}