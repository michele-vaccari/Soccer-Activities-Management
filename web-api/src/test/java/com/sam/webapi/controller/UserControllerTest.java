package com.sam.webapi.controller;

import com.sam.webapi.model.User;
import com.sam.webapi.service.SingleEmailConstraintException;
import com.sam.webapi.service.UserNotFoundException;
import com.sam.webapi.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Tag("UnitTest")
class UserControllerTest {

	@Test
	@DisplayName("When get users, then operation is successful")
	void whenGetUsers_ThenOperationIsSuccessful() {
		var userService = Mockito.mock(UserService.class);
		List<User> users = new ArrayList<>(
				Arrays.asList(
						new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y"),
						new User(2, "Referee", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y"),
						new User(3, "TeamManager", "Jason", "Doe", "jason.doe@sam.com", "Password03", "N")
				));
		Mockito.when(userService.getUsers()).thenReturn(users);
		var userController = new UserController(userService);

		var result = userController.getUsers();

		Mockito.verify(userService, times(1)).getUsers();
		Assertions.assertEquals(users, result);
	}

	@Test
	@DisplayName("When get user, then operation is successful")
	void whenGetUser_ThenOperationIsSuccessful() {
		var userService = Mockito.mock(UserService.class);
		var user = Optional.of(new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y"));
		Mockito.when(userService.getUser(1)).thenReturn(user);
		var userController = new UserController(userService);

		var result = userController.getUser(1);

		Mockito.verify(userService, times(1)).getUser(1);
		Assertions.assertEquals(user, result);
	}

	@Test
	@DisplayName("When get user, then throw ResponseStatusException: User not found")
	void whenGetUser_ThenThrowResponseStatusException_UserNotFound() {
		var userService = Mockito.mock(UserService.class);
		Optional<User> emptyUser = Optional.empty();
		Mockito.when(userService.getUser(1)).thenReturn(emptyUser);
		var userController = new UserController(userService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> userController.getUser(1));
		Mockito.verify(userService, times(1)).getUser(1);
	}

	@Test
	@DisplayName("When add user, then operation is successful")
	void whenAddUser_ThenOperationIsSuccessful() {
		var userService = Mockito.mock(UserService.class);
		var user = new User(0, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var userController = new UserController(userService);

		userController.addUser(user);

		Mockito.verify(userService, times(1)).createUser(user);
	}

	@Test
	@DisplayName("When add user, then throw ResponseStatusException: Email has already been used")
	void whenAddUser_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var userService = Mockito.mock(UserService.class);
		var user = new User(0, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		Mockito.doThrow(SingleEmailConstraintException.class).when(userService).createUser(user);
		var userController = new UserController(userService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> userController.addUser(user));
	}

	@Test
	@DisplayName("When update user, then operation is successful")
	void whenUpdateUser_ThenOperationIsSuccessful() {
		var userService = Mockito.mock(UserService.class);
		var user = new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var userController = new UserController(userService);

		userController.updateUser(1, user);

		Mockito.verify(userService, times(1)).updateUser(1, user);
	}

	@Test
	@DisplayName("When update user, then throw ResponseStatusException: User not found")
	void whenUpdateUser_ThenThrowResponseStatusException_UserNotFound() {
		var userService = Mockito.mock(UserService.class);
		var user = new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		Mockito.doThrow(UserNotFoundException.class).when(userService).updateUser(1, user);
		var userController = new UserController(userService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> userController.updateUser(1, user));
	}

	@Test
	@DisplayName("When update user, then throw ResponseStatusException: Email has already been used")
	void whenUpdateUser_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var userService = Mockito.mock(UserService.class);
		var user = new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		Mockito.doThrow(SingleEmailConstraintException.class).when(userService).updateUser(1, user);
		var userController = new UserController(userService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> userController.updateUser(1, user));
	}

	@Test
	@DisplayName("When delete user, then operation is successful")
	void whenDeleteUser_ThenOperationIsSuccessful() {
		var userService = Mockito.mock(UserService.class);
		var userController = new UserController(userService);

		userController.deleteUser(1);

		Mockito.verify(userService, times(1)).deleteUser(1);
	}

	@Test
	@DisplayName("When delete user, then throw ResponseStatusException: User not found")
	void whenDeleteUser_ThenThrowResponseStatusException_UserNotFound() {
		var userService = Mockito.mock(UserService.class);
		Mockito.doThrow(UserNotFoundException.class).when(userService).deleteUser(1);
		var userController = new UserController(userService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> userController.deleteUser(1));
	}
}