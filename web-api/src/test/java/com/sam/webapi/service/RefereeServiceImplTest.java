package com.sam.webapi.service;

import com.sam.webapi.dataaccess.*;
import com.sam.webapi.dto.RefereeDto;
import com.sam.webapi.model.Referee;
import com.sam.webapi.model.RegisteredUser;
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
class RefereeServiceImplTest {

	@Test
	@DisplayName("When get referees, then operation is successful")
	void whenGetReferees_ThenOperationIsSuccessful() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var user1 = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var user2 = new User(3, "Referee", "Jane", "Doe", "jane.doe@sam.com", "Password02", "N");
		var registeredUser1 = new RegisteredUser(2,1,"123456789","Street");
		var registeredUser2 = new RegisteredUser(3,1,"987654321","Street");
		var referee1 = new Referee(2, "01-01-1970", "Italian", "Resume");
		var referee2 = new Referee(3, "31-12-1970", "English", "Resume");
		registeredUser1.setUserById(user1);
		registeredUser2.setUserById(user2);
		referee1.setRegisteredUserById(registeredUser1);
		referee2.setRegisteredUserById(registeredUser2);
		List<Referee> referees = new ArrayList<>(
				Arrays.asList(
						referee1,
						referee2
				));
		Mockito.when(refereeRepository.findAll()).thenReturn(referees);
		List<RefereeDto> expectedResult = new ArrayList<>(
				Arrays.asList(
						new RefereeDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume"),
						new RefereeDto(3, "Jane", "Doe", "jane.doe@sam.com", null, "N", "987654321","Street", "31-12-1970", "English", "Resume")
				));
		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		refereeServiceImpl.getReferees("john.doe@sam.com");

		Mockito.verify(refereeRepository, times(1)).findAll();
		Assertions.assertEquals(expectedResult, refereeServiceImpl.getReferees("john.doe@sam.com"));
	}

	@Test
	@DisplayName("When get referees, then throw UnauthorizedException")
	void whenGetReferees_ThenThrowUnauthorizedException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com", "Y")).thenReturn(null);

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> refereeServiceImpl.getReferees("john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive("john.doe@sam.com", "Y");
	}

	@Test
	@DisplayName("When get referee, then operation is successful")
	void whenGetReferee_ThenOperationIsSuccessful() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var referee = new Referee(2, "01-01-1970", "Italian", "Resume");
		registeredUser.setUserById(user);
		referee.setRegisteredUserById(registeredUser);
		Mockito.when(refereeRepository.findById(1)).thenReturn(
				Optional.of(referee)
		);
		var expectedResult = new RefereeDto(2, "John", "Doe", "john.doe@sam.com", null, "Y", "123456789", "Street", "01-01-1970", "Italian", "Resume");
		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		var result = refereeServiceImpl.getReferee(1, "john.doe@sam.com");

		Mockito.verify(refereeRepository, times(1)).findById(1);
		Assertions.assertEquals(expectedResult, result);
	}

	@Test
	@DisplayName("When get referee, then throw UnauthorizedException")
	void whenGetReferee_ThenThrowUnauthorizedException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com", "Y")).thenReturn(null);

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> refereeServiceImpl.getReferee(1, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive("john.doe@sam.com", "Y");
	}

	@Test
	@DisplayName("When get referee, then throw referee not found exception")
	void whenGetReferee_ThenThrowRefereeNotFoundException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		Mockito.when(refereeRepository.findById(1)).thenThrow(RefereeNotFoundException.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(RefereeNotFoundException.class, ()-> refereeServiceImpl.getReferee(1, "john.doe@sam.com"));
		Mockito.verify(refereeRepository, times(1)).findById(1);
	}

	@Test
	@DisplayName("When create referee, then operation is successful")
	void whenCreateReferee_ThenOperationIsSuccessful() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var refereeDto = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null, "123456789","Street", "01-01-1970", "Italian", "Resume");
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(adminUser);
		Mockito.when(userRepository.existsByEmail(refereeDto.getEmail())).thenReturn(false);
		Mockito.when(userRepository.getMaxId()).thenReturn(1);
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var referee = new Referee(2, "01-01-1970", "Italian", "Resume");

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		refereeServiceImpl.createReferee(adminUser.getEmail(), refereeDto);

		Mockito.verify(userRepository, Mockito.atLeastOnce()).findByEmailAndActive(adminUser.getEmail(), "Y");
		Mockito.verify(userRepository, times(1)).existsByEmail(refereeDto.getEmail());
		Mockito.verify(userRepository, times(1)).getMaxId();
		Mockito.verify(userRepository, times(1)).save(user);
		Mockito.verify(registeredUserRepository, times(1)).save(registeredUser);
		Mockito.verify(refereeRepository, times(1)).save(referee);
	}

	@Test
	@DisplayName("When create referee, then throw UnauthorizedException")
	void whenCreateReferee_ThenThrowUnauthorizedException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		var refereeDto = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null, "123456789","Street", "01-01-1970", "Italian", "Resume");
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(null);
		Mockito.when(userRepository.existsByEmail(refereeDto.getEmail())).thenReturn(true);

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> refereeServiceImpl.createReferee(adminUser.getEmail(), refereeDto));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive(adminUser.getEmail(), "Y");
	}

	@Test
	@DisplayName("When create referee, then throw SingleEmailConstraintException")
	void whenCreateReferee_ThenThrowSingleEmailConstraintException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var refereeDto = new RefereeDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null, "123456789","Street", "01-01-1970", "Italian", "Resume");
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(adminUser);
		Mockito.when(userRepository.existsByEmail(refereeDto.getEmail())).thenReturn(true);

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> refereeServiceImpl.createReferee(adminUser.getEmail(), refereeDto));
		Mockito.verify(userRepository, Mockito.atLeastOnce()).findByEmailAndActive(adminUser.getEmail(), "Y");
		Mockito.verify(userRepository, times(1)).existsByEmail(refereeDto.getEmail());
	}

	@Test
	@DisplayName("When update referee, then operation is successful")
	void whenUpdateReferee_ThenOperationIsSuccessful() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var refereeDto = new RefereeDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "01-01-1970", "Italian", "Resume");
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var referee = new Referee(2, "01-01-1970", "Italian", "Resume");
		var expectedUser = new User(2, "Referee", "Jasmine", "Doe", "jasmine.doe@sam.com", "Password01", "Y");
		var expectedRegisteredUser = new RegisteredUser(2,1,"123456789","Street");
		var expectedReferee = new Referee(2, "01-01-1970", "Italian", "Resume");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.of(registeredUser));
		Mockito.when(refereeRepository.findById(id)).thenReturn(Optional.of(referee));

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		refereeServiceImpl.updateReferee(id, refereeDto, "john.doe@sam.com");

		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
		Mockito.verify(refereeRepository, times(1)).findById(id);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(refereeDto.getEmail(), id);
		Mockito.verify(userRepository, times(1)).save(expectedUser);
		Mockito.verify(registeredUserRepository, times(1)).save(expectedRegisteredUser);
		Mockito.verify(refereeRepository, times(1)).save(expectedReferee);
	}

	@Test
	@DisplayName("When update referee, then throw UnauthorizedException")
	void whenUpdateReferee_ThenThrowUnauthorizedException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com", "Y")).thenReturn(null);
		var refereeDto = new RefereeDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "01-01-1970", "Italian", "Resume");

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> refereeServiceImpl.updateReferee(1, refereeDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive("john.doe@sam.com", "Y");
	}

	@Test
	@DisplayName("When update referee, then throw UserNotFoundException")
	void whenUpdateReferee_ThenThrowUserNotFoundException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var refereeDto = new RefereeDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "01-01-1970", "Italian", "Resume");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> refereeServiceImpl.updateReferee(id, refereeDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update referee, then throw RegisteredUserNotFoundException")
	void whenUpdateReferee_ThenThrowRegisteredUserNotFoundException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var refereeDto = new RefereeDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "01-01-1970", "Italian", "Resume");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.empty());

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(RegisteredUserNotFoundException.class, ()-> refereeServiceImpl.updateReferee(id, refereeDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update referee, then throw RefereeNotFoundException")
	void whenUpdateReferee_ThenThrowRefereeNotFoundException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var refereeDto = new RefereeDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "01-01-1970", "Italian", "Resume");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.of(registeredUser));
		Mockito.when(refereeRepository.findById(id)).thenReturn(Optional.empty());

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(RefereeNotFoundException.class, ()-> refereeServiceImpl.updateReferee(id, refereeDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
		Mockito.verify(refereeRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update referee, then throw SingleEmailConstraintException")
	void whenUpdateReferee_ThenThrowSingleEmailConstraintException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var refereeDto = new RefereeDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y", "123456789","Street", "01-01-1970", "Italian", "Resume");
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var registeredUser = new RegisteredUser(2,1,"123456789","Street");
		var referee = new Referee(2, "01-01-1970", "Italian", "Resume");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(registeredUserRepository.findById(id)).thenReturn(Optional.of(registeredUser));
		Mockito.when(refereeRepository.findById(id)).thenReturn(Optional.of(referee));
		Mockito.when(userRepository.existsByEmailAndIdNot(refereeDto.getEmail(), id)).thenReturn(true);

		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> refereeServiceImpl.updateReferee(id, refereeDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(registeredUserRepository, times(1)).findById(id);
		Mockito.verify(refereeRepository, times(1)).findById(id);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(refereeDto.getEmail(), id);
	}

	@Test
	@DisplayName("When delete referee, then operation is successful")
	void whenDeleteReferee_ThenOperationIsSuccessful() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var id = 1;
		Mockito.when(refereeRepository.existsById(id)).thenReturn(true);
		Mockito.when(registeredUserRepository.existsById(id)).thenReturn(true);
		Mockito.when(userRepository.existsById(id)).thenReturn(true);
		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		refereeServiceImpl.deleteReferee(id, "john.doe@sam.com");

		Mockito.verify(refereeRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).deactivateUserById(id);
	}

	@Test
	@DisplayName("When delete referee, then throw UnauthorizedException")
	void whenDeleteReferee_ThenThrowUnauthorizedException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com", "Y")).thenReturn(null);
		var id = 1;
		Mockito.when(refereeRepository.existsById(id)).thenReturn(false);
		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> refereeServiceImpl.deleteReferee(id, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive("john.doe@sam.com", "Y");
	}

	@Test
	@DisplayName("When delete referee, then throw RefereeNotFoundException")
	void whenDeleteReferee_ThenThrowRefereeNotFoundException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var id = 1;
		Mockito.when(refereeRepository.existsById(id)).thenReturn(false);
		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(RefereeNotFoundException.class, ()-> refereeServiceImpl.deleteReferee(id, "john.doe@sam.com"));
		Mockito.verify(refereeRepository, times(1)).existsById(id);
	}

	@Test
	@DisplayName("When delete referee, then throw RegisteredUserNotFoundException")
	void whenDeleteReferee_ThenThrowRegisteredUserNotFoundException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var id = 1;
		Mockito.when(refereeRepository.existsById(id)).thenReturn(true);
		Mockito.when(registeredUserRepository.existsById(id)).thenReturn(false);
		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(RegisteredUserNotFoundException.class, ()-> refereeServiceImpl.deleteReferee(id, "john.doe@sam.com"));
		Mockito.verify(refereeRepository, times(1)).existsById(id);
	}

	@Test
	@DisplayName("When delete referee, then throw UserNotFoundException")
	void whenDeleteReferee_ThenThrowUserNotFoundException() {
		var refereeRepository = Mockito.mock(RefereeRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var teamRepository = Mockito.mock(TeamRepository.class);
		var teamPlayerReportRepository =  Mockito.mock(TeamPlayerReportRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var id = 1;
		Mockito.when(refereeRepository.existsById(id)).thenReturn(true);
		Mockito.when(registeredUserRepository.existsById(id)).thenReturn(true);
		Mockito.when(userRepository.existsById(id)).thenReturn(false);
		var refereeServiceImpl = new RefereeServiceImpl(refereeRepository, registeredUserRepository, userRepository, teamRepository, teamPlayerReportRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> refereeServiceImpl.deleteReferee(id, "john.doe@sam.com"));
		Mockito.verify(refereeRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).existsById(id);
	}
}