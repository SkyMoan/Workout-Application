package all.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import Statistics.Statistic;
import Statistics.Statistics;
import all.MainController;
import all.LayoutHandler;

public class StatisticsController extends MainController {
	private LinkedHashMap<String, Double> partners;
	private int currentPartner;

	@FXML
	private Image image;
	@FXML
	private JFXButton goButton;
	@FXML
	private JFXButton returnButton;
	@FXML
	private JFXButton nextButton;
	@FXML
	private JFXButton previousButton;
	@FXML
	private JFXButton historyButton;
	@FXML
	private Label avgLabel;
	@FXML
	private Label profileLabel;
	@FXML
	private Label questionLabel;
	@FXML
	private Label timeLabel;
	@FXML
	private Label avgPulseLabel;
	@FXML
	private Label maxPulseLabel;
	@FXML
	private Label kmRanLabel, kmSwamLabel, kmBikedLabel;
	@FXML
	private Label invalidLabel;

	@FXML
	private Label myRun, mySwim, myBike;

	@FXML
	private Label avgCityRun, avgCitySwim, avgCityBike;

	@FXML
	private Label estMaxPulse;

	@FXML
	private Label avgPulseRun, avgPulseSwim, avgPulseBike;

	@FXML
	private Label timeSpentRunning, timeSpentSwimming, timeSpentBiking;

	@FXML
	private Label userName, compName;

	/*
	 * Checks that user have typed a positive duration and chosen a date for new
	 * cardio workout. Then submits the data to database and send user back to main
	 * menu.
	 */
	@FXML
	private void initialize() {

		Platform.runLater(() -> {
			returnButton.setOnAction((event) -> {
				onCancel();
			});
			Statistics statObj = this.app.getStatistics();
			if (app.getUser().getIsCoach()) {
				userName.setText(this.getClient());
				Statistic statistic = app.getStatistics().getStatistics().get(getClient());
				app.setMyStatistics(statistic);
				loadForCoach();
				loadStatistics(statistic, statObj.calculateAverageInCity(app.getUsers(), app.getUser().getCity()), null,
						null);
			} else {
				userName.setText(app.getUser().getUsername());

				goButton.setOnAction((event) -> {
					onFind();
				});

				loadStatistics(app.getMyStatistics(),
						statObj.calculateAverageInCity(app.getUsers(), app.getUser().getCity()), null, null);
			}
		});
	}

	private void loadForCoach() {
		goButton.setText("Send message");
		goButton.setOnAction((event) -> {
			onSendMessage();
		});
		historyButton.setVisible(true);
		historyButton.setOnAction((event) -> {
			onHistory();
		});
		questionLabel.setVisible(false);
		avgLabel.setText("Average data for city");
	}
	
	private String timeSpent(int time) {
		int hours = time/60;
		int minutes = time % 60;
		
		return hours + " Hours " + minutes + " Minutes";
	
	}
	
	

	private void loadStatistics(Statistic profileStatistic, Statistic statistic, Double matchPercent,
			String comparingName) {
		Statistic myStatistic = app.getMyStatistics();
		// Checks if we compare to city or users
		if (comparingName != null) {
			// pluss percent
			matchPercent = matchPercent * 100;
			int percent = (int) Math.round(matchPercent);
			String namePercent = comparingName + " is a " + Integer.toString(percent) + "% match! ";
			compName.setText(namePercent);

			avgPulseLabel.setText(comparingName + "'s average pulse");
			maxPulseLabel.setText(comparingName + "'s estimated max pulse");
			timeLabel.setText(comparingName + "'s time spent on exercises");
		}
		else if (app.getUser().getIsCoach()) {
			compName.setText("Your clients city");
		}
		else {
			compName.setText("Your city (" + app.getUser().getCity() + ")");
		}

		// Calculates average once to save time.

		// Card 1 - Distance ran compared to other users in city for the last 30 days.
		myRun.setText("" + myStatistic.getRunKm() + " Km");
		avgCityRun.setText("" + statistic.getRunKm() + " Km");

		// Card 2 - Distance swam? compared to other users in city for the last 30 days.
		mySwim.setText("" + myStatistic.getSwimKm() + " Km");
		avgCitySwim.setText("" + statistic.getSwimKm() + " Km");

		// Card 3 - Distance biked compared to other users in city for the last 30 days.
		myBike.setText("" + myStatistic.getBikeKm() + " Km");
		avgCityBike.setText("" + statistic.getBikeKm() + " Km");

		// Card 4 - Time spent on each excercise type
		timeSpentRunning.setText("" + profileStatistic.getRunMin() + " Minutes");
		timeSpentSwimming.setText("" + profileStatistic.getSwimMin() + " Minutes");
		timeSpentBiking.setText("" + profileStatistic.getBikeMin() + " Minutes");
		

		// Card 5 - Shows your estimated max pulse.
		estMaxPulse.setText("" + profileStatistic.getMaxPulse());

		// Card 6 - Shows your average pulse separately for biking, swimming and running
		// for the last 30 days.
		avgPulseRun.setText("" + profileStatistic.getAvgRunPulse());
		avgPulseSwim.setText("" + profileStatistic.getAvgSwimPulse());
		avgPulseBike.setText("" + profileStatistic.getAvgBikePulse());

	}

	@FXML
	private void onFind() {
		// Find partners, show their profiles?
		partners = app.findPartners();

		List<String> names = new ArrayList<String>(partners.keySet());
		currentPartner = 1;
		String name = names.get(currentPartner);
		Statistic statistic = app.getStatistics().getStatistics().get(name);
		Double percentMatch = partners.get(name);
		this.loadStatistics(statistic, statistic, percentMatch, name);

		nextButton.setVisible(true);
		questionLabel.setVisible(false);

		avgPulseLabel.setText("Average pulse");
		maxPulseLabel.setText("Estimated max pulse");
		timeLabel.setText("Time spent on exercises");
		
		avgLabel.setText("Average data for "+name);
		//Set imageimage

		goButton.setText("Send message!");
		returnButton.setText("Cancel");
		goButton.setOnAction((event) -> {
			onSubmit();
		});
		returnButton.setOnAction((event) -> {
			onBack();
		});
	}

	//Sends you to the inbox with selected partner
	@FXML
	private void onSubmit() {
		try {
			this.setConversation(new ArrayList<String>(partners.keySet()).get(currentPartner));
			loadScene(LayoutHandler.inboxPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Sends you to the next potential partner
	@FXML
	private void onNext() {
		List<String> names = new ArrayList<String>(partners.keySet());
		currentPartner += 1;
		if (currentPartner == names.size() - 1) {
			// Gjem knapp
			nextButton.setVisible(false);

		}
		if (currentPartner == 2) {
			previousButton.setVisible(true);
		}
		String name = names.get(currentPartner);
		avgLabel.setText("Average data for "+name);
		Statistic statistic = app.getStatistics().getStatistics().get(name);
		Double percentMatch = partners.get(name);
		this.loadStatistics(statistic, statistic, percentMatch, name);
	}

	//Sends you to the previous potential partner
	@FXML
	private void onPrevious() {
		currentPartner -= 1;
		List<String> names = new ArrayList<String>(partners.keySet());
		if (currentPartner == 1) {
			previousButton.setVisible(false);
		}
		if (currentPartner == (names.size() - 1)) {
			nextButton.setVisible(true);
		}
		String name = names.get(currentPartner);
		avgLabel.setText("Average data for "+name);
		Statistic statistic = app.getStatistics().getStatistics().get(name);
		Double percentMatch = partners.get(name);
		this.loadStatistics(statistic, statistic, percentMatch, name);
	}

	//Sends you to inbox for your selected client (only when you are logged in as coach)
	@FXML
	private void onSendMessage() {
		try {
			this.setConversation(this.getClient());
			loadScene(LayoutHandler.inboxPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Sends you to history for your selected client (only when you are logged in as coach)
	@FXML
	void onHistory() {
		try {
			this.setClient(this.getClient());
			loadScene(LayoutHandler.historyPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Sends you to the original statistics
	@FXML
	private void onBack() {
		try {
			loadScene(LayoutHandler.statisticsPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Back to main menu
	@FXML
	private void onCancel() {
		try {
			if (app.getUser().getIsCoach())
				this.loadScene(LayoutHandler.mainCoachPane, getRoot(), this.app);
			else
				this.loadScene(LayoutHandler.mainUserPane, getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
