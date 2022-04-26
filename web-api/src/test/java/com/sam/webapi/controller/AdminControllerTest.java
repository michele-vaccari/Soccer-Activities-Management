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
	@DisplayName("When get admins, then operation is successful")
	void whenGetAdmins_ThenOperationIsSuccessful() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		List<AdminDto> admins = new ArrayList<>(
				Arrays.asList(
						new AdminDto(2, "John", "Doe", "john.doe@sam.com", null, "Y"),
						new AdminDto(3, "Jane", "Doe", "jane.doe@sam.com", null, "N")
				));
		Mockito.when(adminService.getAdmins("john.doe@sam.com")).thenReturn(admins);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		var result = adminController.getAdmins(bearerToken);

		Mockito.verify(adminService, times(1)).getAdmins("john.doe@sam.com");
		Assertions.assertEquals(admins, result);
	}

	@Test
	@DisplayName("When get admins, then throw ResponseStatusException: Unauthorized")
	void whenGetAdmins_ThenThrowResponseStatusException_Unauthorized() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(adminService.getAdmins("john.doe@sam.com")).thenThrow(UnauthorizedException.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.getAdmins(bearerToken));
		Mockito.verify(adminService, times(1)).getAdmins("john.doe@sam.com");
	}

	@Test
	@DisplayName("When get admin, then operation is successful")
	void whenGetAdmin_ThenOperationIsSuccessful() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var admin = new AdminDto(2, "John", "Doe", "john.doe@sam.com", null, "Y");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		Mockito.when(adminService.getAdmin(2, "john.doe@sam.com")).thenReturn(admin);
		var adminController = new AdminController(adminService, jwtService);

		var result = adminController.getAdmin(bearerToken, 2);

		Mockito.verify(adminService, times(1)).getAdmin(2, "john.doe@sam.com");
		Assertions.assertEquals(admin, result);
	}

	@Test
	@DisplayName("When get admin, then throw ResponseStatusException: Unauthorized")
	void whenGetAdmin_ThenThrowResponseStatusException_Unauthorized() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(adminService.getAdmin(2, "john.doe@sam.com")).thenThrow(UnauthorizedException.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.getAdmin(bearerToken, 2));
		Mockito.verify(adminService, times(1)).getAdmin(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When get admin, then throw ResponseStatusException: User not found")
	void whenGetAdmin_ThenThrowResponseStatusException_UserNotFound() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.when(adminService.getAdmin(2, "john.doe@sam.com")).thenThrow(AdminUserNotFoundException.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.getAdmin(bearerToken, 2));
		Mockito.verify(adminService, times(1)).getAdmin(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When add admin, then operation is successful")
	void whenAddAdmin_ThenOperationIsSuccessful() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var admin = new AdminDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var adminController = new AdminController(adminService, jwtService);

		adminController.addAdmin(bearerToken, admin);

		Mockito.verify(adminService, times(1)).createAdmin("john.doe@sam.com", admin);
	}

	@Test
	@DisplayName("When add admin, then throw ResponseStatusException: Unauthorized")
	void whenAddAdmin_ThenThrowResponseStatusException_Unauthorized() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var admin = new AdminDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		Mockito.doThrow(UnauthorizedException.class).when(adminService).createAdmin("john.doe@sam.com", admin);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.addAdmin(bearerToken, admin));
		Mockito.verify(adminService, times(1)).createAdmin("john.doe@sam.com", admin);
	}

	@Test
	@DisplayName("When add admin, then throw ResponseStatusException: Email has already been used")
	void whenAddAdmin_ThenThrowResponseStatusException_EmailHasAlreadyBeenUsed() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var admin = new AdminDto(0, "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		Mockito.doThrow(SingleEmailConstraintException.class).when(adminService).createAdmin("john.doe@sam.com", admin);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.addAdmin(bearerToken, admin));
	}

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
	@DisplayName("When update referee, then throw ResponseStatusException: User not found")
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

	@Test
	@DisplayName("When delete admin, then operation is successful")
	void whenDeleteAdmin_ThenOperationIsSuccessful() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		adminController.deleteAdmin(bearerToken, 2);

		Mockito.verify(adminService, times(1)).deleteAdmin(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When delete admin, then throw ResponseStatusException: Unauthorized")
	void whenDeleteAdmin_ThenThrowResponseStatusException_Unauthorized() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.doThrow(UnauthorizedException.class).when(adminService).deleteAdmin(2, "john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.deleteAdmin(bearerToken, 2));
		Mockito.verify(adminService, times(1)).deleteAdmin(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When delete admin, then throw ResponseStatusException: Admin user not found")
	void whenDeleteAdmin_ThenThrowResponseStatusException_AdminUserNotFound() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.doThrow(UnauthorizedException.class).when(adminService).deleteAdmin(2, "john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.deleteAdmin(bearerToken, 2));
		Mockito.verify(adminService, times(1)).deleteAdmin(2, "john.doe@sam.com");
	}

	@Test
	@DisplayName("When delete admin, then throw ResponseStatusException: User not found")
	void whenDeleteAdmin_ThenThrowResponseStatusException_UserNotFound() {
		var adminService = Mockito.mock(AdminService.class);
		var jwtService = Mockito.mock(JwtService.class);
		Mockito.doThrow(UnauthorizedException.class).when(adminService).deleteAdmin(2, "john.doe@sam.com");
		var bearerToken = "Bearer token";
		Mockito.when(jwtService.getEmail(bearerToken)).thenReturn("john.doe@sam.com");
		var adminController = new AdminController(adminService, jwtService);

		Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.deleteAdmin(bearerToken, 2));
		Mockito.verify(adminService, times(1)).deleteAdmin(2, "john.doe@sam.com");
	}
}