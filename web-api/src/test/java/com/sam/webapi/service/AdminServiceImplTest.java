package com.sam.webapi.service;

import com.sam.webapi.dataaccess.AdminUserRepository;
import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.dto.AdminDto;
import com.sam.webapi.model.AdminUser;
import com.sam.webapi.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.times;

@Tag("UnitTest")
class AdminServiceImplTest {

	@Test
	@DisplayName("When update admin, then operation is successful")
	void whenUpdateAdmin_ThenOperationIsSuccessful() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var user = new User(2, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var admin = new AdminUser(2);
		var expectedUser = new User(2, "Admin", "Jasmine", "Doe", "jasmine.doe@sam.com", "Password01", "Y");
		var expectedAdmin = new AdminUser(2);
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(adminUserRepository.findById(id)).thenReturn(Optional.of(admin));

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		adminServiceImpl.updateAdmin(id, adminDto, "john.doe@sam.com");

		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(adminUserRepository, times(1)).findById(id);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(adminDto.getEmail(), id);
		Mockito.verify(userRepository, times(1)).save(expectedUser);
		Mockito.verify(adminUserRepository, times(1)).save(expectedAdmin);
	}

	@Test
	@DisplayName("When update admin, then throw UnauthorizedException")
	void whenUpdateAdmin_ThenThrowUnauthorizedException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				null
		);
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(UnauthorizedException.class, ()-> adminServiceImpl.updateAdmin(id, adminDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive("john.doe@sam.com", "Y");
	}

	@Test
	@DisplayName("When update admin, then throw UserNotFoundException")
	void whenUpdateAdmin_ThenThrowUserNotFoundException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> adminServiceImpl.updateAdmin(id, adminDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update admin, then throw AdminUserNotFoundException")
	void whenUpdateAdmin_ThenThrowAdminUserNotFoundException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var user = new User(2, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(adminUserRepository.findById(id)).thenReturn(Optional.empty());

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(AdminUserNotFoundException.class, ()-> adminServiceImpl.updateAdmin(id, adminDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(adminUserRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update admin, then throw SingleEmailConstraintException")
	void whenUpdateAdmin_ThenThrowSingleEmailConstraintException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.findByEmailAndActive("john.doe@sam.com","Y")).thenReturn(
				new User(1, "Admin", "John", "Doe", "john.doe@sam.com", "password", "Y")
		);
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var user = new User(2, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var admin = new AdminUser(2);
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(adminUserRepository.findById(id)).thenReturn(Optional.of(admin));
		Mockito.when(userRepository.existsByEmailAndIdNot(adminDto.getEmail(), id)).thenReturn(true);

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> adminServiceImpl.updateAdmin(id, adminDto, "john.doe@sam.com"));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(adminUserRepository, times(1)).findById(id);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(adminDto.getEmail(), id);
	}
}