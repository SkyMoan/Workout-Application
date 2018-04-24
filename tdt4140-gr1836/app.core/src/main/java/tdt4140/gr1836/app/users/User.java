//Helper class for handling user objects
package tdt4140.gr1836.app.users;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class User implements Serializable {

	private String username, name, city, myCoach, password, salt;
	private int age, height, weight;
	private boolean isMale, isCoach;

	public User(String username, String name, int age, int height, int weight, String city, boolean isMale,
			boolean isCoach, String password) {
		this.username = username;
		this.name = name;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.city = city;
		this.isMale = isMale;
		this.isCoach = isCoach;
		this.password = password;
		this.myCoach = "";

	}

	public User() {
		// empty constructor to make new object based on snapshot data from login
		// function
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setMyCoach(String coach) {
		this.myCoach = coach;
	}

	// Getters
	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return this.name;
	}

	public String getCity() {
		return this.city;
	}

	public String getSalt() {
		return this.salt;
	}/*
		 * public String getCoachname () { return this.coachName; }
		 */

	public int getHeight() {
		return this.height;
	}

	public int getWeight() {
		return this.weight;
	}

	public boolean getIsMale() {
		return isMale;
	}

	public int getAge() {
		return this.age;
	}

	public boolean getIsCoach() {
		return this.isCoach;
	}

	public String getMyCoach() {
		return this.myCoach;
	}

	public UserTempList toTempUser() {
		return new UserTempList(getUsername(),getName(),getCity(),""+getAge());
	}
}
