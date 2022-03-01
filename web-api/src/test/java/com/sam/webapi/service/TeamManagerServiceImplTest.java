package com.sam.webapi.service;

import com.sam.webapi.dataaccess.RegisteredUserRepository;
import com.sam.webapi.dataaccess.TeamManagerRepository;
import com.sam.webapi.dataaccess.TeamRepository;
import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.dto.TeamManagerDto;
import com.sam.webapi.model.RegisteredUser;
import com.sam.webapi.model.Team;
import com.sam.webapi.model.TeamManager;
import com.sam.webapi.model.User;
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
class TeamManagerServiceImplTest {

	@Test
	@DisplayName("When get team managers, then operation is successful")
	void whenGetTeamManagers_ThenOperationIsSuccessful() {
		var teamManagersRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var user1 = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var user2 = new User(3, "Referee", "Jane", "Doe", "jane.doe@sam.com", "Password02", "N");
		var registeredUser1 = new RegisteredUser(2,1,"123456789","Street");
		var registeredUser2 = new RegisteredUser(3,1,"987654321","Street");
		var teamManager1 = new TeamManager(2);
		var teamManager2 = new TeamManager(3);
		var team1 = new Team(1,2,"Team1", null, null, null);
		var team2 = new Team(2,3,"Team2", null, null, null);
		registeredUser1.setUserById(user1);
		registeredUser2.setUserById(user2);
		teamManager1.setRegisteredUserById(registeredUser1);
		teamManager2.setRegisteredUserById(registeredUser2);
		teamManager1.setTeamById(team1);
		teamManager2.setTeamById(team2);
		List<TeamManager> teamManagers = new ArrayList<>(
				Arrays.asList(
						teamManager1,
						teamManager2
				));
		Mockito.when(teamManagersRepository.findAll()).thenReturn(teamManagers);
		List<TeamManagerDto> expectedResult = new ArrayList<>(
				Arrays.asList(
						new TeamManagerDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "Team1"),
						new TeamManagerDto(3, "Jane", "Doe", "jane.doe@sam.com", null, "N", "987654321","Street", "Team2")
				));
		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagersRepository, registeredUserRepository, userRepository, teamRepository);

		teamManagerServiceImpl.getTeamManagers();

		Mockito.verify(teamManagersRepository, times(1)).findAll();
		Assertions.assertEquals(expectedResult, teamManagerServiceImpl.getTeamManagers());
	}

	@Test
	@DisplayName("When get team manager, then operation is successful")
	void whenGetTeamManager_ThenOperationIsSuccessful() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var teamManager = new TeamManager(2);
		var team = new Team(1,2,"Team1", null, null, null);
		registeredUser.setUserById(user);
		teamManager.setRegisteredUserById(registeredUser);
		teamManager.setTeamById(team);
		Mockito.when(teamManagerRepository.findById(1)).thenReturn(
				Optional.of(teamManager)
		);
		var expectedResult = new TeamManagerDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "Team1");
		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		var result = teamManagerServiceImpl.getTeamManager(1);

		Mockito.verify(teamManagerRepository, times(1)).findById(1);
		Assertions.assertEquals(expectedResult, result.get());
	}

	@Test
	@DisplayName("When create team manager, then operation is successful")
	void whenCreateTeamManager_ThenOperationIsSuccessful() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamManagerDto = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null, "123456789","Street", "Team1");
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(adminUser);
		Mockito.when(userRepository.existsByEmail(teamManagerDto.getEmail())).thenReturn(false);
		Mockito.when(userRepository.getMaxId()).thenReturn(1);
		var user = new User(2, "TeamManager", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var teamManager = new TeamManager(2);
		var team = new Team(1,2,"Team1", null, null, null);
		teamManager.setTeamById(team);

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		teamManagerServiceImpl.createTeamManager(adminUser.getEmail(), teamManagerDto);

		Mockito.verify(userRepository, times(1)).findByEmailAndActive(adminUser.getEmail(), "Y");
		Mockito.verify(userRepository, times(1)).existsByEmail(teamManagerDto.getEmail());
		Mockito.verify(userRepository, times(1)).getMaxId();
		Mockito.verify(userRepository, times(1)).save(user);
		Mockito.verify(registeredUserRepository, times(1)).save(registeredUser);
		Mockito.verify(teamManagerRepository, times(1)).save(teamManager);
		Mockito.verify(teamRepository, times(1)).save(team);
	}

	@Test
	@DisplayName("When create team manager, then throw AdminUserNotFoundException")
	void whenCreateTeamManager_ThenThrowAdminUserNotFoundException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamManagerDto = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null, "123456789", "Street", "Team1");
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(null);
		Mockito.when(userRepository.existsByEmail(teamManagerDto.getEmail())).thenReturn(true);

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(AdminUserNotFoundException.class, ()-> teamManagerServiceImpl.createTeamManager(adminUser.getEmail(), teamManagerDto));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive(adminUser.getEmail(), "Y");
	}


	@Test
	@DisplayName("When create team manager, then throw SingleEmailConstraintException")
	void whenCreateTeamManager_ThenThrowSingleEmailConstraintException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamManagerDto = new TeamManagerDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null, "123456789","Street", "Team1");
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(adminUser);
		Mockito.when(userRepository.existsByEmail(teamManagerDto.getEmail())).thenReturn(true);

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> teamManagerServiceImpl.createTeamManager(adminUser.getEmail(), teamManagerDto));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive(adminUser.getEmail(), "Y");
		Mockito.verify(userRepository, times(1)).existsByEmail(teamManagerDto.getEmail());
	}

	@Test
	@DisplayName("When update team manager, then operation is successful")
	void whenUpdateTeamManager_ThenOperationIsSuccessful() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamManagerDto = new TeamManagerDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "Team1");
		var user = new User(2, "TeamManager", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var teamManager = new TeamManager(2);
		var team = new Team(1,2,"Team1", null, null, null);
		var expectedUser = new User(2, "TeamManager", "Jasmine", "Doe", "jasmine.doe@sam.com", "Password01", "Y");
		var expectedRegisteredUser = new RegisteredUser(2,1,"123456789","Street");
		var expectedTeamManager = new TeamManager(2);
		var expectedTeam = new Team(1,2,"Team1", null, null, null);
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.of(registeredUser));
		Mockito.when(teamManagerRepository.findById(id)).thenReturn(Optional.of(teamManager));
		Mockito.when(teamRepository.findByTeamManagerId(id)).thenReturn(Optional.of(team));

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		teamManagerServiceImpl.updateTeamManager(id, teamManagerDto);

		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
		Mockito.verify(teamManagerRepository, times(1)).findById(id);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(teamManagerDto.getEmail(), id);
		Mockito.verify(teamRepository, times(1)).findByTeamManagerId(id);
		Mockito.verify(userRepository, times(1)).save(expectedUser);
		Mockito.verify(registeredUserRepository, times(1)).save(expectedRegisteredUser);
		Mockito.verify(teamManagerRepository, times(1)).save(expectedTeamManager);
		Mockito.verify(teamRepository, times(1)).save(expectedTeam);
	}

	@Test
	@DisplayName("When update team manager, then throw UserNotFoundException")
	void whenUpdateTeamManager_ThenThrowUserNotFoundException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamManagerDto = new TeamManagerDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "Team1");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> teamManagerServiceImpl.updateTeamManager(id, teamManagerDto));
		Mockito.verify(userRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update team manager, then throw RegisteredUserNotFoundException")
	void whenUpdateTeamManager_ThenThrowRegisteredUserNotFoundException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var teamManagerDto = new TeamManagerDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "Team1");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.empty());

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(RegisteredUserNotFoundException.class, ()-> teamManagerServiceImpl.updateTeamManager(id, teamManagerDto));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update team manager, then throw TeamManagerNotFoundException")
	void whenUpdateReferee_ThenThrowTeamManagerNotFoundException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var teamManagerDto = new TeamManagerDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "Team1");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.of(registeredUser));
		Mockito.when(teamManagerRepository.findById(id)).thenReturn(Optional.empty());

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(TeamManagerNotFoundException.class, ()-> teamManagerServiceImpl.updateTeamManager(id, teamManagerDto));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
		Mockito.verify(teamManagerRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update team manager, then throw TeamNotFoundException")
	void whenUpdateTeamManager_ThenThrowTeamNotFoundException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamManagerDto = new TeamManagerDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "Team1");
		var user = new User(2, "TeamManager", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var teamManager = new TeamManager(2);
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.of(registeredUser));
		Mockito.when(teamManagerRepository.findById(id)).thenReturn(Optional.of(teamManager));
		Mockito.when(teamRepository.findByTeamManagerId(id)).thenReturn(Optional.empty());

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(TeamNotFoundException.class, ()-> teamManagerServiceImpl.updateTeamManager(id, teamManagerDto));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
		Mockito.verify(teamManagerRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update team manager, then throw SingleEmailConstraintException")
	void whenUpdateTeamManager_ThenThrowSingleEmailConstraintException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamManagerDto = new TeamManagerDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "Team1");
		var user = new User(2, "TeamManager", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var teamManager = new TeamManager(2);
		var team = new Team(1, 2, "Team1", null, null, null);
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.of(registeredUser));
		Mockito.when(teamManagerRepository.findById(id)).thenReturn(Optional.of(teamManager));
		Mockito.when(teamRepository.findByTeamManagerId(id)).thenReturn(Optional.of(team));
		Mockito.when(userRepository.existsByEmailAndIdNot(teamManagerDto.getEmail(), id)).thenReturn(true);

		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> teamManagerServiceImpl.updateTeamManager(id, teamManagerDto));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
		Mockito.verify(teamManagerRepository, times(1)).findById(id);
		Mockito.verify(teamRepository, times(1)).findByTeamManagerId(id);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(teamManagerDto.getEmail(), id);
	}

	@Test
	@DisplayName("When delete team manager, then operation is successful")
	void whenDeleteTeamManager_ThenOperationIsSuccessful() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var id = 1;
		Mockito.when(teamManagerRepository.existsById(id)).thenReturn(true);
		Mockito.when(registeredUserRepository.existsById(id)).thenReturn(true);
		Mockito.when(userRepository.existsById(id)).thenReturn(true);
		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		teamManagerServiceImpl.deleteTeamManager(id);

		Mockito.verify(teamManagerRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).deactivateUserById(id);
	}

	@Test
	@DisplayName("When delete team manager, then throw TeamManagerNotFoundException")
	void whenDeleteTeamManager_ThenThrowTeamManagerNotFoundException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var id = 1;
		Mockito.when(teamManagerRepository.existsById(id)).thenReturn(false);
		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(TeamManagerNotFoundException.class, ()-> teamManagerServiceImpl.deleteTeamManager(id));
		Mockito.verify(teamManagerRepository, times(1)).existsById(id);
	}

	@Test
	@DisplayName("When delete team manager, then throw RegisteredUserNotFoundException")
	void whenDeleteTeamManager_ThenThrowRegisteredUserNotFoundException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var id = 1;
		Mockito.when(teamManagerRepository.existsById(id)).thenReturn(true);
		Mockito.when(registeredUserRepository.existsById(id)).thenReturn(false);
		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(RegisteredUserNotFoundException.class, ()-> teamManagerServiceImpl.deleteTeamManager(id));
		Mockito.verify(teamManagerRepository, times(1)).existsById(id);
	}

	@Test
	@DisplayName("When delete team manger, then throw UserNotFoundException")
	void whenDeleteTeamManager_ThenThrowUserNotFoundException() {
		var teamManagerRepository = Mockito.mock(TeamManagerRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var id = 1;
		Mockito.when(teamManagerRepository.existsById(id)).thenReturn(true);
		Mockito.when(registeredUserRepository.existsById(id)).thenReturn(true);
		Mockito.when(userRepository.existsById(id)).thenReturn(false);
		var teamManagerServiceImpl = new TeamManagerServiceImpl(teamManagerRepository, registeredUserRepository, userRepository, teamRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> teamManagerServiceImpl.deleteTeamManager(id));
		Mockito.verify(teamManagerRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).existsById(id);
	}
}