//Helper class for a workout
package tdt4140.gr1836.app.workouts;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Workout implements Serializable{
	private double duration, distance, pulse;
	private String date, type;

	Workout(){
		
	}

	public Workout(String type, double duration, double distance, double pulse, String date) {
		this.type=type;
		this.duration=duration;
		this.distance=distance;
		this.pulse=pulse;
		this.date=date;

	}
	public double getDuration() {
		return this.duration;
	}
	public String getDate() {
		return this.date;
	}
	public String getType() {
		return this.type;
	}
	public double getDistance() {
		return this.distance;
	}
	public double getPulse() {
		return this.pulse;
	}
}
