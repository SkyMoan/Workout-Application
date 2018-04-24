package tdt4140.gr1836.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1836.app.statistics.Statistic;
import tdt4140.gr1836.app.statistics.Statistics;
import tdt4140.gr1836.app.users.User;
import tdt4140.gr1836.app.workouts.Workout;
import tdt4140.gr1836.app.workouts.Workouts;

public class StatisticTest {
	private Statistic statistic;
	private Statistics statistics;
	private Map <String, User> users;
	@Before
	public void setUp() {
		users = new HashMap<String, User>();
		users.put("1",  new User("1", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest"));
		users.put("2", new User("2", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest"));
		users.put("3",  new User("3", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest"));
		users.put("4", new User("4", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest"));
		Map <String, Statistic> allStatistics = new HashMap<String, Statistic>();
		allStatistics.put("1", new Statistic(200.0, 0, 10, 10, 100, 100, 100, 1, 1, 4, 150, 150, 150));
		allStatistics.put("2", new Statistic(200.0, 0, 10, 10, 100, 100, 100, 1, 1, 4, 150, 150, 150));
		allStatistics.put("3", new Statistic(200.0, 0, 30, 10, 300, 100, 100, 1, 1, 2, 150, 150, 150));
		allStatistics.put("4", new Statistic(200.0, 0, 30, 10, 300, 100, 100, 1, 1, 6, 150, 150, 150));
		statistics = new Statistics();
		statistics.setStatistics(allStatistics);
	}

	@Test
	public void testCalculateAverage() {
		Statistic avg = statistics.calculateAverageInCity(users, "Oslo");
		assertEquals(avg.getBikeKm(), 20);
		assertEquals(avg.getRunMin(), 200);
		assertEquals(avg.getTotalSwims(), 4);
	}

	@Test
	public void testGetPartners() {
		users.put("me", new User("me", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest"));
		users.put("match", new User("match", "Mr.David", 22, 185, 80, "Oslo", true, false, "coachTest"));
		Statistic myStatistics = new Statistic(200.0, 2000, 2000, 2000, 100, 100, 100, 1, 1, 4, 150, 150, 150);
		statistics.getStatistics().put("me", myStatistics);
		statistics.getStatistics().put("match", new Statistic(200.0, 2000, 2000, 2000, 100, 100, 100, 1, 1, 10, 150, 150, 150));
		LinkedHashMap<String, Double> partners = statistics.findPartners(users, myStatistics, "Oslo");
		List<String> names = new ArrayList<String>(partners.keySet());
		String name = names.get(0);
		assertEquals(name, "me");
		assertEquals(names.get(1), "match");
		assertTrue(partners.get("match")>0.5 && partners.get("match")<1);
	}
	@Test
	public void testUpdateMyStatistics() throws ParseException {
		Workouts workouts = new Workouts();
		workouts.setWorkouts(new HashMap<String, Workout>(), new HashMap<String, Workout>(), new HashMap<String, Workout>()); 
		int age = 22;
		workouts.addBikingWorkout(new Workout("Biking", 90, 10, 0, "2018-04-12"));
		workouts.addRunningWorkout(new Workout("Running", 90, 10, 0, "2018-04-12"));
		workouts.addSwimmingWorkout(new Workout("Biking", 90, 10, 0, "2018-04-12"));
		Statistic myStatistic = statistics.updateMyStatistics(workouts, age);
		
		workouts.addRunningWorkout(new Workout("Running", 270, 30, 0, "2018-04-13"));
		Statistic updatedMyStatistic = statistics.updateMyStatistics(workouts, age);
		
		assertEquals(updatedMyStatistic.getRunKm(), myStatistic.getRunKm()*4);
		assertEquals(updatedMyStatistic.getRunMin(), myStatistic.getRunMin()*4);
		
	}

}
