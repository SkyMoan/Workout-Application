//Handles communications to the inbox in the database

package tdt4140.gr1836.app.db;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tdt4140.gr1836.app.inbox.Message;
import tdt4140.gr1836.app.inbox.Messages;
import tdt4140.gr1836.app.users.User;

public class InboxDatabase extends CommonDatabase {
	private Map<String, Messages> messages;
	private ArrayList<User> conversations;
	private Map<String, User> allUsers;
	
	public InboxDatabase (DatabaseReference reference) {
		this.reference = reference;
	}
	public void sendMessage(String message, String referant, String username) {
		// Need double reference cause I didn't find a better system ://
		Message m = new Message(message, referant, username);
		DatabaseReference ref = FirebaseDatabase.getInstance()
				.getReference("inbox/" + username + "/" + referant + "/messages/" + m.getDate());
		ref.setValueAsync(m);

		ref = FirebaseDatabase.getInstance()
				.getReference("inbox/" + referant + "/" + username + "/messages/" + m.getDate());
		ref.setValueAsync(m);
	}
	
	public Map<String, Messages> loadMessages(String referant, String username) {
		InboxDatabase db = this;
		DatabaseReference ref = this.reference.child("inbox").child(username).child(referant);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot != null) {
					Messages m = dataSnapshot.getValue(Messages.class);
					db.setMessages(referant, m);

					db.setWaitForDatabase(false);

				} else {
					db.setWaitForDatabase(false);
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
				db.setWaitForDatabase(false);

			}
		});
		this.waitForDatabase();
		return this.messages;
	}
	
	public ArrayList<User> getConversations(String username, Map<String, User> allUsers) {
		this.allUsers = allUsers;
		this.conversations = new ArrayList<User>();
		
		InboxDatabase db = this;
		DatabaseReference ref = this.reference.child("inbox/" + username);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot != null) {
					for(DataSnapshot d : dataSnapshot.getChildren()){
						db.setConversationItem(d.getKey());
					}
					db.setWaitForDatabase(false);

				} else {
					db.setWaitForDatabase(false);
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
				db.setWaitForDatabase(false);

			}
		});
		this.waitForDatabase();
		return this.conversations;
	}
	public void setConversationItem(String user){
		User temp = allUsers.get(user);
		if (temp!=null) {
			this.conversations.add(temp);
		}
	}
	public void setMessages(String referant, Messages m) {
		if (messages == null) {
			messages = new HashMap<>();
		}
		this.messages.put(referant, m);
	}
}
