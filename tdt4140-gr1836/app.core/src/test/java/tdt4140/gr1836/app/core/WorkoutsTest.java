package tdt4140.gr1836.app.core;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1836.app.workouts.Workout;
import tdt4140.gr1836.app.workouts.Workouts;
import tdt4140.gr1836.app.statistics.Statistic;
import tdt4140.gr1836.app.workouts.TempList;

public class WorkoutsTest {
	private Workouts workouts;
	
	@Before
	public void setUp(){
		workouts=new Workouts();
		
		workouts.setWorkouts(new HashMap<String, Workout>(), new HashMap<String, Workout>(), new HashMap<String, Workout>()); 
		workouts.addRunningWorkout(new Workout("Running", 90, 10, 0, "2018-04-12"));
		workouts.addBikingWorkout(new Workout("Biking", 90, 10, 0, "2018-04-13"));
		workouts.addSwimmingWorkout(new Workout("Biking", 90, 10, 0, "2018-04-14"));
		
		
	}
	@Test public void getWorkoutsAndCheckValues() {
		Workouts workouts = new Workouts();
		workouts.setWorkouts(new HashMap<String, Workout>(), new HashMap<String, Workout>(), new HashMap<String, Workout>()); 
		workouts.addBikingWorkout(new Workout("Biking", 90, 10, 0, "2018-04-12"));
		workouts.addRunningWorkout(new Workout("Running", 90, 10, 0, "2018-04-12"));
		workouts.addSwimmingWorkout(new Workout("Biking", 90, 10, 0, "2018-04-12"));
		
		assertEquals(workouts.getBiking().get("2018-04-12").getDistance(), 10, 1);
	}
	
	@Test public void testWorkoutsAsList() {
		ArrayList<TempList>temp=workouts.getWorkoutsAsList();
		temp.sort(null);
		for (TempList t:temp) {
			if(t.getDate()=="2018-04-12") {
				assertEquals(t.getType(), "Running");
			}
			else if(t.getDate()=="2018-04-13"){
				assertEquals(t.getType(), "Biking");
			}
		}
	}

}
