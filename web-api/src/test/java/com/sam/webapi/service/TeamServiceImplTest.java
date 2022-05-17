package com.sam.webapi.service;

import com.google.common.collect.Iterables;
import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.AdminDto;
import com.sam.webapi.dto.PlayerDto;
import com.sam.webapi.dto.TeamDto;
import com.sam.webapi.dto.TeamManagerDto;
import com.sam.webapi.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@Tag("UnitTest")
class TeamServiceImplTest {

	@Test
	@DisplayName("When get teams, then operation is successful")
	void whenGetTeams_ThenOperationIsSuccessful() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team1 = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		var team2 = new Team(2, 2, "name2", "description2", "headquarters2", "sponsorName2");
		List<Team> teams = new ArrayList<>(
				Arrays.asList(
						team1,
						team2
				));
		Mockito.when(teamRepository.findAll()).thenReturn(teams);

		List<TeamDto> expectedResult = new ArrayList<>(
				Arrays.asList(
						new TeamDto(1, "name1", "description1", "headquarters1", "sponsorName1"),
						new TeamDto(2, "name2", "description2", "headquarters2", "sponsorName2")
				));
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		teamServiceImpl.getTeams();

		Mockito.verify(teamRepository, times(1)).findAll();
		Assertions.assertEquals(expectedResult, teamServiceImpl.getTeams());
	}

	@Test
	@DisplayName("When get team, then operation is successful")
	void whenGetTeam_ThenOperationIsSuccessful() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.of(team));

		var expectedResult = new TeamDto(1, "name1", "description1", "headquarters1", "sponsorName1");
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		teamServiceImpl.getTeam(1);

		Mockito.verify(teamRepository, times(1)).findById(1);
		Assertions.assertEquals(expectedResult, teamServiceImpl.getTeam(1));
	}

	@Test
	@DisplayName("When get team, then throw team not found exception")
	void whenGetTeam_ThenThrowTeamNotFoundException() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.empty());
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		Assertions.assertThrows(TeamNotFoundException.class, ()-> teamServiceImpl.getTeam(1));
		Mockito.verify(teamRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When update team, then operation is successful")
	void whenUpdateTeams_ThenOperationIsSuccessful() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.of(team));
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email", "password", "Y");
		registeredUser.setUserById(user);
		var teamDto = new TeamDto(1, "name", "description", "headquarters", "sponsorName");
		var updatedTeam = new Team(1, 1, "name", "description", "headquarters", "sponsorName");
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		teamServiceImpl.updateTeam(1, "email", teamDto);

		Mockito.verify(teamRepository, times(1)).findById(1);
		Mockito.verify(teamRepository, times(1)).save(updatedTeam);
	}

	@Test
	@DisplayName("When update team, then throw TeamNotFoundException")
	void whenUpdateTeams_ThenThrowTeamNotFoundException() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.empty());
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);
		var teamDto = new TeamDto(1, "name", "description", "headquarters", "sponsorName");

		Assertions.assertThrows(TeamNotFoundException.class, ()-> teamServiceImpl.updateTeam(1, "email", teamDto));
		Mockito.verify(teamRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When update team, then throw UnauthorizedException")
	void whenUpdateTeams_ThenThrowUnauthorizedException() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.of(team));
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email@sam.com", "password", "Y");
		registeredUser.setUserById(user);
		var teamDto = new TeamDto(1, "name", "description", "headquarters", "sponsorName");
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> teamServiceImpl.updateTeam(1, "email", teamDto));
		Mockito.verify(teamRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When get players of team, then operation is successful")
	void whenGetPlayersOfTeam_ThenOperationIsSuccessful() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.of(team));
		var player1 = new Player( 1, 2, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var player2 = new Player( 2, 2, "Y",1, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		List<Player> players = new ArrayList<>(
				Arrays.asList(
						player1,
						player2
				));
		team.setPlayersById(players);

		var playerDto1 = new PlayerDto( 1, 2, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var playerDto2 = new PlayerDto( 2, 2, "Y",1, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		List<PlayerDto> expectedResult = new ArrayList<>(
				Arrays.asList(
						playerDto1,
						playerDto2
				));
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		teamServiceImpl.getPlayersOfTeam(1);

		Mockito.verify(teamRepository, times(1)).findById(1);
		Assertions.assertEquals(expectedResult, teamServiceImpl.getPlayersOfTeam(1));
	}

	@Test
	@DisplayName("When get players of team, then throw team not found exception")
	void whenGetPlayersOfTeam_ThenThrowTeamNotFoundException() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.empty());
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		Assertions.assertThrows(TeamNotFoundException.class, ()-> teamServiceImpl.getPlayersOfTeam(1));
		Mockito.verify(teamRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When create player of team, then operation is successful")
	void whenCreatePlayerOfTeam_ThenOperationIsSuccessful() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email@sam.com", "password", "Y");
		registeredUser.setUserById(user);
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.of(team));
		var player1 = new Player( 1, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var player2 = new Player( 2, 1, "Y",1, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		List<Player> players = new ArrayList<>(
				Arrays.asList(
						player1,
						player2
				));
		Mockito.when(playerRepository.findByTeamIdAndActive(1, "Y")).thenReturn(players);
		Mockito.when(playerRepository.getMaxId()).thenReturn(2);
		var playerDto = new PlayerDto( 0, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);
		teamServiceImpl.createPlayerOfTeam(1, "email@sam.com", playerDto);

		Mockito.verify(teamRepository, times(1)).findById(1);
		Mockito.verify(playerRepository, times(1)).findByTeamIdAndActive(1, "Y");
		Mockito.verify(playerRepository, times(1)).getMaxId();
		Mockito.verify(playerRepository, times(1)).save(
				new Player( 3, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0)
		);
	}

	@Test
	@DisplayName("When create player of team, then throw bad request exception")
	void whenCreatePlayerOfTeam_ThenThrowBadRequestException() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email@sam.com", "password", "Y");
		registeredUser.setUserById(user);
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.of(team));
		var player1 = new Player( 1, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var player2 = new Player( 2, 1, "Y",1, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		List<Player> players = new ArrayList<>(
				Arrays.asList(
						player1,
						player2
				));
		Mockito.when(playerRepository.findByTeamIdAndActive(1, "Y")).thenReturn(players);
		Mockito.when(playerRepository.getMaxId()).thenReturn(2);
		var playerDto = new PlayerDto( 0, 1, "Y",-1, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		Assertions.assertThrows(BadRequestException.class, ()-> teamServiceImpl.createPlayerOfTeam(1, "email@sam.com", playerDto));
	}

	@Test
	@DisplayName("When create player of team, then throw team not found exception")
	void whenCreatePlayerOfTeam_ThenThrowTeamNotFoundException() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.empty());
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		var playerDto = new PlayerDto( 0, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		Assertions.assertThrows(TeamNotFoundException.class, ()-> teamServiceImpl.createPlayerOfTeam(1, "email@sam.com", playerDto));
		Mockito.verify(teamRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When create player of team, then throw unauthorized exception")
	void whenCreatePlayerOfTeam_ThenThrowUnauthorizedException() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email@sam.com", "password", "Y");
		registeredUser.setUserById(user);
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.of(team));
		var player1 = new Player( 1, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var player2 = new Player( 2, 1, "Y",1, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		List<Player> players = new ArrayList<>(
				Arrays.asList(
						player1,
						player2
				));
		Mockito.when(playerRepository.findByTeamIdAndActive(1, "Y")).thenReturn(players);
		Mockito.when(playerRepository.getMaxId()).thenReturn(2);
		var playerDto = new PlayerDto( 0, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> teamServiceImpl.createPlayerOfTeam(1, "e@sam.com", playerDto));
		Mockito.verify(teamRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When create player of team, then throw max players in team exception")
	void whenCreatePlayerOfTeam_ThenThrowMaxPlayersInTeamException() {
		var teamRepository = Mockito.mock(TeamRepository.class);
		var playerRepository = Mockito.mock(PlayerRepository.class);
		var tournamentTeamRepository = Mockito.mock(TournamentTeamRepository.class);
		var tournamentRepository = Mockito.mock(TournamentRepository.class);
		var teamPlayerReportRepository = Mockito.mock((TeamPlayerReportRepository.class));
		var team = new Team(1, 1, "name1", "description1", "headquarters1", "sponsorName1");
		var teamManager = new TeamManager(1);
		team.setTeamManagerByTeamManagerId(teamManager);
		var registeredUser = new RegisteredUser(1, 2, "phone", "address");
		teamManager.setRegisteredUserById(registeredUser);
		var user = new User(1, "TeamManager", "name", "surname", "email@sam.com", "password", "Y");
		registeredUser.setUserById(user);
		Mockito.when(teamRepository.findById(1)).thenReturn(Optional.of(team));
		var player1 = new Player( 1, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var player2 = new Player( 2, 1, "Y",1, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		List<Player> players = new ArrayList<>(
				Arrays.asList(
						player1,
						player2
				));
		final int MAX_PLAYERS_IN_TEAM = 36;
		for (int i = 0; i < MAX_PLAYERS_IN_TEAM; ++i)
			players.add(player1);
		Mockito.when(playerRepository.findByTeamIdAndActive(1, "Y")).thenReturn(players);
		Mockito.when(playerRepository.getMaxId()).thenReturn(2);
		var playerDto = new PlayerDto( 0, 1, "Y",10, "role", "name", "surname", "01-01-1970", "Italian","description", 0,0,0);
		var teamServiceImpl = new TeamServiceImpl(teamRepository, playerRepository, tournamentTeamRepository, tournamentRepository, teamPlayerReportRepository);

		Assertions.assertThrows(MaxPlayersInTeamException.class, ()-> teamServiceImpl.createPlayerOfTeam(1, "email@sam.com", playerDto));
		Mockito.verify(teamRepository, times(1)).findById(1);
		Mockito.verify(playerRepository, times(1)).findByTeamIdAndActive(1, "Y");
	}

}