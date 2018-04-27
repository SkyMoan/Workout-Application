/*
 * Our main class for handling communcation to the database and our analyzing methods.
 * Used by our JavaFX application as a model in a MVC architecture.
 */
package Core;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import Users.UserTempList;
import Workouts.Workout;
import Database.Database;
import Inbox.Message;
import Inbox.Messages;
import Statistics.Statistic;
import Statistics.Statistics;
import Users.User;
import Users.Users;
import Workouts.Workouts;

public class App {
	private Database database;
	private User user;
	private Workouts workouts;
	private Map<String, User> users;
	private Map<String, User> coaches;
	private Map<String, Messages> messages;
	
	private Statistic myStatistics;
	private Statistics statistics; 

	private ArrayList<User> conversations;

	private Map<String, User> allUsers;

	public App() throws IOException {
		this.database = new Database();
		//this.database.init();
		this.user = null;
	}
	//Function for resetting all values when logging out
	public void reset() {
		this.database = new Database();
		this.user = null;
		this.workouts = new Workouts();
		this.users = new HashMap<String, User>();
		this.coaches = new HashMap<String, User>();
		this.messages = new HashMap<String, Messages>();
		
		this.myStatistics = new Statistic();
		this.statistics = new Statistics();; 

		this.conversations = new ArrayList<User>();

		this.allUsers = null;
		this.getUsersFromDatabase();
	}

	public void deleteFromDatabase(String reference) {
		this.database.deleteFromDatabase(reference);
	}
	//User managment to database------------------------------------------------------------------------------------------
	//Sets your coach to coachname
	public void setMyCoach(String coachname) {
		this.database.setMyCoach(coachname, this.user.getUsername());
		this.user.setMyCoach(coachname);
	}

	//Registers a new users in database
	public void register(String username, String name, int age, int height, int weight, String city, boolean isMale,
			boolean isCoach, String password) {
		this.user = this.database.register(username, name, age, height, weight, city, isMale, isCoach, password);
		try {
			this.workouts = new Workouts();
			this.statistics = new Statistics();
			this.myStatistics = this.statistics.updateMyStatistics(this.workouts, this.user.getAge());
		} catch (ParseException e) {
			e.printStackTrace();
			//Error happened while parsing date from database, see statisticsAnalyzer updateMyStatistics()
		}	
		if (allUsers == null) {
			allUsers = new HashMap<String, User>();
		}
		
		this.allUsers.put(username, this.user);
		this.database.updateStatistics(this.myStatistics, this.user.getUsername());
	}

	//Logs in user from database
	public User login(String username, String password) {
		this.user = this.database.login(username, password);
		return this.user;
	}

	//Deletes a user  in database
	public void deleteUser(String username) {
		this.database.deleteUser(username);
	}

	// Gets all users and sets it to either coach or user
	public void getUsersFromDatabase() {
		this.setUsers(this.database.getUsers());
	}
	//Workout managment to database------------------------------------------------------------------------------------------
	//Submits workout only
	public void onlySubmitWorkout(String type, double duration, double distance, double pulse, String date) {
		Workout cdw = new Workout(type, duration, distance, pulse, date);
		this.database.submitCardioWorkout(cdw, this.user.getUsername());
	}
	//Submits workout and updates your statistics
	public void submitCardioWorkout(String type, double duration, double distance, double pulse, String date) {
		Workout cdw = new Workout(type, duration, distance, pulse, date);
		this.database.submitCardioWorkout(cdw, this.user.getUsername());
		this.workouts.addWorkout(cdw);
		try {
			this.myStatistics = this.statistics.updateMyStatistics(this.workouts, this.user.getAge());
		} catch (ParseException e) {
			e.printStackTrace(); //Error happened while parsing date from database, see statisticsAnalyzer updateMyStatistics()
		}	
		this.updateStatistics(this.myStatistics, this.user.getUsername());
	}
	//Fetches all your workouts from database
	public void getWorkoutsFromDB() {
		this.setWorkouts(null);
		this.workouts = this.database.getWorkouts(this.getUser().getUsername());
		if (this.workouts==null) {
			this.workouts = new Workouts();
		}
	}
	//Fetches specified users workouts from database (used for finding clients workouts)
	public void getClientsWorkouts(String client) {
		this.setWorkouts(null);
		this.workouts = this.database.getWorkouts(client);
	}
	//Deletes workout in database
	public void deleteWorkout(String username, String type, String date) {
		this.database.deleteWorkout(username, type, date);
	}
	//Statistics managment to database------------------------------------------------------------------------------------------
	//Retrieves all statistics from database
	public void getStatisticsFromDB() {
		this.statistics = this.database.getStatistics();
		this.myStatistics = statistics.getStatistics().get(user.getUsername());
	}
	//Updates your statistics in database, used when inserting new workout
	public void updateStatistics(Statistic myStatistics, String username) {
		this.database.updateStatistics(myStatistics, username);
	}
	//Inbox managment to database------------------------------------------------------------------------------------------
	//Sends a message to referant
	public void sendMessage(String message, String referant) {
		database.sendMessage(message, referant, user.getUsername());
		User user = allUsers.get(referant);
		if(!conversations.contains(user) && user !=null) {
			this.conversations.add(this.allUsers.get(referant));
		}
	}
	//Retrieves specified messages from database
	public void loadMessages(String referant) {
		this.messages = null;
		this.messages = this.database.loadMessages(referant, user.getUsername());
	}
	//Retrieves conversation partners from database
	public void getConversationsFromDB() {
		this.conversations = this.database.getConversations(getUser().getUsername(), this.allUsers);
	}
	
	// GETTER & SETTERS ---------------------------------------------------------------------------------------
	//Retrieves all your conversation partners, if not yet retrieved get from database
	public ArrayList<User> getConversations() {
		if(conversations==null){
			conversations=new ArrayList<>();
			getConversationsFromDB();
		}
		return conversations;
	}
	// Helper method for presenting coaches
	public ArrayList<UserTempList> getCoachesAsList() {
		ArrayList<UserTempList> temp = new ArrayList<>();
		for (String s : coaches.keySet()) {
			UserTempList tmplist = new UserTempList(coaches.get(s).getUsername(), coaches.get(s).getName(), coaches.get(s).getCity(),
					Integer.toString(coaches.get(s).getAge()));
			temp.add(tmplist);
		}
		return temp;
	}
	//Helper method for presenting all users
	public ArrayList<UserTempList> getUsersAsList() {
		ArrayList<UserTempList> temp = new ArrayList<UserTempList>();
		for (String s : users.keySet()) {
			UserTempList tmplist = new UserTempList(users.get(s).getUsername(), users.get(s).getName(), users.get(s).getCity(),
					Integer.toString(users.get(s).getAge()));
			temp.add(tmplist);
		}
		return temp;
	}
	//Helper method for presenting all clients for coach
	public ArrayList<UserTempList> getClients() {
		ArrayList<UserTempList> allClients = new ArrayList<>();
		try {
			allUsers = this.getUsers();
			String myName = this.getUser().getUsername();
			String clientCoach;
			// userList.sort(null);
			for (String s : allUsers.keySet()) {
				clientCoach = allUsers.get(s).getMyCoach();
				if (clientCoach.equals(myName)) {
					allClients.add(new UserTempList(allUsers.get(s).getUsername(), allUsers.get(s).getName(),
							allUsers.get(s).getCity(), Integer.toString(allUsers.get(s).getAge())));
				}
			}
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		return allClients;
	}
	//Sets all users to either coach or user from all users from database
	public void setUsers(Users value) {
		allUsers=value.getUsers();
		Map<String, User> tempCoach = new HashMap<String, User>();
		Map<String, User> tempUsers = new HashMap<String, User>();
		for (String key : value.getUsers().keySet()) {
			if (value.getUsers().get(key).getIsCoach()) {
				tempCoach.put(key, value.getUsers().get(key));
			} else {
				tempUsers.put(key, value.getUsers().get(key));
			}
		}
		this.users = tempUsers;
		this.coaches = tempCoach;
	}
	public Map<String, User> getAllUsers(){
		return this.allUsers;
	}
	public Map<String, User> getUsers() {
		return this.users;
	}
	public Map<String, User> getCoaches() {
		return this.coaches;
	}
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Workouts getWorkouts() {
		return this.workouts;
	}
	public void setWorkouts(Workouts value) {
		this.workouts = value;
	}
	public void setMessages(String referant, Messages m) {
		if (messages == null) {
			messages = new HashMap<>();
		}
		this.messages.put(referant, m);
	}
	public ArrayList<Message> getMessages(String ref) {
		if (messages == null || messages.get(ref) == null) {
			loadMessages(ref);
		}
		if (messages.get(ref) == null) {
			return null;
		}
		return messages.get(ref).toList();
	}
	public Statistic getMyStatistics () {
		return this.myStatistics;
	}
	public void setMyStatistics(Statistic statistics) {
		this.myStatistics = statistics;
	}
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
		this.myStatistics = statistics.getStatistics().get(user.getUsername());
	}
	public Statistics getStatistics() {
		return this.statistics;
	}
	//Sets user in your conversation partners
	public void setConversationItem(String user){
		User temp = allUsers.get(user);
		if (temp!=null) {
			this.conversations.add(temp);
		}
	}
	//Gets your coach
	public User getMyCoach() {
		return coaches.get(getUser().getMyCoach());
	}
	
	//Statistics control ----------------------------------------------------------------------------------------------
	//Based on your statistics and the average in your city, it will return a map of type username, percentage of match on other users
	public LinkedHashMap<String, Double> findPartners(){
		return this.statistics.findPartners(this.users, this.myStatistics, this.user.getCity());
	}

}
