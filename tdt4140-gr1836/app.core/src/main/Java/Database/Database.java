/*
 * Class which collects all handlers to database in one class, here we init() a connection to the Firebase, and set up all database handlers.
 * Database consists of four parts, users (all user data), workouts (all workouts for each user), inbox (messages to and from users) 
 * and statistics (summarized statistics for all users based on their workouts).
 * init() must be run first to setup the connection, the a reference can be set on the instance variable to communicate with the Firebase.
 */
package Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import Inbox.Messages;
import Statistics.Statistic;
import Statistics.Statistics;
import Users.Users;
import Workouts.Workout;
import Workouts.Workouts;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Users.User;

public class Database {
	//For handling individual parts of the database
	private UsersDatabase usersDatabase;
	private WorkoutsDatabase workoutsDatabase;
	private InboxDatabase inboxDatabase;
	private StatisticsDatabase statisticsDatabase;
	
	//The current instance to database
	private FirebaseDatabase instance;
	
	public Database()  {
		try {
			this.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.usersDatabase = new UsersDatabase(this.instance.getReference());
		this.workoutsDatabase = new WorkoutsDatabase(this.instance.getReference());
		this.inboxDatabase = new InboxDatabase(this.instance.getReference());
		this.statisticsDatabase = new StatisticsDatabase(this.instance.getReference());
	}

	public void init() throws IOException {
		try {
			FirebaseApp.getInstance();
		} catch (IllegalStateException e) {
			FileInputStream serviceAccount = new FileInputStream(("tdt4140-g36-firebase-adminsdk-u74mt-fa295def3e.json"));

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://tdt4140-g36.firebaseio.com").build();

			FirebaseApp.initializeApp(options);
		}
		this.instance = FirebaseDatabase.getInstance();
	}
	public void deleteFromDatabase(String reference) {
		this.instance.getReference().child(reference).setValueAsync(null);
	}
	//UsersDatabase communication -------------------------------------------------------------------------------------
	public User login(String username, String password) {
		return this.usersDatabase.login(username, password);
	}
	public User register(String username, String name, int age, int height, int weight, String city, boolean isMale,
			boolean isCoach, String password) {
		return this.usersDatabase.register(username, name, age, height, weight, city, isMale, isCoach, password);

	}
	public void deleteUser(String username) {
		this.usersDatabase.deleteUser(username);
	}

	public Users getUsers() {
		return this.usersDatabase.getUsers();
	}

	public void setMyCoach(String coach, String username) {
		this.usersDatabase.setMyCoach(coach, username);
	}
	
	//WorkoutsDatabase communication -------------------------------------------------------------------------------------
	public Workouts getWorkouts(String username) {
		return this.workoutsDatabase.getWorkouts(username);
	}

	public void submitCardioWorkout(Workout cdw, String username) {
		this.workoutsDatabase.submitCardioWorkout(cdw, username);
	}

	public void deleteWorkout(String username, String type, String date) {
		this.workoutsDatabase.deleteWorkout(username, type, date);
	}

	public void submitWorkoutWithoutApp(Workout cdw, String username) {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("workouts");
		DatabaseReference s = ref.child(username).child(cdw.getType()).child(cdw.getDate());
		s.setValueAsync(cdw);
	}
	
	//InboxDatabase communication -------------------------------------------------------------------------------------
	public void sendMessage(String message, String referant, String username) {
		this.inboxDatabase.sendMessage(message, referant, username);
	}

	public Map<String, Messages> loadMessages(String referant, String username) {
		return this.inboxDatabase.loadMessages(referant, username);

	}
	public ArrayList<User> getConversations(String username, Map<String, User> allUsers) {
		return this.inboxDatabase.getConversations(username, allUsers);
	}
	
	//StatisticsDatabase communication -------------------------------------------------------------------------------------
	public Statistics getStatistics() {
		return this.statisticsDatabase.getStatistics();
	}
	public void updateStatistics(Statistic statistic, String username) {
		this.statisticsDatabase.updateStatistics(statistic, username);	
	}
}
