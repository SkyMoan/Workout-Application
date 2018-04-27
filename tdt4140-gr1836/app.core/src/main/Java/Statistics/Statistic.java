//Helper class for handling the statistics for users

package Statistics;

import all.statistics.Statistics;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Statistic implements Serializable {
	//Saves stats for the last 30 days, each time you add a new value, your stats should be updated.
	//powerLevel
	private double maxPulse;
	private int runKm, bikeKm, swimKm, runMin, bikeMin, swimMin, totalRuns, totalBikes, totalSwims, avgRunPulse, avgBikePulse, avgSwimPulse;
	
	// Empty constructor to make new object based on snapshot data from login
	public Statistic() {
		
	}
	public Statistic(double maxPulse, int runKm, int bikeKm, int swimKm, int runMin, int bikeMin, int swimMin, int totalRuns, int totalBikes, int totalSwims, int avgRunPulse, int avgBikePulse, int avgSwimPulse) {
		this.maxPulse = maxPulse;
		this.runKm = runKm;
		this.bikeKm = bikeKm;
		this.swimKm = swimKm;
		this.runMin = runMin;
		this.bikeMin = bikeMin;
		this.swimMin = swimMin;
		this.totalRuns = totalRuns;
		this.totalBikes = totalBikes;
		this.totalSwims = totalSwims;
		this.avgRunPulse = avgRunPulse;
		this.avgBikePulse = avgBikePulse;
		this.avgSwimPulse = avgSwimPulse;
		
	}
	//Getters & setters
	public double getMaxPulse() {
		return maxPulse;
	}
	public void setMaxPulse(double maxPulse) {
		this.maxPulse = maxPulse;
	}
	public int getSwimMin() {
		return swimMin;
	}
	public void setSwimMin(int swimMin) {
		this.swimMin = swimMin;
	}
	public int getTotalRuns() {
		return totalRuns;
	}
	public void setTotalRuns(int totalRuns) {
		this.totalRuns = totalRuns;
	}
	public int getTotalBikes() {
		return totalBikes;
	}
	public void setTotalBikes(int totalBikes) {
		this.totalBikes = totalBikes;
	}
	public int getTotalSwims() {
		return totalSwims;
	}
	public void setTotalSwims(int totalSwims) {
		this.totalSwims = totalSwims;
	}
	public int getAvgRunPulse() {
		return avgRunPulse;
	}
	public void setAvgRunPulse(int avgRunPulse) {
		this.avgRunPulse = avgRunPulse;
	}
	public int getAvgBikePulse() {
		return avgBikePulse;
	}
	public void setAvgBikePulse(int avgBikePulse) {
		this.avgBikePulse = avgBikePulse;
	}
	public int getAvgSwimPulse() {
		return avgSwimPulse;
	}
	public void setAvgSwimPulse(int avgSwimPulse) {
		this.avgSwimPulse = avgSwimPulse;
	}
	public int getRunKm() {
		return this.runKm;
	}
	public void setRunKm(int r) {
		this.runKm = r;
	}
	public int getBikeKm() {
		return this.bikeKm;
	}
	public void setBikeKm(int b) {
		this.bikeKm = b;
	}
	public int getSwimKm() {
		return this.swimKm;
	}
	public void setSwimKm(int s) {
		this.swimKm = s;
	}
	public int getRunMin() {
		return runMin;
	}
	public void setRunMin(int runMin) {
		this.runMin = runMin;
	}
	public int getBikeMin() {
		return bikeMin;
	}
	public void setBikeMin(int bikeMin) {
		this.bikeMin = bikeMin;
	}

}
