package com.sam.webapi.service;

import com.sam.webapi.dataaccess.PlayerRepository;
import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.times;

@Tag("UnitTest")
class PlayerServiceImplTest {

	@Test
	@DisplayName("When get player, then operation is successful")
	void whenGetPlayer_ThenOperationIsSuccessful() {
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var player = Optional.of(new Player(1, 2, "Y", 10, "role", "name", "surname", "01-01-1970", "Italian", "description", 0, 0, 0));
		Mockito.when(playerRepository.findById(1)).thenReturn(player);
		var playerServiceImpl = new PlayerServiceImpl(playerRepository);

		var result = playerServiceImpl.getPlayer(1);

		Mockito.verify(playerRepository, times(1)).findById(1);
		var expectedResult = Optional.of(new PlayerDto(1, 2, "Y", 10, "role", "name", "surname", "01-01-1970", "Italian", "description", 0, 0, 0));
		Assertions.assertEquals(expectedResult, result);
	}

	@Test
	@DisplayName("When update player, then operation is successful")
	void whenUpdatePlayer_ThenOperationIsSuccessful() {
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var player = new Player(1, 2, "Y", 10, "role", "name", "surname", "01-01-1970", "Italian", "description", 0, 0, 0);
		Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		player.setTeamByTeamId(team);
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email", "password", "Y");
		registeredUser.setUserById(user);
		var playerDto = new PlayerDto(1, 2, "Y", 11, "Role", "John", "Doe", "01-01-1970", "Italian", "description", 0, 0, 0);
		var updatedPlayer = new Player(1, 2, "Y", 11, "Role", "John", "Doe", "01-01-1970", "Italian", "description", 0, 0, 0);
		var playerServiceImpl = new PlayerServiceImpl(playerRepository);

		playerServiceImpl.updatePlayer(1, "email", playerDto);

		Mockito.verify(playerRepository, times(1)).findById(1);
		Mockito.verify(playerRepository, times(1)).save(updatedPlayer);
	}

	@Test
	@DisplayName("When update player, then throw PlayerNotFoundException")
	void whenUpdatePlayer_ThenThrowPlayerNotFoundException() {
		var playerRepository = Mockito.mock(PlayerRepository.class);
		Mockito.when(playerRepository.findById(1)).thenReturn(Optional.empty());
		var playerServiceImpl = new PlayerServiceImpl(playerRepository);
		var playerDto = new PlayerDto(1, 2, "Y", 11, "Role", "John", "Doe", "01-01-1970", "Italian", "description", 0, 0, 0);

		Assertions.assertThrows(PlayerNotFoundException.class, ()-> playerServiceImpl.updatePlayer(1, "email", playerDto));
		Mockito.verify(playerRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When update player, then throw UnauthorizedException")
	void whenUpdatePlayer_ThenThrowUnauthorizedException() {
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var player = new Player(1, 2, "Y", 10, "role", "name", "surname", "01-01-1970", "Italian", "description", 0, 0, 0);
		Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		player.setTeamByTeamId(team);
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email", "password", "Y");
		registeredUser.setUserById(user);
		var playerDto = new PlayerDto(1, 2, "Y", 11, "Role", "John", "Doe", "01-01-1970", "Italian", "description", 0, 0, 0);
		var playerServiceImpl = new PlayerServiceImpl(playerRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> playerServiceImpl.updatePlayer(1, "email@sam.com", playerDto));
		Mockito.verify(playerRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When delete player, then operation is successful")
	void whenDeletePlayer_ThenOperationIsSuccessful() {
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var player = new Player(1, 2, "Y", 10, "role", "name", "surname", "01-01-1970", "Italian", "description", 0, 0, 0);
		Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		player.setTeamByTeamId(team);
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email", "password", "Y");
		registeredUser.setUserById(user);
		var deletedPlayer = new Player(1, 2, "N", 10, "role", "name", "surname", "01-01-1970", "Italian", "description", 0, 0, 0);
		var playerServiceImpl = new PlayerServiceImpl(playerRepository);

		playerServiceImpl.deletePlayer(1, "email");

		Mockito.verify(playerRepository, times(1)).findById(1);
		Mockito.verify(playerRepository, times(1)).save(deletedPlayer);
	}

	@Test
	@DisplayName("When delete player, then throw ResponseStatusException: Player not found exception")
	void whenDeletePlayer_ThenThrowResponseStatusException_PlayerNotFoundException() {
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var player = new Player(1, 2, "Y", 10, "role", "name", "surname", "01-01-1970", "Italian", "description", 0, 0, 0);
		Mockito.when(playerRepository.findById(1)).thenReturn(Optional.empty());
		var playerServiceImpl = new PlayerServiceImpl(playerRepository);

		Assertions.assertThrows(PlayerNotFoundException.class, ()-> playerServiceImpl.deletePlayer(1, "email@sam.com"));
		Mockito.verify(playerRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When delete player, then throw ResponseStatusException: Unauthorized")
	void whenDeletePlayer_ThenThrowResponseStatusException_Unauthorized() {
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var player = new Player(1, 2, "Y", 10, "role", "name", "surname", "01-01-1970", "Italian", "description", 0, 0, 0);
		Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		player.setTeamByTeamId(team);
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email", "password", "Y");
		registeredUser.setUserById(user);
		var playerServiceImpl = new PlayerServiceImpl(playerRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> playerServiceImpl.deletePlayer(1, "email@sam.com"));
		Mockito.verify(playerRepository, times(1)).findById(1);
	}
}