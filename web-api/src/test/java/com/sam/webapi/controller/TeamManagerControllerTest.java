package com.sam.webapi.controller;

import com.sam.webapi.dto.TeamManagerDto;
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

import static org.mockito.Mockito.times;

@Tag("UnitTest")
class TeamManagerControllerTest {

	@Test
	@DisplayName("When get team managers, then operation is successful")
	void whenGetTeamManagers_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		List<TeamManagerDto> teamManagers = new ArrayList<>(
				Arrays.asList(
						new TeamManagerDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "Team1"),
						new TeamManagerDto(3, "Jane", "Doe", "jane.doe@sam.com", null, "N", "987654321","Street", "Team2")
				));
		Mockito.when(teamManagerService.getTeamManagers("john.doe@sam.com")).thenReturn(teamManagers);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		var result = teamManagerController.getTeamManagers(bearerToken);

		Mockito.verify(teamManagerService, times(1)).getTeamManagers("john.doe@sam.com");
		Assertions.assertEquals(teamManagers, result);
	}

	@Test
	@DisplayName("When get team managers, then throw ResponseStatusException: Unauthorized")
	void whenGetTeamManagers_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(UnauthorizedException.class).when(teamManagerService).getTeamManagers("john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.getTeamManagers(bearerToken));
	}

	@Test
	@DisplayName("When get team manager, then operation is successful")
	void whenGetTeamManager_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "Team1");
		Mockito.when(teamManagerService.getTeamManager(2, "john.doe@sam.com")).thenReturn(teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		var result = teamManagerController.getTeamManager(bearerToken, 2);

		Mockito.verify(teamManagerService, times(1)).getTeamManager(2, "john.doe@sam.com");
		Assertions.assertEquals(teamManager, result);
	}

	@Test
	@DisplayName("When get team manager, then throw ResponseStatusException: Unauthorized")
	void whenGetTeamManager_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(UnauthorizedException.class).when(teamManagerService).getTeamManager(2, "john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.getTeamManager(bearerToken, 2));
	}

	@Test
	@DisplayName("When get team manager, then throw ResponseStatusException: team manager not found")
	void whenGetTeamManager_ThenThrowResponseStatusException_TeamManagerNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.when(teamManagerService.getTeamManager(2, "john.doe@sam.com")).thenThrow(TeamManagerNotFoundException.class);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.getTeamManager(bearerToken, 2));
		Mockito.verify(teamManagerService, times(1)).getTeamManager(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When add team manager, then operation is successful")
	void whenAddTeamManager_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "Team1");
		var refereeController = new TeamManagerController(teamManagerService, jwtService);

		refereeController.addTeamManager(bearerToken, teamManager);

		Mockito.verify(teamManagerService, times(1)).createTeamManager("john.doe@sam.com", teamManager);
	}

	@Test
	@DisplayName("When add team manager, then throw ResponseStatusException: Unauthorized")
	void whenAddTeamManager_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "Team1");
		Mockito.doThrow(UnauthorizedException.class).when(teamManagerService).createTeamManager("john.doe@sam.com", teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.addTeamManager(bearerToken, teamManager));
	}

	@Test
	@DisplayName("When add team manager, then throw ResponseStatusException: Email has already been used")
	void whenAddTeamManager_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "Team1");
		Mockito.doThrow(SingleEmailConstraintException.class).when(teamManagerService).createTeamManager("john.doe@sam.com", teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.addTeamManager(bearerToken, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then operation is successful")
	void whenUpdateTeamManager_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		teamManagerController.updateTeamManager(bearerToken, 2, referee);

		Mockito.verify(teamManagerService, times(1)).updateTeamManager(2, referee, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: Unauthorized")
	void whenUpdateTeamManager_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null);
		Mockito.doThrow(UnauthorizedException.class).when(teamManagerService).updateTeamManager(2, teamManager,"john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: Team manager not found")
	void whenUpdateTeamManager_ThenThrowResponseStatusException_TeamManagerNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null);
		Mockito.doThrow(TeamManagerNotFoundException.class).when(teamManagerService).updateTeamManager(2, teamManager,"john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: Registered user not found")
	void whenUpdateTeamManager_ThenThrowResponseStatusException_RegisteredUserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null);
		Mockito.doThrow(RegisteredUserNotFoundException.class).when(teamManagerService).updateTeamManager(2, teamManager, "john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: User not found")
	void whenUpdateTeamManager_ThenThrowResponseStatusException_UserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null);
		Mockito.doThrow(UserNotFoundException.class).when(teamManagerService).updateTeamManager(2, teamManager, "john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: Email has already been used")
	void whenUpdateReferee_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null);
		Mockito.doThrow(SingleEmailConstraintException.class).when(teamManagerService).updateTeamManager(2, teamManager, "john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When delete team manager, then operation is successful")
	void whenDeleteTeamManager_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.when(jwtService.hasAnAdminUser(bearerToken)).thenReturn(true);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		teamManagerController.deleteTeamManager(bearerToken, 2);

		Mockito.verify(teamManagerService, times(1)).deleteTeamManager(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When delete team manager, then throw ResponseStatusException: Unauthorized")
	void whenDeleteTeamManager_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(UnauthorizedException.class).when(teamManagerService).deleteTeamManager(2, "john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.deleteTeamManager(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete team manager, then throw ResponseStatusException: Referee not found")
	void whenDeleteTeamManager_ThenThrowResponseStatusException_RefereeNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(UserNotFoundException.class).when(teamManagerService).deleteTeamManager(2, "john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.deleteTeamManager(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete team manager, then throw ResponseStatusException: RegisteredUser not found")
	void whenDeleteTeamManager_ThenThrowResponseStatusException_RegisteredUserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(RegisteredUserNotFoundException.class).when(teamManagerService).deleteTeamManager(2, "john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.deleteTeamManager(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete team manager, then throw ResponseStatusException: User not found")
	void whenDeleteTeamManager_ThenThrowResponseStatusException_UserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(UserNotFoundException.class).when(teamManagerService).deleteTeamManager(2, "john.doe@sam.com");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.deleteTeamManager(bearerToken, 2));
	}
}