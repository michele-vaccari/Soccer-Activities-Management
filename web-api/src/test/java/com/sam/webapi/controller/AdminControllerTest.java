package com.sam.webapi.controller;

import com.sam.webapi.dto.AdminDto;
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
class AdminControllerTest {

	@Test
	@DisplayName("When update admin, then operation is successful")
	void whenUpdateAdmin_ThenOperationIsSuccessful() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var admin = new AdminDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		adminController.updateAdmin(bearerToken, 2, admin);

		Mockito.verify(adminService, times(1)).updateAdmin(2, admin, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When update admin, then throw ResponseStatusException: Unauthorized")
	void whenUpdateAdmin_ThenThrowResponseStatusException_Unauthorized() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var admin = new AdminDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null);
		Mockito.doThrow(UnauthorizedException.class).when(adminService).updateAdmin(2, admin,"john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.updateAdmin(bearerToken, 2, admin));
		Mockito.verify(adminService, times(1)).updateAdmin(2, admin, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When update admin, then throw ResponseStatusException: Admin user not found")
	void whenUpdateAdmin_ThenThrowResponseStatusException_AdminUserNotFound() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var admin = new AdminDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null);
		Mockito.doThrow(AdminUserNotFoundException.class).when(adminService).updateAdmin(2, admin,"john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.updateAdmin(bearerToken, 2, admin));
		Mockito.verify(adminService, times(1)).updateAdmin(2, admin, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When update admin, then throw ResponseStatusException: User not found")
	void whenUpdateAdmin_ThenThrowResponseStatusException_UserNotFound() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var admin = new AdminDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null);
		Mockito.doThrow(UserNotFoundException.class).when(adminService).updateAdmin(2, admin,"john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.updateAdmin(bearerToken, 2, admin));
		Mockito.verify(adminService, times(1)).updateAdmin(2, admin, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When update admin, then throw ResponseStatusException: Email has already been used")
	void whenUpdateAdmin_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var admin = new AdminDto(0, "Jane", "Doe", "jane.doe@sam.com", null, null);
		Mockito.doThrow(SingleEmailConstraintException.class).when(adminService).updateAdmin(2, admin,"john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.updateAdmin(bearerToken, 2, admin));
		Mockito.verify(adminService, times(1)).updateAdmin(2, admin, "john.doe@sam.com");
	}
}