//Handles communications to the users in the database

package Database;

import java.security.NoSuchAlgorithmException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import Users.User;
import Users.Users;


public class UsersDatabase extends SuperDatabase {

	public UsersDatabase(DatabaseReference reference) {
		this.reference = reference;
	}
	
	public User login(String username, String password) {
		UsersDatabase db = this;
		
		DatabaseReference ref = this.reference.child("users").child(username);
				//FirebaseDatabase.getInstance().getReference("users/" + username);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			public void onDataChange(DataSnapshot dataSnapshot) {
				User user = dataSnapshot.getValue(User.class);
				if (user != null) {
					String hashedPassword = Hash.hash(password, Hash.decodeSalt(user.getSalt()));
					if (!hashedPassword.equals(user.getPassword())) {
											
						db.setUser(null);
						db.setWaitForDatabase(false);
					} else {
						db.setUser(user);
						db.setWaitForDatabase(false);
					}
				} else {
					// user does not exist. login failed.
					db.setUser(null);
					db.setWaitForDatabase(false);
				}
			}

			public void onCancelled(DatabaseError arg0) {
				db.setWaitForDatabase(false);

			}
		});
		// Wait loop while waiting for login, should not last more than 30 seconds 
		this.waitForDatabase();
		return this.user;
	}
	public User register(String username, String name, int age, int height, int weight, String city, boolean isMale,
			boolean isCoach, String password) {
		// hash password
		byte[] salt = null;
		try {
			salt = Hash.getSalt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String hashedPassword = Hash.hash(password, salt);

		User user = new User(username, name, age, height, weight, city, isMale, isCoach, hashedPassword);

		user.setSalt(Hash.convertSalt(salt));

		// Send to database
		DatabaseReference s = this.reference.child("users").child(username);
		s.setValueAsync(user);
		return user;
	}
	public void deleteUser(String username) {
		// return null på /username for å fjerne data
		//DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
		this.reference.child("users").child(username).setValueAsync(null);
	}
	public Users getUsers() {
		UsersDatabase db = this;
		
		//DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
		this.reference.addListenerForSingleValueEvent(new ValueEventListener() {

			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot != null) {
					db.setUsers(dataSnapshot.getValue(Users.class));
					db.setWaitForDatabase(false);
				} else {
					db.setUsers(null);
					db.setWaitForDatabase(false);
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
				db.setWaitForDatabase(false);

			}
		});
		// Wait loop while waiting for login, should not last more than 30 seconds
		// before giving error
		this.waitForDatabase();
		return this.users;
	}
	public void setMyCoach(String coach, String username) {
		DatabaseReference s = this.reference.child("users").child(username).child("myCoach");
		s.setValueAsync(coach);
	}

}
