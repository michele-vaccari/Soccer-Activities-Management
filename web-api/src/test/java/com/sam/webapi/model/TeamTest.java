package com.sam.webapi.model;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class TeamTest {

	private Team team;

	@BeforeEach
	void setUp() {
		team = new Team(1,
						2,
						"name",
						"description",
						"headquarters",
						"sponsorName");
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(1, team.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		team.setId(2);
		Assertions.assertEquals(2, team.getId());
	}

	@Test
	@DisplayName("Should get team manager id")
	void shouldGetTeamManagerId() {
		Assertions.assertEquals(2, team.getTeamManagerId());
	}

	@Test
	@DisplayName("Should set team manager id")
	void shouldSetTeamManagerId() {
		team.setTeamManagerId(3);
		Assertions.assertEquals(3, team.getTeamManagerId());
	}

	@Test
	@DisplayName("Should get name")
	void shouldGetName() {
		Assertions.assertEquals("name", team.getName());
	}

	@Test
	@DisplayName("Should set name")
	void shouldSetName() {
		team.setName("Name");
		Assertions.assertEquals("Name", team.getName());
	}

	@Test
	@DisplayName("Should get description")
	void shouldGetDescription() {
		Assertions.assertEquals("description", team.getDescription());
	}

	@Test
	@DisplayName("Should set description")
	void shouldSetDescription() {
		team.setDescription("Description");
		Assertions.assertEquals("Description", team.getDescription());
	}

	@Test
	@DisplayName("Should get headquarters")
	void shouldGetHeadquarters() {
		Assertions.assertEquals("headquarters", team.getHeadquarters());
	}

	@Test
	@DisplayName("Should set headquarters")
	void shouldSetHeadquarters() {
		team.setHeadquarters("Headquarters");
		Assertions.assertEquals("Headquarters", team.getHeadquarters());
	}

	@Test
	@DisplayName("Should get sponsor name")
	void shouldGetSponsorName() {
		Assertions.assertEquals("sponsorName", team.getSponsorName());
	}

	@Test
	@DisplayName("Should set sponsor name")
	void shouldSetSponsorName() {
		team.setSponsorName("SponsorName");
		Assertions.assertEquals("SponsorName", team.getSponsorName());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherTeam = new Team(1,
								 2,
								 "name",
								 "description",
								 "headquarters",
								 "sponsorName");
		Assertions.assertTrue(team.equals(otherTeam));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherTeam = new Team(2,
								 2,
								 "name",
								 "description",
								 "headquarters",
								 "sponsorName");

		Assertions.assertNotEquals(otherTeam.hashCode(), team.hashCode());
	}
}