package com.sam.webapi.model;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class TeamManagerTest {

	private TeamManager teamManager;

	@BeforeEach
	void setUp() {
		teamManager = new TeamManager(1);
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(1, teamManager.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		teamManager.setId(2);
		Assertions.assertEquals(2, teamManager.getId());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherTeamManager = new TeamManager(1);
		Assertions.assertTrue(teamManager.equals(otherTeamManager));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherTeamManager = new TeamManager(2);

		Assertions.assertNotEquals(otherTeamManager.hashCode(), teamManager.hashCode());
	}
}