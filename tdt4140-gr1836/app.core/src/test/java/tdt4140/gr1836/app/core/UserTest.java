package tdt4140.gr1836.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1836.app.users.User;
import tdt4140.gr1836.app.users.UserTempList;

public class UserTest {
	private User user;

	@Before
	public void setUp() {
		// Lag user test get og set på alle verdeir
		user = new User("TestUser", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest");
	}

	@Test
	public void testGetUsername() {
		assertEquals(user.getUsername(), "TestUser");
	}

	@Test
	public void testSetGetSalt() {
		user.setSalt("Salt");
		assertEquals(user.getSalt(), "Salt");
	}

	@Test
	public void testGetRest() {
		assertEquals(user.getName(), "Mr.David");
		assertEquals(user.getAge(), 22);
		assertEquals(user.getHeight(), 185);
		assertEquals(user.getWeight(), 80);
		assertEquals(user.getCity(), "Oslo");
		assertTrue(user.getIsMale());
		assertFalse(user.getIsCoach());
		assertEquals(user.getPassword(), "coachTest");
	}
	@Test
	public void testUserTempList() {
		UserTempList user = new UserTempList("Username", "Name", "City", "Age");
		assertEquals(user.getUsername(), "Username");
		assertEquals(user.getName(), "Name");
		assertEquals(user.getCity(), "City");
		assertEquals(user.getAge(),"Age");
	}
}
