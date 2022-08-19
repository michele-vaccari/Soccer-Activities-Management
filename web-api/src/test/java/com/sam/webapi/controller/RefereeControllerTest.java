package com.sam.webapi.controller;

import com.sam.webapi.dto.RefereeDto;
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
class RefereeControllerTest {

	@Test
	@DisplayName("When get referees, then operation is successful")
	void whenGetReferees_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		List<RefereeDto> referees = new ArrayList<>(
				Arrays.asList(
						new RefereeDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume"),
						new RefereeDto(3, "Jane", "Doe", "jane.doe@sam.com", null, "N", "987654321","Street", "31-12-1970", "English", "Resume")
				));
		Mockito.when(refereeService.getReferees("john.doe@sam.com")).thenReturn(referees);
		var refereeController = new RefereeController(refereeService, jwtService);

		var result = refereeController.getReferees(bearerToken);

		Mockito.verify(refereeService, times(1)).getReferees("john.doe@sam.com");
		Assertions.assertEquals(referees, result);
	}

	@Test
	@DisplayName("When get referees, then throw ResponseStatusException: Unauthorized")
	void whenGetReferees_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(UnauthorizedException.class).when(refereeService).getReferees("john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.getReferees(bearerToken));
	}

	@Test
	@DisplayName("When get referee, then operation is successful")
	void whenGetReferee_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		Mockito.when(refereeService.getReferee(2, "john.doe@sam.com")).thenReturn(referee);
		var refereeController = new RefereeController(refereeService, jwtService);

		var result = refereeController.getReferee(bearerToken, 2);

		Mockito.verify(refereeService, times(1)).getReferee(2, "john.doe@sam.com");
		Assertions.assertEquals(referee, result);
	}

	@Test
	@DisplayName("When get referee, then throw ResponseStatusException: Unauthorized")
	void whenGetReferee_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.when(refereeService.getReferee(2, "john.doe@sam.com")).thenThrow(UnauthorizedException.class);
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.getReferee(bearerToken, 2));
	}

	@Test
	@DisplayName("When get referee, then throw ResponseStatusException: Referee not found")
	void whenGetReferee_ThenThrowResponseStatusException_RefereeNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.when(refereeService.getReferee(2, "john.doe@sam.com")).thenThrow(RefereeNotFoundException.class);
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.getReferee(bearerToken, 2));
		Mockito.verify(refereeService, times(1)).getReferee(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When add referee, then operation is successful")
	void whenAddReferee_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		var refereeController = new RefereeController(refereeService, jwtService);

		refereeController.addReferee(bearerToken, referee);

		Mockito.verify(refereeService, times(1)).createReferee("john.doe@sam.com", referee);
	}

	@Test
	@DisplayName("When add referee, then throw ResponseStatusException: Unauthorized")
	void whenAddReferee_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		Mockito.doThrow(UnauthorizedException.class).when(refereeService).createReferee( "john.doe@sam.com", referee);
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.addReferee(bearerToken, referee));
	}

	@Test
	@DisplayName("When add referee, then throw ResponseStatusException: Email has already been used")
	void whenAddReferee_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		Mockito.doThrow(SingleEmailConstraintException.class).when(refereeService).createReferee("john.doe@sam.com", referee);
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.addReferee(bearerToken, referee));
	}

	@Test
	@DisplayName("When update referee, then operation is successful")
	void whenUpdateReferee_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		var refereeController = new RefereeController(refereeService, jwtService);

		refereeController.updateReferee(bearerToken, 2, referee);

		Mockito.verify(refereeService, times(1)).updateReferee(2, referee, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: Unauthorized")
	void whenUpdateReferee_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(UnauthorizedException.class).when(refereeService).updateReferee(2, referee,"john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: Referee not found")
	void whenUpdateReferee_ThenThrowResponseStatusException_RefereeNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(RefereeNotFoundException.class).when(refereeService).updateReferee(2, referee, "john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: Registered user not found")
	void whenUpdateReferee_ThenThrowResponseStatusException_RegisteredUserNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(RegisteredUserNotFoundException.class).when(refereeService).updateReferee(2, referee, "john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: User not found")
	void whenUpdateReferee_ThenThrowResponseStatusException_UserNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(UserNotFoundException.class).when(refereeService).updateReferee(2, referee, "john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: Email has already been used")
	void whenUpdateReferee_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(SingleEmailConstraintException.class).when(refereeService).updateReferee(2, referee, "john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When delete referee, then operation is successful")
	void whenDeleteReferee_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		refereeController.deleteReferee(bearerToken, 2);

		Mockito.verify(refereeService, times(1)).deleteReferee(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When delete referee, then throw ResponseStatusException: Unauthorized")
	void whenDeleteReferee_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(UnauthorizedException.class).when(refereeService).deleteReferee(2, "john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.deleteReferee(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete referee, then throw ResponseStatusException: Referee not found")
	void whenDeleteReferee_ThenThrowResponseStatusException_RefereeNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(RefereeNotFoundException.class).when(refereeService).deleteReferee(2, "john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.deleteReferee(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete referee, then throw ResponseStatusException: RegisteredUser not found")
	void whenDeleteReferee_ThenThrowResponseStatusException_RegisteredUserNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(RegisteredUserNotFoundException.class).when(refereeService).deleteReferee(2, "john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.deleteReferee(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete referee, then throw ResponseStatusException: User not found")
	void whenDeleteReferee_ThenThrowResponseStatusException_UserNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.doThrow(UserNotFoundException.class).when(refereeService).deleteReferee(2, "john.doe@sam.com");
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.deleteReferee(bearerToken, 2));
	}
}