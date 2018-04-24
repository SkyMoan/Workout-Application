//Handles communications to the statistics in the database
package tdt4140.gr1836.app.db;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tdt4140.gr1836.app.statistics.Statistic;
import tdt4140.gr1836.app.statistics.Statistics;

public class StatisticsDatabase extends CommonDatabase {
	
	private Statistics statistics; 
	
	public StatisticsDatabase (DatabaseReference reference) {
		this.reference = reference;
	}
	public Statistics getStatistics() {
		StatisticsDatabase db = this;
		DatabaseReference ref = this.reference;
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (dataSnapshot != null) {
					db.setStatistics(dataSnapshot.getValue(Statistics.class));
					db.setWaitForDatabase(false);
				} else {
					db.setStatistics(null);
					db.setWaitForDatabase(false);
				}
			}
			@Override
			public void onCancelled(DatabaseError arg0) {
				db.setWaitForDatabase(false);
			}
		});
		this.waitForDatabase();
		return this.statistics;
	}
	public void updateStatistics(Statistic statistic, String username) {
		DatabaseReference s = this.reference.child("statistics").child(username);
		s.setValueAsync(statistic);
	}
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

}
