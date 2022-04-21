package com.sam.webapi.controller;

import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.security.service.JwtService;
import com.sam.webapi.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.times;

@Tag("UnitTest")
class PlayerControllerTest {

	@Test
	@DisplayName("When get player, then operation is successful")
	void whenGetPlayer_ThenOperationIsSuccessful() {
		var playerService = Mockito.mock(PlayerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var player = Optional.of(new PlayerDto(1, 2, "Y", 10, "Role", "John", "Doe", "01-01-1970", "Italian", "description", 0, 0, 0));
		Mockito.when(playerService.getPlayer(1)).thenReturn(player);
		var playerController = new PlayerController(playerService, jwtService);

		var result = playerController.getPlayer(1);

		Mockito.verify(playerService, times(1)).getPlayer(1);
		Assertions.assertEquals(player, result);
	}

	@Test
	@DisplayName("When get player, then throw ResponseStatusException: Player not found")
	void whenGetPlayer_ThenThrowResponseStatusException_PlayerNotFound() {
		var playerService = Mockito.mock(PlayerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(playerService.getPlayer(1)).thenReturn(Optional.empty());
		var playerController = new PlayerController(playerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> playerController.getPlayer(1));
		Mockito.verify(playerService, times(1)).getPlayer(1);
	}

	@Test
	@DisplayName("When update player, then operation is successful")
	void whenUpdatePlayer_ThenOperationIsSuccessful() {
		var playerService = Mockito.mock(PlayerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var playerController = new PlayerController(playerService, jwtService);
		var playerDto = new PlayerDto(1, 2, "Y", 10, "Role", "John", "Doe", "01-01-1970", "Italian", "description", 0, 0, 0);
		playerController.updatePlayer(bearerToken, 1, playerDto);

		Mockito.verify(playerService, times(1)).updatePlayer(1, "john.doe@sam.com", playerDto);
	}

	@Test
	@DisplayName("When update player, then throw ResponseStatusException: Unauthorized")
	void whenUpdatePlayer_ThenThrowResponseStatusException_Unauthorized() {
		var playerService = Mockito.mock(PlayerService.class);
		var playerDto = new PlayerDto(1, 2, "Y", 10, "Role", "John", "Doe", "01-01-1970", "Italian", "description", 0, 0, 0);
		Mockito.doThrow(UnauthorizedException.class).when(playerService).updatePlayer(1,"john.doe@sam.com", playerDto);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var playerController = new PlayerController(playerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> playerController.updatePlayer(bearerToken, 1, playerDto));
		Mockito.verify(playerService, times(1)).updatePlayer(1, "john.doe@sam.com", playerDto);
	}

	@Test
	@DisplayName("When update player, then throw ResponseStatusException: Player not found")
	void whenUpdatePlayer_ThenThrowResponseStatusException_PlayerNotFound() {
		var playerService = Mockito.mock(PlayerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var playerDto = new PlayerDto(1, 2, "Y", 10, "Role", "John", "Doe", "01-01-1970", "Italian", "description", 0, 0, 0);
		Mockito.doThrow(PlayerNotFoundException.class).when(playerService).updatePlayer(1,"john.doe@sam.com", playerDto);
		var playerController = new PlayerController(playerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> playerController.updatePlayer(bearerToken, 1, playerDto));
		Mockito.verify(playerService, times(1)).updatePlayer(1, "john.doe@sam.com", playerDto);
	}

	@Test
	@DisplayName("When delete player, then operation is successful")
	void whenDeletePlayer_ThenOperationIsSuccessful() {
		var playerService = Mockito.mock(PlayerService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var playerController = new PlayerController(playerService, jwtService);

		playerController.deletePlayer(bearerToken, 2);

		Mockito.verify(playerService, times(1)).deletePlayer(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When delete player, then throw ResponseStatusException: Unauthorized")
	void whenDeletePlayer_ThenThrowResponseStatusException_Unauthorized() {
		var playerService = Mockito.mock(PlayerService.class);
		Mockito.doThrow(UnauthorizedException.class).when(playerService).deletePlayer(1,"john.doe@sam.com");
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var playerController = new PlayerController(playerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> playerController.deletePlayer(bearerToken, 1));
		Mockito.verify(playerService, times(1)).deletePlayer(1, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When delete player, then throw ResponseStatusException: Player not found")
	void whenDeletePlayer_ThenThrowResponseStatusException_PlayerNotFound() {
		var playerService = Mockito.mock(PlayerService.class);
		Mockito.doThrow(PlayerNotFoundException.class).when(playerService).deletePlayer(1,"john.doe@sam.com");
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var playerController = new PlayerController(playerService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> playerController.deletePlayer(bearerToken, 1));
		Mockito.verify(playerService, times(1)).deletePlayer(1, "john.doe@sam.com");
	}
}