package com.sam.webapi.dto;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class RefereeDtoTest {

	private RefereeDto referee;

	@BeforeEach
	void setUp() {
		referee = new RefereeDto(
				2,
				"John",
				"Doe",
				"j.doe@sam.com",
				"Password01",
				"123456789",
				"Street",
				"01-01-1970",
				"Citizenship",
				"Resume");
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(2, referee.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		referee.setId(3);
		Assertions.assertEquals(3, referee.getId());
	}

	@Test
	@DisplayName("Should get name")
	void shouldGetName() {
		Assertions.assertEquals("John", referee.getName());
	}

	@Test
	@DisplayName("Should set name")
	void shouldSetName() {
		referee.setName("Jane");
		Assertions.assertEquals("Jane", referee.getName());
	}

	@Test
	@DisplayName("Should get surname")
	void shouldGetSurname() {
		Assertions.assertEquals("Doe", referee.getSurname());
	}

	@Test
	@DisplayName("Should set surname")
	void shouldSetSurname() {
		referee.setSurname("Red");
		Assertions.assertEquals("Red", referee.getSurname());
	}

	@Test
	@DisplayName("Should get email")
	void shouldGetEmail() {
		Assertions.assertEquals("j.doe@sam.com", referee.getEmail());
	}

	@Test
	@DisplayName("Should set email")
	void shouldSetEmail() {
		referee.setEmail("j.red@sam.it");
		Assertions.assertEquals("j.red@sam.it", referee.getEmail());
	}

	@Test
	@DisplayName("Should get password")
	void shouldGetPassword() {
		Assertions.assertEquals("Password01", referee.getPassword());
	}

	@Test
	@DisplayName("Should set password")
	void shouldSetPassword() {
		referee.setPassword("Password02");
		Assertions.assertEquals("Password02", referee.getPassword());
	}

	@Test
	@DisplayName("Should get phone")
	void shouldGetPhone() {
		Assertions.assertEquals("123456789", referee.getPhone());
	}

	@Test
	@DisplayName("Should set phone")
	void shouldSetPhone() {
		referee.setPhone("987654321");
		Assertions.assertEquals("987654321", referee.getPhone());
	}

	@Test
	@DisplayName("Should get address")
	void shouldGetAddress() {
		Assertions.assertEquals("Street", referee.getAddress());
	}

	@Test
	@DisplayName("Should set address")
	void shouldSetAddress() {
		referee.setAddress("Address");
		Assertions.assertEquals("Address", referee.getAddress());
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
		var otherReferee = new RefereeDto(
				2,
				"John",
				"Doe",
				"j.doe@sam.com",
				"Password01",
				"123456789",
				"Street",
				"01-01-1970",
				"Citizenship",
				"Resume");
		Assertions.assertTrue(referee.equals(otherReferee));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherReferee = new RefereeDto(
				3,
				"John",
				"Doe",
				"j.d@sam.com",
				"Password01",
				"123456789",
				"Street",
				"01-01-1970",
				"Citizenship",
				"Resume");

		Assertions.assertNotEquals(otherReferee.hashCode(), referee.hashCode());
	}
}