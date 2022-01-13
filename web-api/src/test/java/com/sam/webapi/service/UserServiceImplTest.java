package com.sam.webapi.service;

import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.model.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Tag("UnitTest")
class UserServiceImplTest {

	@Test
	@DisplayName("When get users, then operation is successful")
	void whenGetUsers_ThenOperationIsSuccessful() {
		var userRepository = Mockito.mock(UserRepository.class);
		List<User> users = new ArrayList<>(
				Arrays.asList(
						new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y"),
						new User(2, "Referee", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y"),
						new User(3, "TeamManager", "Jason", "Doe", "jason.doe@sam.com", "Password03", "N")
				));
		Mockito.when(userRepository.findAll()).thenReturn(users);
		var userServiceImpl = new UserServiceImpl(userRepository);

		userServiceImpl.getUsers();

		Mockito.verify(userRepository, times(1)).findAll();
		Assertions.assertEquals(users, userServiceImpl.getUsers());
	}

	@Test
	@DisplayName("When get user, then operation is successful")
	void whenGetUser_ThenOperationIsSuccessful() {
		var userRepository = Mockito.mock(UserRepository.class);
		var user = new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		Mockito.when(userRepository.findById(1)).thenReturn(
				Optional.of(user)
		);
		var userServiceImpl = new UserServiceImpl(userRepository);

		var result = userServiceImpl.getUser(1);

		Mockito.verify(userRepository, times(1)).findById(1);
		Assertions.assertEquals(user, result.get());
	}

	@Test
	@DisplayName("When create user, then operation is successful")
	void whenCreateUser_ThenOperationIsSuccessful() {
		var userRepository = Mockito.mock(UserRepository.class);
		var user = new User(0, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "");
		Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
		Mockito.when(userRepository.getMaxId()).thenReturn(3);
		var userServiceImpl = new UserServiceImpl(userRepository);

		userServiceImpl.createUser(user);

		Mockito.verify(userRepository, times(1)).existsByEmail(user.getEmail());
		Mockito.verify(userRepository, times(1)).getMaxId();
		Mockito.verify(userRepository, times(1)).save(user);
		var expectedUser = new User(4, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		Assertions.assertTrue(expectedUser.equals(user));
	}

	@Test
	@DisplayName("When create user, then throw SingleEmailConstraintException")
	void whenCreateUser_ThenThrowSingleEmailConstraintException() {
		var userRepository = Mockito.mock(UserRepository.class);
		var user = new User(0, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "");
		Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
		var userServiceImpl = new UserServiceImpl(userRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> userServiceImpl.createUser(user));
		Mockito.verify(userRepository, times(1)).existsByEmail(user.getEmail());
	}

	@Test
	@DisplayName("When update user, then operation is successful")
	void whenUpdateUser_ThenOperationIsSuccessful() {
		var userRepository = Mockito.mock(UserRepository.class);
		var user = new User(0, "Admin", "John", "Doe", "john.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.existsById(1)).thenReturn(true);
		Mockito.when(userRepository.existsByEmailAndIdNot(user.getEmail(), 1)).thenReturn(false);
		var userServiceImpl = new UserServiceImpl(userRepository);

		userServiceImpl.updateUser(1, user);

		Mockito.verify(userRepository, times(1)).existsById(1);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(user.getEmail(), 1);
		Mockito.verify(userRepository, times(1)).save(user);
		var expectedUser = new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "Password02", "Y");
		Assertions.assertTrue(expectedUser.equals(user));
	}

	@Test
	@DisplayName("When update user, then throw UserNotFoundException")
	void whenUpdateUser_ThenThrowUserNotFoundException() {
		var userRepository = Mockito.mock(UserRepository.class);
		var user = new User(0, "Admin", "John", "Doe", "john.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.existsById(1)).thenReturn(false);
		var userServiceImpl = new UserServiceImpl(userRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> userServiceImpl.updateUser(1, user));
		Mockito.verify(userRepository, times(1)).existsById(1);
	}

	@Test
	@DisplayName("When update user, then throw SingleEmailConstraintException")
	void whenUpdateUser_ThenThrowSingleEmailConstraintException() {
		var userRepository = Mockito.mock(UserRepository.class);
		var user = new User(0, "Admin", "John", "Doe", "john.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.existsById(1)).thenReturn(true);
		Mockito.when(userRepository.existsByEmailAndIdNot(user.getEmail(), 1)).thenReturn(true);
		var userServiceImpl = new UserServiceImpl(userRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> userServiceImpl.updateUser(1, user));
		Mockito.verify(userRepository, times(1)).existsById(1);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(user.getEmail(), 1);
	}

	@Test
	@DisplayName("When delete user, then operation is successful")
	void whenDeleteUser_ThenOperationIsSuccessful() {
		var userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.existsById(1)).thenReturn(true);
		var userServiceImpl = new UserServiceImpl(userRepository);

		userServiceImpl.deleteUser(1);

		Mockito.verify(userRepository, times(1)).existsById(1);
		Mockito.verify(userRepository, times(1)).deactivateUserById(1);
	}

	@Test
	@DisplayName("When delete user, then throw UserNotFoundException")
	void whenDeleteUser_ThenThrowUserNotFoundException() {
		var userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.existsById(1)).thenReturn(false);
		var userServiceImpl = new UserServiceImpl(userRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> userServiceImpl.deleteUser(1));
		Mockito.verify(userRepository, times(1)).existsById(1);
	}
}