package com.sam.webapi.dto;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class TeamDtoTest {

	private TeamDto team;

	@BeforeEach
	void setUp() {
		team = new TeamDto(
				1,
				"Hellas Verona",
				"Description",
				"Verona",
				"AGSM");
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
	@DisplayName("Should get name")
	void shouldGetName() {
		Assertions.assertEquals("Hellas Verona", team.getName());
	}

	@Test
	@DisplayName("Should set name")
	void shouldSetName() {
		team.setName("SPAL");
		Assertions.assertEquals("SPAL", team.getName());
	}

	@Test
	@DisplayName("Should get description")
	void shouldGetDescription() {
		Assertions.assertEquals("Description", team.getDescription());
	}

	@Test
	@DisplayName("Should set description")
	void shouldSetDescription() {
		team.setDescription("description");
		Assertions.assertEquals("description", team.getDescription());
	}

	@Test
	@DisplayName("Should get headquarters")
	void shouldGetHeadquarters() {
		Assertions.assertEquals("Verona", team.getHeadquarters());
	}

	@Test
	@DisplayName("Should set headquarters")
	void shouldSetHeadquarters() {
		team.setHeadquarters("Ferrara");
		Assertions.assertEquals("Ferrara", team.getHeadquarters());
	}

	@Test
	@DisplayName("Should get sponsor name")
	void shouldGetSponsorName() {
		Assertions.assertEquals("AGSM", team.getSponsorName());
	}

	@Test
	@DisplayName("Should set sponsor name")
	void shouldSetSponsorName() {
		team.setSponsorName("Inter Spar");
		Assertions.assertEquals("Inter Spar", team.getSponsorName());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherTeam = new TeamDto(
				1,
				"Hellas Verona",
				"Description",
				"Verona",
				"AGSM");
		Assertions.assertTrue(team.equals(otherTeam));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherTeam = new TeamDto(
				2,
				"Hellas Verona",
				"Description",
				"Verona",
				"AGSM");

		Assertions.assertNotEquals(otherTeam.hashCode(), team.hashCode());
	}
}