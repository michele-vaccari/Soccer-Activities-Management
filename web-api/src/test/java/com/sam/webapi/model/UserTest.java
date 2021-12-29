package com.sam.webapi.model;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class UserTest {

	private User user;

	@BeforeEach
	void setUp() {
		user = new User(
				1,
				"SystemAdministrator",
				"John",
				"Doe",
				"j.doe@sam.com",
				"Password",
				"Y");
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(1, user.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		user.setId(2);
		Assertions.assertEquals(2, user.getId());
	}

	@Test
	@DisplayName("Should get type")
	void shouldGetType() {
		Assertions.assertEquals("SystemAdministrator", user.getType());
	}

	@Test
	@DisplayName("Should set type")
	void shouldSetType() {
		user.setType("Refree");
		Assertions.assertEquals("Refree", user.getType());
	}

	@Test
	@DisplayName("Should get name")
	void shouldGetName() {
		Assertions.assertEquals("John", user.getName());
	}

	@Test
	@DisplayName("Should set name")
	void shouldSetName() {
		user.setName("Jane");
		Assertions.assertEquals("Jane", user.getName());
	}

	@Test
	@DisplayName("Should get surname")
	void shouldGetSurname() {
		Assertions.assertEquals("Doe", user.getSurname());
	}

	@Test
	@DisplayName("Should set surname")
	void shouldSetSurname() {
		user.setSurname("Red");
		Assertions.assertEquals("Red", user.getSurname());
	}

	@Test
	@DisplayName("Should get email")
	void shouldGetEmail() {
		Assertions.assertEquals("j.doe@sam.com", user.getEmail());
	}

	@Test
	@DisplayName("Should set email")
	void shouldSetEmail() {
		user.setEmail("j.red@sam.it");
		Assertions.assertEquals("j.red@sam.it", user.getEmail());
	}

	@Test
	@DisplayName("Should get password")
	void shouldGetPassword() {
		Assertions.assertEquals("Password", user.getPassword());
	}

	@Test
	@DisplayName("Should set password")
	void shouldSetPassword() {
		user.setPassword("Password01");
		Assertions.assertEquals("Password01", user.getPassword());
	}

	@Test
	@DisplayName("Should get is active")
	void shouldGetIsActive() {
		Assertions.assertEquals("Y", user.getIsActive());
	}

	@Test
	@DisplayName("Should set is active")
	void shouldSetIsActive() {
		user.setIsActive("N");
		Assertions.assertEquals("N", user.getIsActive());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherUser = new User(
				1,
				"SystemAdministrator",
				"John",
				"Doe",
				"j.doe@sam.com",
				"Password",
				"Y");
		Assertions.assertTrue(user.equals(otherUser));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherUser = new User(
				2,
				"Refree",
				"Jane",
				"Doe",
				"j.doe@sam.com",
				"Password",
				"Y");

		Assertions.assertNotEquals(otherUser.hashCode(), user.hashCode());
	}
}