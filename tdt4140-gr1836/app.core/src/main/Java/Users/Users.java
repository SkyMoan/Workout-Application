//Helper class for handling several users and fetching from database
package Users;

import java.util.Map;


public class Users {
	private Map<String,User> users;
	
	Users(){
		
	}
	public Map<String,User> getUsers(){
		return this.users;
	}
}
