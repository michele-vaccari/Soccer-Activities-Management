package com.sam.webapi.dto;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class TeamManagerDtoTest {

	private TeamManagerDto teamManager;

	@BeforeEach
	void setUp() {
		teamManager = new TeamManagerDto(
				2,
				"John",
				"Doe",
				"j.doe@sam.com",
				"Password01",
				"Y",
				"123456789",
				"Street");
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(2, teamManager.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		teamManager.setId(3);
		Assertions.assertEquals(3, teamManager.getId());
	}

	@Test
	@DisplayName("Should get name")
	void shouldGetName() {
		Assertions.assertEquals("John", teamManager.getName());
	}

	@Test
	@DisplayName("Should set name")
	void shouldSetName() {
		teamManager.setName("Jane");
		Assertions.assertEquals("Jane", teamManager.getName());
	}

	@Test
	@DisplayName("Should get surname")
	void shouldGetSurname() {
		Assertions.assertEquals("Doe", teamManager.getSurname());
	}

	@Test
	@DisplayName("Should set surname")
	void shouldSetSurname() {
		teamManager.setSurname("Red");
		Assertions.assertEquals("Red", teamManager.getSurname());
	}

	@Test
	@DisplayName("Should get email")
	void shouldGetEmail() {
		Assertions.assertEquals("j.doe@sam.com", teamManager.getEmail());
	}

	@Test
	@DisplayName("Should set email")
	void shouldSetEmail() {
		teamManager.setEmail("j.red@sam.it");
		Assertions.assertEquals("j.red@sam.it", teamManager.getEmail());
	}

	@Test
	@DisplayName("Should get password")
	void shouldGetPassword() {
		Assertions.assertEquals("Password01", teamManager.getPassword());
	}

	@Test
	@DisplayName("Should set password")
	void shouldSetPassword() {
		teamManager.setPassword("Password02");
		Assertions.assertEquals("Password02", teamManager.getPassword());
	}

	@Test
	@DisplayName("Should get active")
	void shouldGetActive() {
		Assertions.assertEquals("Y", teamManager.getActive());
	}

	@Test
	@DisplayName("Should set active")
	void shouldSetActive() {
		teamManager.setActive("N");
		Assertions.assertEquals("N", teamManager.getActive());
	}


	@Test
	@DisplayName("Should get phone")
	void shouldGetPhone() {
		Assertions.assertEquals("123456789", teamManager.getPhone());
	}

	@Test
	@DisplayName("Should set phone")
	void shouldSetPhone() {
		teamManager.setPhone("987654321");
		Assertions.assertEquals("987654321", teamManager.getPhone());
	}

	@Test
	@DisplayName("Should get address")
	void shouldGetAddress() {
		Assertions.assertEquals("Street", teamManager.getAddress());
	}

	@Test
	@DisplayName("Should set address")
	void shouldSetAddress() {
		teamManager.setAddress("Address");
		Assertions.assertEquals("Address", teamManager.getAddress());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherTeamManager = new TeamManagerDto(
				2,
				"John",
				"Doe",
				"j.doe@sam.com",
				"Password01",
				"Y",
				"123456789",
				"Street");
		Assertions.assertTrue(teamManager.equals(otherTeamManager));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherTeamManager = new TeamManagerDto(
				3,
				"John",
				"Doe",
				"j.d@sam.com",
				"Password01",
				"Y",
				"123456789",
				"Street");

		Assertions.assertNotEquals(otherTeamManager.hashCode(), teamManager.hashCode());
	}
}