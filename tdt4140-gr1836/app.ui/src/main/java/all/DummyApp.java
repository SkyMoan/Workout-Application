package all;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import Core.App;
import Statistics.Statistic;
import Statistics.Statistics;
import Users.User;
import Users.UserTempList;
import Workouts.Workout;
import Workouts.Workouts;

public class DummyApp extends App {
	
	private User user;
	private User correctUser;
	private User coachUser;
	private Workouts workouts;
	private Statistics statistics;
	private Statistic statistic;
	private Map<String, User> users;
	private Map<String, User> allUsers;
	private Map<String, User> coaches;
	
	
	public DummyApp() throws IOException {
		this.user=null;
		this.coachUser = new User("coachFxBoy", "Mr.Coach", 20, 185, 80, "Trondheim", true, true, "coachTest");
		this.correctUser = new User("testFxBoy", "Mr.TestFx", 20, 185, 80, "Trondheim", true, false, "coachTest");
		correctUser.setMyCoach("coachFxBoy");
		users = new HashMap<String, User>();
		coaches = new HashMap<String, User>();
		coaches.put("coachFxBoy", coachUser);
		users.put("testFxBoy", correctUser);
		users.put("temp1", new User("temp1", "Mr.TestFx", 20, 185, 80, "Trondheim", true, false, "coachTest"));	
		users.put("temp2", new User("temp2", "Mr.TestFx", 20, 185, 80, "Trondheim", true, false, "coachTest"));	
		users.put("temp3", new User("temp3", "Mr.TestFx", 20, 185, 80, "Trondheim", true, false, "coachTest"));	
		
		allUsers = users;
		allUsers.put("coachFxBoy", coachUser);
		
		Map <String, Statistic> allStatistics = new HashMap<String, Statistic>();
		statistic = new Statistic(200.0, 0, 10, 10, 100, 100, 100, 1, 1, 4, 150, 150, 150);
		allStatistics.put("testFxBoy", statistic);
		allStatistics.put("temp1", new Statistic(200.0, 0, 30, 10, 300, 100, 100, 1, 1, 2, 150, 150, 150));
		allStatistics.put("temp2", new Statistic(200.0, 0, 30, 10, 300, 100, 100, 1, 1, 6, 150, 150, 150));
		allStatistics.put("temp3", new Statistic(200.0, 0, 10, 10, 100, 100, 100, 1, 1, 4, 150, 150, 150));

		statistics = new Statistics();
		statistics.setStatistics(allStatistics);	
	}
	public void reset() {
		
	}
	public void register(String username, String name, int age, int height, int weight, String city, boolean isMale,
			boolean isCoach, String password) {
	System.out.println("Register");
	}
	public User login(String username, String password) {
		
		if(username.equals("testFxBoy") && password.equals("test")) {
			return correctUser;
		}
		else if(username.equals("coachFxBoy") && password.equals("coach"))  {
			return coachUser;
		}
		else {
			return user;
		}
		
	}
	public User getUser() {
		return this.correctUser;
	}
	//Submits workout and updates your statistics
	public void submitCardioWorkout(String type, double duration, double distance, double pulse, String date) {
		Workout cdw = new Workout(type, duration, distance, pulse, date);
		this.workouts.addWorkout(cdw);
	}
	public void deleteUser(String username) {
		System.out.println("Delete user");
		this.correctUser=null;
	}
	public void getWorkoutsFromDB() {
		workouts=new Workouts();
	}
	public void getStatisticsFromDB() {
		//Allready done in decleration
	}
	public Statistics getStatistics() {
		return this.statistics;
	}
	public Statistic getMyStatistics() {
		return this.statistic;
	}
	public LinkedHashMap<String, Double> findPartners() {
		LinkedHashMap<String, Double> partners = this.statistics.findPartners(users, statistic, correctUser.getCity());
		return partners;
	}
	public ArrayList<UserTempList> getClients(){
		ArrayList<UserTempList> allClients = new ArrayList<>();
		allClients.add(new UserTempList(correctUser.getUsername(), correctUser.getName(),
				correctUser.getCity(), Integer.toString(correctUser.getAge())));
		return allClients;

	}

}