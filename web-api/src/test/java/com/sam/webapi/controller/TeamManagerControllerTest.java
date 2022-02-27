package com.sam.webapi.controller;

import com.sam.webapi.dto.TeamManagerDto;
import com.sam.webapi.security.model.JwtTokenData;
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
class TeamManagerControllerTest {

	@Test
	@DisplayName("When get team managers, then operation is successful")
	void whenGetTeamManagesr_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		List<TeamManagerDto> teamManagers = new ArrayList<>(
				Arrays.asList(
						new TeamManagerDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street"),
						new TeamManagerDto(3, "Jane", "Doe", "jane.doe@sam.com", null, "N", "987654321","Street")
				));
		Mockito.when(teamManagerService.getTeamManagers()).thenReturn(teamManagers);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		var result = teamManagerController.getTeamManagers(bearerToken);

		Mockito.verify(teamManagerService, times(1)).getTeamManagers();
		Assertions.assertEquals(teamManagers, result);
	}

	@Test
	@DisplayName("When get team managers, then throw ResponseStatusException: Unauthorized")
	void whenGetTeamManagers_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.getTeamManagers(bearerToken));
	}

	@Test
	@DisplayName("When get team manager, then operation is successful")
	void whenGetTeamManager_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		var teamManager = Optional.of(new TeamManagerDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street"));
		Mockito.when(teamManagerService.getTeamManager(2)).thenReturn(teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		var result = teamManagerController.getTeamManager(bearerToken, 2);

		Mockito.verify(teamManagerService, times(1)).getTeamManager(2);
		Assertions.assertEquals(teamManager, result);
	}

	@Test
	@DisplayName("When get team manager, then throw ResponseStatusException: Unauthorized")
	void whenGetTeamManager_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.getTeamManager(bearerToken, 2));
	}

	@Test
	@DisplayName("When get team manager, then throw ResponseStatusException: User not found")
	void whenGetTeamManager_ThenThrowResponseStatusException_UserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		Mockito.when(teamManagerService.getTeamManager(2)).thenReturn(Optional.empty());
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.getTeamManager(bearerToken, 2));
		Mockito.verify(teamManagerService, times(1)).getTeamManager(2);
	}

	@Test
	@DisplayName("When add team manager, then operation is successful")
	void whenAddTeamManager_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		var teamManager = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street");
		var refereeController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		refereeController.addTeamManager(bearerToken, teamManager);

		Mockito.verify(teamManagerService, times(1)).createTeamManager(jwtTokenData.getEmail(), teamManager);
	}

	@Test
	@DisplayName("When add team manager, then throw ResponseStatusException: Unauthorized")
	void whenAddTeamManager_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManager = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street");
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.addTeamManager(bearerToken, teamManager));
	}

	@Test
	@DisplayName("When add team manager, then throw ResponseStatusException: Email has already been used")
	void whenAddTeamManager_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		var teamManager = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street");
		Mockito.doThrow(SingleEmailConstraintException.class).when(teamManagerService).createTeamManager(jwtTokenData.getEmail(), teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.addTeamManager(bearerToken, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then operation is successful")
	void whenUpdateTeamManager_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		var referee = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		teamManagerController.updateTeamManager(bearerToken, 2, referee);

		Mockito.verify(teamManagerService, times(1)).updateTeamManager(2, referee);
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: Unauthorized")
	void whenUpdateTeamManager_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: Referee not found")
	void whenUpdateReferee_ThenThrowResponseStatusException_RefereeNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null);
		Mockito.doThrow(RefereeNotFoundException.class).when(teamManagerService).updateTeamManager(2, teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: Registered user not found")
	void whenUpdateTeamManager_ThenThrowResponseStatusException_RegisteredUserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null);
		Mockito.doThrow(RegisteredUserNotFoundException.class).when(teamManagerService).updateTeamManager(2, teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: User not found")
	void whenUpdateTeamManager_ThenThrowResponseStatusException_UserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null);
		Mockito.doThrow(UserNotFoundException.class).when(teamManagerService).updateTeamManager(2, teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When update team manager, then throw ResponseStatusException: Email has already been used")
	void whenUpdateReferee_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManager = new TeamManagerDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null);
		Mockito.doThrow(SingleEmailConstraintException.class).when(teamManagerService).updateTeamManager(2, teamManager);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.updateTeamManager(bearerToken, 2, teamManager));
	}

	@Test
	@DisplayName("When delete team manager, then operation is successful")
	void whenDeleteTeamManager_ThenOperationIsSuccessful() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		teamManagerController.deleteTeamManager(bearerToken, 2);

		Mockito.verify(teamManagerService, times(1)).deleteTeamManager(2);
	}

	@Test
	@DisplayName("When delete team manager, then throw ResponseStatusException: Unauthorized")
	void whenDeleteTeamManager_ThenThrowResponseStatusException_Unauthorized() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.deleteTeamManager(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete team manager, then throw ResponseStatusException: Referee not found")
	void whenDeleteTeamManager_ThenThrowResponseStatusException_RefereeNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		Mockito.doThrow(UserNotFoundException.class).when(teamManagerService).deleteTeamManager(2);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.deleteTeamManager(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete team manager, then throw ResponseStatusException: RegisteredUser not found")
	void whenDeleteTeamManager_ThenThrowResponseStatusException_RegisteredUserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		Mockito.doThrow(RegisteredUserNotFoundException.class).when(teamManagerService).deleteTeamManager(2);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.deleteTeamManager(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete team manager, then throw ResponseStatusException: User not found")
	void whenDeleteTeamManager_ThenThrowResponseStatusException_UserNotFound() {
		var teamManagerService = Mockito.mock(TeamManagerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		Mockito.doThrow(UserNotFoundException.class).when(teamManagerService).deleteTeamManager(2);
		var teamManagerController = new TeamManagerController(teamManagerService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> teamManagerController.deleteTeamManager(bearerToken, 2));
	}
}