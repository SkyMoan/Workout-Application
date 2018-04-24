//Helper class for handling several users and fetching from database
package tdt4140.gr1836.app.users;

import java.util.Map;


public class Users {
	private Map<String,User> users;
	
	Users(){
		
	}
	public Map<String,User> getUsers(){
		return this.users;
	}
}
