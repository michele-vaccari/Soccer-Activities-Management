package com.sam.webapi.model;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class RefereeTest {

	private Referee referee;

	@BeforeEach
	void setUp() {
		referee = new Referee(
				1,
				"01-01-1970",
				"Citizenship",
				"Resume");
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(1, referee.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		referee.setId(2);
		Assertions.assertEquals(2, referee.getId());
	}

	@Test
	@DisplayName("Should get birth date")
	void shouldGetBirthDate() {
		Assertions.assertEquals("01-01-1970", referee.getBirthDate());
	}

	@Test
	@DisplayName("Should set birth date")
	void shouldSetBirthDate() {
		referee.setBirthDate("31-12-1970");
		Assertions.assertEquals("31-12-1970", referee.getBirthDate());
	}

	@Test
	@DisplayName("Should get citizenship")
	void shouldGetCitizenship() {
		Assertions.assertEquals("Citizenship", referee.getCitizenship());
	}

	@Test
	@DisplayName("Should set citizenship")
	void shouldSetCitizenship() {
		referee.setCitizenship("Italian");
		Assertions.assertEquals("Italian", referee.getCitizenship());
	}

	@Test
	@DisplayName("Should get resume")
	void shouldGetResume() {
		Assertions.assertEquals("Resume", referee.getResume());
	}

	@Test
	@DisplayName("Should set resume")
	void shouldSetResume() {
		referee.setResume("Summary");
		Assertions.assertEquals("Summary", referee.getResume());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherReferee = new Referee(
				1,
				"01-01-1970",
				"Citizenship",
				"Resume");
		Assertions.assertTrue(referee.equals(otherReferee));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherReferee = new Referee(
				2,
				"01-01-1970",
				"Citizenship",
				"Resume");
		Assertions.assertNotEquals(otherReferee.hashCode(), referee.hashCode());
	}
}