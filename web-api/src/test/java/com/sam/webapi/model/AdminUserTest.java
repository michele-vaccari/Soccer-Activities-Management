package com.sam.webapi.model;

import org.junit.jupiter.api.*;

@Tag("UnitTest")
class AdminUserTest {

	private AdminUser adminUser;

	@BeforeEach
	void setUp() {
		adminUser = new AdminUser(1);
	}

	@Test
	@DisplayName("Should get id")
	void shouldGetId() {
		Assertions.assertEquals(1, adminUser.getId());
	}

	@Test
	@DisplayName("Should set id")
	void shouldSetId() {
		adminUser.setId(2);
		Assertions.assertEquals(2, adminUser.getId());
	}

	@Test
	@DisplayName("Should verify equals")
	void shouldVerifyEquals() {
		var otherAdminUser = new AdminUser(1);
		Assertions.assertTrue(adminUser.equals(otherAdminUser));
	}

	@Test
	@DisplayName("Should generate hash code")
	void shouldGenerateHashCode() {
		var otherAdminUser = new AdminUser(2);
		Assertions.assertNotEquals(otherAdminUser.hashCode(), adminUser.hashCode());
	}
}