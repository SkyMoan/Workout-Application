//Helper class for presetning a simple version of workout

package tdt4140.gr1836.app.workouts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class TempList extends RecursiveTreeObject<TempList> implements Comparable<TempList> {
	private String date;
	private double duration, pulse, distance;
	private String type;

	public TempList(String date, String type, double pulse, double duration, Double distance) {
		this.date = date;
		this.type = type;
		this.pulse = pulse;
		this.duration = duration;
		this.distance = distance;
	}

	public String getDate() {
		return this.date;
	}

	public double getDuration() {
		return this.duration;
	}

	public double getPulse() {
		return this.pulse;
	}

	public String getType() {
		return this.type;
	}

	public Double getDistance() {
		return this.distance;
	}

	@Override
	public int compareTo(TempList tl) {
		DateFormat f = new SimpleDateFormat("yyyy-mm-dd");
		try {
			return f.parse(this.getDate()).compareTo(f.parse(tl.getDate()));
		} catch (Exception e) {
			return 0;
		}
	}
}
