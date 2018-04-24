package tdt4140.gr1836.app.db;

import com.google.firebase.database.DatabaseReference;

import tdt4140.gr1836.app.users.User;
import tdt4140.gr1836.app.users.Users;
import tdt4140.gr1836.app.workouts.Workouts;

//Class which mostly contains waitfordatabase which all fetchers to database implemets when waiting for a response
public class CommonDatabase {
	protected User user = null;
	protected Users users;
	protected Workouts workouts;
	protected boolean waitForDatabase;
	
	//References in database
	protected DatabaseReference reference;
	
	public void waitForDatabase() {
		this.waitForDatabase = true;
		int timer = 0;
		while (this.waitForDatabase) {
			try {
				Thread.sleep(300);
				timer += 1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (timer > 100) {
				System.out.println("Took too long");
				break;
			}
		}
	}
	//Getters & Setters
	public void setWaitForDatabase(boolean b) {
		this.waitForDatabase = b;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public void setWorkouts(Workouts workouts) {
		this.workouts = workouts;
	}
	

}
