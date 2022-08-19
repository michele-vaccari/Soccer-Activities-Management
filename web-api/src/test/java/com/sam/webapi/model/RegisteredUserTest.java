package com.sam.webapi.model;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class RegisteredUserTest {

	private RegisteredUser registeredUser;

	@BeforeEach
	void setUp() {
		registeredUser = new RegisteredUser(
				1,
				1,
				"123456789",
				"Street");
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(1, registeredUser.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		registeredUser.setId(2);
		Assertions.assertEquals(2, registeredUser.getId());
	}

	@Test
	@DisplayName("Should get admin user id")
	void shouldGetAdminUserId() {
		Assertions.assertEquals(1, registeredUser.getAdminUserId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetAdminUserId() {
		registeredUser.setAdminUserId(2);
		Assertions.assertEquals(2, registeredUser.getAdminUserId());
	}

	@Test
	@DisplayName("Should get phone")
	void shouldGetPhone() {
		Assertions.assertEquals("123456789", registeredUser.getPhone());
	}

	@Test
	@DisplayName("Should set phone")
	void shouldSetPhone() {
		registeredUser.setPhone("987654321");
		Assertions.assertEquals("987654321", registeredUser.getPhone());
	}

	@Test
	@DisplayName("Should get address")
	void shouldGetAddress() {
		Assertions.assertEquals("Street", registeredUser.getAddress());
	}

	@Test
	@DisplayName("Should set address")
	void shouldSetAddress() {
		registeredUser.setAddress("Address");
		Assertions.assertEquals("Address", registeredUser.getAddress());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherRegisteredUser = new RegisteredUser(
				1,
				1,
				"123456789",
				"Street");
		Assertions.assertTrue(registeredUser.equals(otherRegisteredUser));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherRegisteredUser = new RegisteredUser(
				2,
				1,
				"123456789",
				"Street");

		Assertions.assertNotEquals(otherRegisteredUser.hashCode(), registeredUser.hashCode());
	}
}