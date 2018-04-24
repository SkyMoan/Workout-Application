//Handles communications to the workouts in the database

package tdt4140.gr1836.app.db;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tdt4140.gr1836.app.workouts.Workout;
import tdt4140.gr1836.app.workouts.Workouts;

public class WorkoutsDatabase extends CommonDatabase {
	public WorkoutsDatabase(DatabaseReference reference) {
		this.reference = reference;
	}
	public Workouts getWorkouts(String username) {
		WorkoutsDatabase db = this;
		//DatabaseReference ref = FirebaseDatabase.getInstance().getReference("workouts/" + username);
		DatabaseReference ref = this.reference.child("workouts").child(username);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot != null) {
					db.setWorkouts(dataSnapshot.getValue(Workouts.class));
					db.setWaitForDatabase(false);
				} else {
					db.setWorkouts(null);
					db.setWaitForDatabase(false);
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
				// TODO Auto-generated method stub
				db.setWaitForDatabase(false);

			}
		});
		this.waitForDatabase();
		return this.workouts;
	}
	public void submitCardioWorkout(Workout cdw, String username) {
		DatabaseReference s = this.reference.child("workouts").child(username).child(cdw.getType()).child(cdw.getDate());
		s.setValueAsync(cdw);
	}

	public void deleteWorkout(String username, String type, String date) {
		DatabaseReference ref = this.reference.child("workouts").child( username + "/" + type + "/" + date);
		ref.setValueAsync(null);
	}

}
