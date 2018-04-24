package tdt4140.gr1836.app.ui.controllers.user;

import java.io.IOException;
import java.util.*;

import com.jfoenix.effects.JFXDepthManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import tdt4140.gr1836.app.ui.NavigationHandler;
import tdt4140.gr1836.app.ui.LayoutHandler;
import tdt4140.gr1836.app.workouts.TempList;

public class MainMenuController extends NavigationHandler {

	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private Pane chartPane;

	@FXML
	private Label logoutButton;
	private Map<String,TempList> dataMap=new HashMap<>();

	@FXML
	private void initialize() {
		// load username in logout button
		Platform.runLater(() -> {
			logoutButton.setText("Log out (" + app.getUser().getUsername() + ")");
			if(app.getWorkouts()!=null) setGraph();
		});
	}
	private void setGraph() {

		XYChart.Series<String, Number> series = new XYChart.Series<>();

		//Populate series with data
		int x = 1;
		ArrayList<TempList> workouts = app.getWorkouts().getWorkoutsAsList();
		workouts.sort(Comparator.comparing(TempList::getDate));
		Collections.reverse(workouts);
		for (TempList w : workouts) {

			if (x <= 21) {
				XYChart.Data<String, Number> data = new XYChart.Data<>(w.getDate(), w.getPulse());
				//Add user to reference to use for tooltip
				//date+pulse should be unique enough reference
				dataMap.put(w.getDate()+w.getPulse(),w);
				series.getData().add(data);
				//lag tooptip på hover
			} else {
				break;
			}
			x++;

		}
		barChart.getData().add(series);
		Platform.runLater(()->setMaxBarWidth(40,3));
		//tooltip
		for(XYChart.Series<String,Number> s:barChart.getData()){
			for(XYChart.Data<String,Number> d:s.getData()){
				TempList w=dataMap.get(d.getXValue()+d.getYValue());
				Tooltip tt=new Tooltip("Type: "+w.getType()+"\nDate: "+w.getDate()+"\nAverage pulse: "+w.getPulse()+" bpm\nDuration: "+w.getDuration()+" minutes\nDistance: "+w.getDistance()+"m");
				tt.setStyle("");
				Tooltip.install(d.getNode(),tt);
				//Add class on hover
				d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
				//Removing class on exit
				d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));

			}
		}

		JFXDepthManager.setDepth(chartPane, 1);
	}
	private void setMaxBarWidth(double maxBarWidth, double minCategoryGap){
		double barWidth=0;
		do{
			double catSpace = xAxis.getCategorySpacing();
			double avilableBarSpace = catSpace - (barChart.getCategoryGap());
			barWidth = (avilableBarSpace / barChart.getData().size());
			if (barWidth >maxBarWidth){
				avilableBarSpace=(maxBarWidth);
				barChart.setCategoryGap(catSpace-avilableBarSpace);
			}
		} while(barWidth>maxBarWidth);

		do{
			double catSpace = xAxis.getCategorySpacing();
			double avilableBarSpace = catSpace - (minCategoryGap);
			barWidth = Math.min(maxBarWidth, (avilableBarSpace / barChart.getData().size()));
			avilableBarSpace=(barWidth);
			barChart.setCategoryGap(catSpace-avilableBarSpace);
		} while(barWidth < maxBarWidth && barChart.getCategoryGap()>minCategoryGap);
	}

	@FXML
	private void onInbox(){
		try {
			loadScene(LayoutHandler.inboxPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onNewCardioWorkout() { /* Function for showing the  Workout page */
		try {
			loadScene(LayoutHandler.newWorkoutPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onHistory() { /* Function for showing the workout history */
		try {
			loadScene(LayoutHandler.historyPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onCoaches() { /* Function for showing list of coaches */
		try {
			loadScene(LayoutHandler.coachesPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void OnStatistics() { /* Function for showing statistics */
		try {
			loadScene(LayoutHandler.statisticsPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onLogOut() { /* Redirect to login page */
		try {
			this.app.reset();
			loadScene(LayoutHandler.loginPane, this.getRoot(), this.app);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
