package com.sam.webapi.dto;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class AdminDtoTest {

	private AdminDto admin;

	@BeforeEach
	void setUp() {
		admin = new AdminDto(
				2,
				"John",
				"Doe",
				"j.doe@sam.com",
				"Password01",
				"Y");
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(2, admin.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		admin.setId(3);
		Assertions.assertEquals(3, admin.getId());
	}

	@Test
	@DisplayName("Should get name")
	void shouldGetName() {
		Assertions.assertEquals("John", admin.getName());
	}

	@Test
	@DisplayName("Should set name")
	void shouldSetName() {
		admin.setName("Jane");
		Assertions.assertEquals("Jane", admin.getName());
	}

	@Test
	@DisplayName("Should get surname")
	void shouldGetSurname() {
		Assertions.assertEquals("Doe", admin.getSurname());
	}

	@Test
	@DisplayName("Should set surname")
	void shouldSetSurname() {
		admin.setSurname("Red");
		Assertions.assertEquals("Red", admin.getSurname());
	}

	@Test
	@DisplayName("Should get email")
	void shouldGetEmail() {
		Assertions.assertEquals("j.doe@sam.com", admin.getEmail());
	}

	@Test
	@DisplayName("Should set email")
	void shouldSetEmail() {
		admin.setEmail("j.red@sam.it");
		Assertions.assertEquals("j.red@sam.it", admin.getEmail());
	}

	@Test
	@DisplayName("Should get password")
	void shouldGetPassword() {
		Assertions.assertEquals("Password01", admin.getPassword());
	}

	@Test
	@DisplayName("Should set password")
	void shouldSetPassword() {
		admin.setPassword("Password02");
		Assertions.assertEquals("Password02", admin.getPassword());
	}

	@Test
	@DisplayName("Should get active")
	void shouldGetActive() {
		Assertions.assertEquals("Y", admin.getActive());
	}

	@Test
	@DisplayName("Should set active")
	void shouldSetActive() {
		admin.setActive("N");
		Assertions.assertEquals("N", admin.getActive());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherAdmin = new AdminDto(
				2,
				"John",
				"Doe",
				"j.doe@sam.com",
				"Password01",
				"Y");
		Assertions.assertTrue(admin.equals(otherAdmin));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherAdmin = new AdminDto(
				3,
				"John",
				"Doe",
				"j.d@sam.com",
				"Password01",
				"Y");

		Assertions.assertNotEquals(otherAdmin.hashCode(), admin.hashCode());
	}
}