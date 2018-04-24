//Helper class for presenting all workouts for a user
package tdt4140.gr1836.app.workouts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Workouts implements Serializable {
	private Map<String, Workout> Running;
	private Map<String, Workout> Biking;
	private Map<String, Workout> Swimming;

	public Workouts() {

	}


	//Returns all workouts in an arralist of TempList
	public ArrayList<TempList> getWorkoutsAsList() {
		ArrayList<TempList> temp = new ArrayList<>();

		if (Running != null) {
			for (String s : Running.keySet()) {
				TempList tmplist = new TempList(s, "Running", Running.get(s).getPulse(), Running.get(s).getDuration(),
						Running.get(s).getDistance());
				temp.add(tmplist);
			}
		}
		if (Swimming != null) {
			for (String s : Swimming.keySet()) {
				TempList tmplist = new TempList(s, "Swimming", Swimming.get(s).getPulse(),
						Swimming.get(s).getDuration(), Swimming.get(s).getDistance());
				temp.add(tmplist);
			}
		}
		if (Biking != null) {
			for (String s : Biking.keySet()) {
				TempList tmplist = new TempList(s, "Biking", Biking.get(s).getPulse(), Biking.get(s).getDuration(),
						Biking.get(s).getDistance());
				temp.add(tmplist);
			}
		}
		return temp;
	}

	public void setWorkouts(Map<String, Workout> Running, Map<String, Workout> Swimming, Map<String, Workout> Biking) {
		this.Running = Running;
		this.Biking = Biking;
		this.Swimming = Swimming;
	}

	public void addWorkout(Workout w) {
		if (w.getType().equals("Running")) {
			this.addRunningWorkout(w);
		}
		else if (w.getType().equals("Biking")) {
			this.addBikingWorkout(w);
		}
		else if (w.getType().equals("Swimming")) {
			this.addSwimmingWorkout(w);
		}
	}
	public void addRunningWorkout(Workout cw) {
		if (this.Running==null) {
			this.Running = new HashMap<String, Workout>();
		}
		this.Running.put(cw.getDate(), cw);
	}

	public void addBikingWorkout(Workout cw) {
		if (this.Biking==null) {
			this.Biking = new HashMap<String, Workout>();
		}
		this.Biking.put(cw.getDate(), cw);
	}

	public void addSwimmingWorkout(Workout cw) {
		if (this.Swimming==null) {
			this.Swimming = new HashMap<String, Workout>();
		}
		this.Swimming.put(cw.getDate(), cw);
	}

	public Map<String, Workout> getBiking() {
		return this.Biking;
	}

	public Map<String, Workout> getRunning() {
		return this.Running;
	}

	public Map<String, Workout> getSwimming() {
		return this.Swimming;
	}
}
