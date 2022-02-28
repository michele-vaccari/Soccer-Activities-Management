package com.sam.webapi.service;

import com.sam.webapi.dataaccess.AdminUserRepository;
import com.sam.webapi.dataaccess.RegisteredUserRepository;
import com.sam.webapi.dataaccess.UserRepository;
import com.sam.webapi.dto.AdminDto;
import com.sam.webapi.model.AdminUser;
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
class AdminServiceImplTest {

	@Test
	@DisplayName("When get admins, then operation is successful")
	void whenGetAdmins_ThenOperationIsSuccessful() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var user1 = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var user2 = new User(3, "Referee", "Jane", "Doe", "jane.doe@sam.com", "Password02", "N");
		var adminUser1 = new AdminUser(2);
		var adminUser2 = new AdminUser(3);
		adminUser1.setUserById(user1);
		adminUser2.setUserById(user2);
		List<AdminUser> admins = new ArrayList<>(
				Arrays.asList(
						adminUser1,
						adminUser2
				));
		Mockito.when(adminUserRepository.findAll()).thenReturn(admins);
		List<AdminDto> expectedResult = new ArrayList<>(
				Arrays.asList(
						new AdminDto(2, "John", "Doe", "john.doe@sam.com", null, "Y"),
						new AdminDto(3, "Jane", "Doe", "jane.doe@sam.com", null, "N")
				));
		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		adminServiceImpl.getAdmins();

		Mockito.verify(adminUserRepository, times(1)).findAll();
		Assertions.assertEquals(expectedResult, adminServiceImpl.getAdmins());
	}

	@Test
	@DisplayName("When get admin, then operation is successful")
	void whenGetAdmin_ThenOperationIsSuccessful() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var user = new User(2, "Referee", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var adminUser = new AdminUser(2);
		adminUser.setUserById(user);
		Mockito.when(adminUserRepository.findById(1)).thenReturn(
				Optional.of(adminUser)
		);
		var expectedResult = new AdminDto(2, "John", "Doe", "john.doe@sam.com", null, "Y");
		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		var result = adminServiceImpl.getAdmin(1);

		Mockito.verify(adminUserRepository, times(1)).findById(1);
		Assertions.assertEquals(expectedResult, result.get());
	}

	@Test
	@DisplayName("When create admin, then operation is successful")
	void whenCreateAdmin_ThenOperationIsSuccessful() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var adminDto = new AdminDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null);
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(adminUser);
		Mockito.when(userRepository.existsByEmail(adminDto.getEmail())).thenReturn(false);
		Mockito.when(userRepository.getMaxId()).thenReturn(1);
		var user = new User(2, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var admin = new AdminUser(2);

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		adminServiceImpl.createAdmin(adminUser.getEmail(), adminDto);

		Mockito.verify(userRepository, times(1)).findByEmailAndActive(adminUser.getEmail(), "Y");
		Mockito.verify(userRepository, times(1)).existsByEmail(adminDto.getEmail());
		Mockito.verify(userRepository, times(1)).getMaxId();
		Mockito.verify(userRepository, times(1)).save(user);
		Mockito.verify(adminUserRepository, times(1)).save(admin);
	}

	@Test
	@DisplayName("When create admin, then throw AdminUserNotFoundException")
	void whenCreateAdmin_ThenThrowAdminUserNotFoundException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var adminDto = new AdminDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null);
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(null);
		Mockito.when(userRepository.existsByEmail(adminDto.getEmail())).thenReturn(true);

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(AdminUserNotFoundException.class, ()-> adminServiceImpl.createAdmin(adminUser.getEmail(), adminDto));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive(adminUser.getEmail(), "Y");
	}


	@Test
	@DisplayName("When create admin, then throw SingleEmailConstraintException")
	void whenCreateAdmin_ThenThrowSingleEmailConstraintException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var registeredUserRepository = Mockito.mock(RegisteredUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var adminDto = new AdminDto(0, "John", "Doe", "john.doe@sam.com", "Password01", null);
		var adminUser = new User(1, "Admin", "Jane", "Doe", "jane.doe@sam.com", "Password02", "Y");
		Mockito.when(userRepository.findByEmailAndActive(adminUser.getEmail(), "Y")).thenReturn(adminUser);
		Mockito.when(userRepository.existsByEmail(adminDto.getEmail())).thenReturn(true);

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> adminServiceImpl.createAdmin(adminUser.getEmail(), adminDto));
		Mockito.verify(userRepository, times(1)).findByEmailAndActive(adminUser.getEmail(), "Y");
		Mockito.verify(userRepository, times(1)).existsByEmail(adminDto.getEmail());
	}

	@Test
	@DisplayName("When update admin, then operation is successful")
	void whenUpdateAdmin_ThenOperationIsSuccessful() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var user = new User(2, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var admin = new AdminUser(2);
		var expectedUser = new User(2, "Admin", "Jasmine", "Doe", "jasmine.doe@sam.com", "Password01", "Y");
		var expectedAdmin = new AdminUser(2);
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(adminUserRepository.findById(id)).thenReturn(Optional.of(admin));

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		adminServiceImpl.updateAdmin(id, adminDto);

		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(adminUserRepository, times(1)).findById(id);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(adminDto.getEmail(), id);
		Mockito.verify(userRepository, times(1)).save(expectedUser);
		Mockito.verify(adminUserRepository, times(1)).save(expectedAdmin);
	}

	@Test
	@DisplayName("When update admin, then throw UserNotFoundException")
	void whenUpdateAdmin_ThenThrowUserNotFoundException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> adminServiceImpl.updateAdmin(id, adminDto));
		Mockito.verify(userRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update admin, then throw AdminUserNotFoundException")
	void whenUpdateAdmin_ThenThrowAdminUserNotFoundException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var user = new User(2, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(adminUserRepository.findById(id)).thenReturn(Optional.empty());

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(AdminUserNotFoundException.class, ()-> adminServiceImpl.updateAdmin(id, adminDto));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(adminUserRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("When update admin, then throw SingleEmailConstraintException")
	void whenUpdateAdmin_ThenThrowSingleEmailConstraintException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var adminDto = new AdminDto(0, "Jasmine", "Doe", "jasmine.doe@sam.com", null, "Y");
		var user = new User(2, "Admin", "John", "Doe", "john.doe@sam.com", "Password01", "Y");
		var admin = new AdminUser(2);
		var id = 1;
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Mockito.when(adminUserRepository.findById(id)).thenReturn(Optional.of(admin));
		Mockito.when(userRepository.existsByEmailAndIdNot(adminDto.getEmail(), id)).thenReturn(true);

		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(SingleEmailConstraintException.class, ()-> adminServiceImpl.updateAdmin(id, adminDto));
		Mockito.verify(userRepository, times(1)).findById(id);
		Mockito.verify(adminUserRepository, times(1)).findById(id);
		Mockito.verify(userRepository, times(1)).existsByEmailAndIdNot(adminDto.getEmail(), id);
	}

	@Test
	@DisplayName("When delete admin, then operation is successful")
	void whenDeleteAdmin_ThenOperationIsSuccessful() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var id = 1;
		Mockito.when(adminUserRepository.existsById(id)).thenReturn(true);
		Mockito.when(userRepository.existsById(id)).thenReturn(true);
		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		adminServiceImpl.deleteAdmin(id);

		Mockito.verify(adminUserRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).deactivateUserById(id);
	}

	@Test
	@DisplayName("When delete admin, then throw AdminUserNotFoundException")
	void whenDeleteAdmin_ThenThrowAdminUserNotFoundException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var id = 1;
		Mockito.when(adminUserRepository.existsById(id)).thenReturn(false);
		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(AdminUserNotFoundException.class, ()-> adminServiceImpl.deleteAdmin(id));
		Mockito.verify(adminUserRepository, times(1)).existsById(id);
	}

	@Test
	@DisplayName("When delete admin, then throw UserNotFoundException")
	void whenDeleteAdmin_ThenThrowUserNotFoundException() {
		var adminUserRepository = Mockito.mock(AdminUserRepository.class);
		var userRepository = Mockito.mock(UserRepository.class);
		var id = 1;
		Mockito.when(adminUserRepository.existsById(id)).thenReturn(true);
		Mockito.when(userRepository.existsById(id)).thenReturn(false);
		var adminServiceImpl = new AdminServiceImpl(adminUserRepository, userRepository);

		Assertions.assertThrows(UserNotFoundException.class, ()-> adminServiceImpl.deleteAdmin(id));
		Mockito.verify(adminUserRepository, times(1)).existsById(id);
		Mockito.verify(userRepository, times(1)).existsById(id);
	}
}