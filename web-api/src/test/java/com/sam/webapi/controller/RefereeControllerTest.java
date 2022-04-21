package com.sam.webapi.controller;

import com.sam.webapi.dto.RefereeDto;
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
class RefereeControllerTest {

	@Test
	@DisplayName("When get referees, then operation is successful")
	void whenGetReferees_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.hasAnAdminUser(bearerToken)).thenReturn(true);
		List<RefereeDto> referees = new ArrayList<>(
				Arrays.asList(
						new RefereeDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume"),
						new RefereeDto(3, "Jane", "Doe", "jane.doe@sam.com", null, "N", "987654321","Street", "31-12-1970", "English", "Resume")
				));
		Mockito.when(refereeService.getReferees()).thenReturn(referees);
		var refereeController = new RefereeController(refereeService, jwtService);

		var result = refereeController.getReferees(bearerToken);

		Mockito.verify(refereeService, times(1)).getReferees();
		Assertions.assertEquals(referees, result);
	}

	@Test
	@DisplayName("When get referees, then throw ResponseStatusException: Unauthorized")
	void whenGetReferees_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.getReferees(bearerToken));
	}

	@Test
	@DisplayName("When get referee, then operation is successful")
	void whenGetReferee_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.hasAnAdminUser(bearerToken)).thenReturn(true);
		var referee = new RefereeDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		Mockito.when(refereeService.getReferee(2)).thenReturn(referee);
		var refereeController = new RefereeController(refereeService, jwtService);

		var result = refereeController.getReferee(bearerToken, 2);

		Mockito.verify(refereeService, times(1)).getReferee(2);
		Assertions.assertEquals(referee, result);
	}

	@Test
	@DisplayName("When get referee, then throw ResponseStatusException: Unauthorized")
	void whenGetReferee_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.getReferee(bearerToken, 2));
	}

	@Test
	@DisplayName("When get referee, then throw ResponseStatusException: Referee not found")
	void whenGetReferee_ThenThrowResponseStatusException_RefereeNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.hasAnAdminUser(bearerToken)).thenReturn(true);
		Mockito.when(refereeService.getReferee(2)).thenThrow(RefereeNotFoundException.class);
		var refereeController = new RefereeController(refereeService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.getReferee(bearerToken, 2));
		Mockito.verify(refereeService, times(1)).getReferee(2);
	}

	@Test
	@DisplayName("When add referee, then operation is successful")
	void whenAddReferee_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.hasAnAdminUser(bearerToken)).thenReturn(true);
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var referee = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		var refereeController = new RefereeController(refereeService, jwtService);

		refereeController.addReferee(bearerToken, referee);

		Mockito.verify(refereeService, times(1)).createReferee(jwtTokenData.getEmail(), referee);
	}

	@Test
	@DisplayName("When add referee, then throw ResponseStatusException: Unauthorized")
	void whenAddReferee_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var referee = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.addReferee(bearerToken, referee));
	}

	@Test
	@DisplayName("When add referee, then throw ResponseStatusException: Email has already been used")
	void whenAddReferee_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		var referee = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		Mockito.doThrow(SingleEmailConstraintException.class).when(refereeService).createReferee(jwtTokenData.getEmail(), referee);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.addReferee(bearerToken, referee));
	}

	@Test
	@DisplayName("When update referee, then operation is successful")
	void whenUpdateReferee_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.hasAnAdminUser(bearerToken)).thenReturn(true);
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		var refereeController = new RefereeController(refereeService, jwtService);

		refereeController.updateReferee(bearerToken, 2, referee);

		Mockito.verify(refereeService, times(1)).updateReferee(2, referee);
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: Unauthorized")
	void whenUpdateReferee_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: Referee not found")
	void whenUpdateReferee_ThenThrowResponseStatusException_RefereeNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(RefereeNotFoundException.class).when(refereeService).updateReferee(2, referee);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: Registered user not found")
	void whenUpdateReferee_ThenThrowResponseStatusException_RegisteredUserNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(RegisteredUserNotFoundException.class).when(refereeService).updateReferee(2, referee);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: User not found")
	void whenUpdateReferee_ThenThrowResponseStatusException_UserNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(UserNotFoundException.class).when(refereeService).updateReferee(2, referee);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When update referee, then throw ResponseStatusException: Email has already been used")
	void whenUpdateReferee_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var referee = new RefereeDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null, null, null, null, null, null);
		Mockito.doThrow(SingleEmailConstraintException.class).when(refereeService).updateReferee(2, referee);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.updateReferee(bearerToken, 2, referee));
	}

	@Test
	@DisplayName("When delete referee, then operation is successful")
	void whenDeleteReferee_ThenOperationIsSuccessful() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.hasAnAdminUser(bearerToken)).thenReturn(true);
		var refereeController = new RefereeController(refereeService, jwtService);

		refereeController.deleteReferee(bearerToken, 2);

		Mockito.verify(refereeService, times(1)).deleteReferee(2);
	}

	@Test
	@DisplayName("When delete referee, then throw ResponseStatusException: Unauthorized")
	void whenDeleteReferee_ThenThrowResponseStatusException_Unauthorized() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.empty());
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.deleteReferee(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete referee, then throw ResponseStatusException: Referee not found")
	void whenDeleteReferee_ThenThrowResponseStatusException_RefereeNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		Mockito.doThrow(RefereeNotFoundException.class).when(refereeService).deleteReferee(2);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.deleteReferee(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete referee, then throw ResponseStatusException: RegisteredUser not found")
	void whenDeleteReferee_ThenThrowResponseStatusException_RegisteredUserNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		Mockito.doThrow(RegisteredUserNotFoundException.class).when(refereeService).deleteReferee(2);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.deleteReferee(bearerToken, 2));
	}

	@Test
	@DisplayName("When delete referee, then throw ResponseStatusException: User not found")
	void whenDeleteReferee_ThenThrowResponseStatusException_UserNotFound() {
		var refereeService = Mockito.mock(RefereeService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var jwtTokenData = new JwtTokenData();
		jwtTokenData.setRole("Admin");
		jwtTokenData.setEmail("john.doe@sam.com");
		Mockito.when(jwtService.validateJwt("token")).thenReturn(Optional.of(jwtTokenData));
		Mockito.doThrow(UserNotFoundException.class).when(refereeService).deleteReferee(2);
		var refereeController = new RefereeController(refereeService, jwtService);
		var bearerToken = "Bearer token";

		Assertions.assertThrows(ResponseStatusException.class, ()-> refereeController.deleteReferee(bearerToken, 2));
	}
}