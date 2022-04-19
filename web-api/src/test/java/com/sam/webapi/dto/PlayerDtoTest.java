package com.sam.webapi.dto;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class PlayerDtoTest {

	private PlayerDto player;

	@BeforeEach
	void setUp() {
		player = new PlayerDto(
				1,
				2,
				"Y",
				10,
				"Role",
				"John",
				"Doe",
				"01-01-1970",
				"Italian",
				"description",
				0,
				0,
				0);
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(1, player.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		player.setId(3);
		Assertions.assertEquals(3, player.getId());
	}

	@Test
	@DisplayName("Should get team id")
	void shouldGetTeamId() {
		Assertions.assertEquals(2, player.getTeamId());
	}

	@Test
	@DisplayName("Should set team id")
	void shouldSetTeamId() {
		player.setTeamId(3);
		Assertions.assertEquals(3, player.getTeamId());
	}

	@Test
	@DisplayName("Should get active")
	void shouldGetActive() {
		Assertions.assertEquals("Y", player.getActive());
	}

	@Test
	@DisplayName("Should set active")
	void shouldSetActive() {
		player.setActive("N");
		Assertions.assertEquals("N", player.getActive());
	}

	@Test
	@DisplayName("Should get jersey number")
	void shouldGetJerseyNumber() {
		Assertions.assertEquals(10, player.getJerseyNumber());
	}

	@Test
	@DisplayName("Should set jersey number")
	void shouldSetJerseyNumber() {
		player.setJerseyNumber(2);
		Assertions.assertEquals(2, player.getJerseyNumber());
	}

	@Test
	@DisplayName("Should get role")
	void shouldGetRole() {
		Assertions.assertEquals("Role", player.getRole());
	}

	@Test
	@DisplayName("Should set role")
	void shouldSetRole() {
		player.setRole("role");
		Assertions.assertEquals("role", player.getRole());
	}

	@Test
	@DisplayName("Should get name")
	void shouldGetName() {
		Assertions.assertEquals("John", player.getName());
	}

	@Test
	@DisplayName("Should set name")
	void shouldSetName() {
		player.setName("Jane");
		Assertions.assertEquals("Jane", player.getName());
	}

	@Test
	@DisplayName("Should get surname")
	void shouldGetSurname() {
		Assertions.assertEquals("Doe", player.getSurname());
	}

	@Test
	@DisplayName("Should set surname")
	void shouldSetSurname() {
		player.setSurname("Red");
		Assertions.assertEquals("Red", player.getSurname());
	}

	@Test
	@DisplayName("Should get birth date")
	void shouldGetBirthDate() {
		Assertions.assertEquals("01-01-1970", player.getBirthDate());
	}

	@Test
	@DisplayName("Should set birth date")
	void shouldSetBirthDate() {
		player.setBirthDate("01-01-1980");
		Assertions.assertEquals("01-01-1980", player.getBirthDate());
	}

	@Test
	@DisplayName("Should get citizenship")
	void shouldGetCitizenship() {
		Assertions.assertEquals("Italian", player.getCitizenship());
	}

	@Test
	@DisplayName("Should set citizenship")
	void shouldSetCitizenship() {
		player.setCitizenship("American");
		Assertions.assertEquals("American", player.getCitizenship());
	}

	@Test
	@DisplayName("Should get description")
	void shouldGetDescription() {
		Assertions.assertEquals("description", player.getDescription());
	}

	@Test
	@DisplayName("Should set description")
	void shouldSetDescription() {
		player.setDescription("Description");
		Assertions.assertEquals("Description", player.getDescription());
	}

	@Test
	@DisplayName("Should get goal")
	void shouldGetGoal() {
		Assertions.assertEquals(0, player.getGoal());
	}

	@Test
	@DisplayName("Should set goal")
	void shouldSetGoal() {
		player.setGoal(10);
		Assertions.assertEquals(10, player.getGoal());
	}

	@Test
	@DisplayName("Should get admonitions")
	void shouldGetAdmonitions() {
		Assertions.assertEquals(0, player.getAdmonitions());
	}

	@Test
	@DisplayName("Should set admonitions")
	void shouldSetAdmonitions() {
		player.setAdmonitions(10);
		Assertions.assertEquals(10, player.getAdmonitions());
	}

	@Test
	@DisplayName("Should get ejections")
	void shouldGetEjections() {
		Assertions.assertEquals(0, player.getEjections());
	}

	@Test
	@DisplayName("Should set ejections")
	void shouldSetEjections() {
		player.setEjections(10);
		Assertions.assertEquals(10, player.getEjections());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherPlayer = new PlayerDto(
				1,
				2,
				"Y",
				10,
				"Role",
				"John",
				"Doe",
				"01-01-1970",
				"Italian",
				"description",
				0,
				0,
				0);
		Assertions.assertTrue(player.equals(otherPlayer));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherPlayer = new PlayerDto(2,
				2,
				"Y",
				10,
				"Role",
				"John",
				"Doe",
				"01-01-1970",
				"Italian",
				"description",
				0,
				0,
				0);

		Assertions.assertNotEquals(otherPlayer.hashCode(), player.hashCode());
	}
}