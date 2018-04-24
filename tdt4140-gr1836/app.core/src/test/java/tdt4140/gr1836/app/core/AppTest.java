//Tests our App and conncetion to Database at the same time
package tdt4140.gr1836.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1836.app.db.Database;
import tdt4140.gr1836.app.inbox.Message;
import tdt4140.gr1836.app.inbox.Messages;
import tdt4140.gr1836.app.statistics.Statistic;
import tdt4140.gr1836.app.statistics.Statistics;
import tdt4140.gr1836.app.users.User;
import tdt4140.gr1836.app.users.UserTempList;
import tdt4140.gr1836.app.workouts.Workout;
import tdt4140.gr1836.app.workouts.Workouts;

public class AppTest {
	private App app;
	private User correctCoach = new User("TestCoach", "Mr.Coach", 22, 185, 80, "Oslo", true, true, "coachTest");
	private User correctUser = new User("TestUser", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest");
	private User user;

	// Registers a new user to be tested on
	@BeforeClass
	public static void oneTimeSetUp() throws IOException {
		App app = new App();
		app.setUser(new User("TestUser", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest"));
		app.register("TestUser", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest");
		app.onlySubmitWorkout("Running", 90, 10, 160, "2018-04-15");
		app.onlySubmitWorkout("Biking", 80, 10, 160, "2018-04-15");
		app.onlySubmitWorkout("Swimming", 70, 10, 160, "2018-04-15");

		//Register coach
		app.register("TestCoach", "Mr.Coach", 22, 185, 80, "Oslo", true, true, "coachTest");
	}

	@AfterClass
	public static void oneTimeCleanUp() throws IOException {
		App app = new App();
		app.deleteUser("TestUser");
		app.deleteUser("TestCoach");
		app.deleteWorkout("TestUser", "Running", "2018-04-15");
		app.deleteWorkout("TestUser", "Biking", "2018-04-15");
		app.deleteWorkout("TestUser", "Swimming", "2018-04-15");
		app.deleteFromDatabase("inbox/TestUser");
		app.deleteFromDatabase("inbox/TestCoach");
	}

	@Before
	public void setUp() throws IOException {
		app = new App();
	}
	// @Test public void register_user(){
	// @Test public void register_existing_user(){
	// @Test public void register_invalid_user(){

	@Test
	public void checkWorkoutsFromDB() {
		app.setUser(correctUser);
		app.getWorkoutsFromDB();
		Workouts workouts = app.getWorkouts();
		Map<String, Workout> running = workouts.getRunning();
		Map<String, Workout> biking = workouts.getBiking();
		Map<String, Workout> swimming = workouts.getSwimming();
		assertEquals(running.get("2018-04-15").getType(), "Running");
		assertEquals(biking.get("2018-04-15").getType(), "Biking");
		assertEquals(swimming.get("2018-04-15").getType(), "Swimming");
		
	}

	@Test
	public void testGetCoaches() {
		app.getUsersFromDatabase();
		Map<String, User> coaches = app.getCoaches();
		User t = coaches.get("TestCoach");
		assertEquals(t.getCity(), correctCoach.getCity());
	}

	@Test
	public void try_login_wrong_input() {
		user = app.login("TestUser", "BanaltPassord");
		assertEquals(user, null);
		user = app.login("Banaluser", "BanaltPassord");
		assertEquals(user, null);
	}

	@Test
	public void login() {
		user = app.login("TestUser", "coachTest");
		assertEquals(user.getName(), correctUser.getName());
		assertEquals(user.getAge(), correctUser.getAge());
		assertEquals(user.getCity(), correctUser.getCity());
		assertEquals(user.getHeight(), correctUser.getHeight());
	}

	@Test
	public void loginCoach() {
		user = app.login("TestCoach", "coachTest");
		assertEquals(user.getName(), correctCoach.getName());
		assertTrue(user.getIsCoach());
	}
	@Test
	public void testCoachClients() {
		app.setUser(correctUser);
		app.setMyCoach("TestCoach");
		assertEquals(app.getUser().getMyCoach(), "TestCoach");
		app.setUser(correctCoach);
		app.getUsersFromDatabase();
		ArrayList<UserTempList> allClients = app.getClients();
		assertEquals(allClients.get(0).getUsername(), "TestUser");
	}
	@Test
	public void getUsersCoachesAsList() {
		app.getUsersFromDatabase();
		ArrayList<UserTempList> tempUsers = app.getUsersAsList();
		ArrayList<UserTempList> tempCoaches = app.getCoachesAsList();
		assertFalse(tempUsers.isEmpty());
		assertFalse(tempCoaches.isEmpty());
	}

	@Test
	public void delete_account_and_try_login() {
		/*
		app.getUsersFromDatabase();
		Map<String, User> users = app.getUsers();
		assertEquals(users.get("TestUser").getName(), "Mr.David");
		app.deleteUser("TestUser");
		app.getUsersFromDatabase();
		users = app.getUsers();
		assertEquals(users.get("TestUser"), null);
		app.register("TestUser", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest");
		*/
	}
	@Test
	public void testReset() {
		app.setUser(correctUser);
		app.reset();
		assertEquals(app.getUser(), null);
	}
	@Test
	public void testSubmitNewWorkout() {
		app.setUser(correctUser);
		app.deleteWorkout(correctUser.getUsername(), "Running", "2018-04-15");
		app.getWorkoutsFromDB();
		app.getStatisticsFromDB();
		Workouts workouts = app.getWorkouts();
		assertEquals(workouts.getRunning(), null);
		app.submitCardioWorkout("Running", 90, 10, 160, "2018-04-15");
		
		Statistic myStatistic = app.getMyStatistics();
		assertEquals(myStatistic.getRunKm(),10, 1);
	}
	@Test
	public void testSendGetMessageAndConversation() {
		app.setUser(correctUser);
		app.getUsersFromDatabase();
		ArrayList<User> conv = app.getConversations();
		assertTrue(conv.isEmpty());
		app.sendMessage("Hallo!", "TestCoach");
		conv = app.getConversations();
		assertEquals(conv.get(0).getUsername(), "TestCoach");
		app.loadMessages("TestUser");
		ArrayList<Message> messages = app.getMessages("TestCoach");
		assertEquals(messages.get(0).getMessage(), "Hallo!");
	}

}
