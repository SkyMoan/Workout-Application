package all.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import all.LayoutHandler;
import all.SuperController;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import Workouts.TempList;

public class HistoryController extends SuperController {

	@FXML
	private JFXTreeTableView<Workout> tableView;

	@FXML
	private TreeTableColumn<Workout, String> dateColumn;

	@FXML
	private TreeTableColumn<Workout, String> typeColumn;

	@FXML
	private TreeTableColumn<Workout, String> pulseColumn;

	@FXML
	private TreeTableColumn<Workout, String> durationColumn;

	@FXML
	private TreeTableColumn<Workout, String> distanceColumn;

	@FXML
	public void initialize() {
		Platform.runLater(() -> {
			setWorkouts();
		});
	}

	private void setWorkouts() {

		dateColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Workout, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getDate()));
		typeColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Workout, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getType()));
		pulseColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Workout, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getPulse()));
		durationColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Workout, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getDuration()));
		distanceColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Workout, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getDistance()));


		ObservableList<Workout> workouts = FXCollections.observableArrayList();
		if (app.getUser().getIsCoach()) {
			app.getClientsWorkouts(this.getClient());
		}

		loadWorkouts(workouts);


		final TreeItem<Workout> root = new RecursiveTreeItem<Workout>(workouts, RecursiveTreeObject::getChildren);
		tableView.setRoot(root);
		tableView.setShowRoot(false);

	}

	@FXML
	private void onBack() {
		try {
			if(app.getUser().getIsCoach())this.loadScene(LayoutHandler.statisticsPane, getRoot(), this.app);
			else this.loadScene(LayoutHandler.mainUserPane, getRoot(), this.app);		
			} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadWorkouts(ObservableList<Workout> workouts) {
		try {
			ArrayList<TempList> tList = app.getWorkouts().getWorkoutsAsList();
			tList.sort(null);
			for (TempList w : tList) {
				workouts.add(new Workout(w.getDate(), w.getType(), Double.toString(w.getPulse()),
						Double.toString(w.getDuration()), Double.toString(w.getDistance())));
			}
		} catch (Exception e) {

		}
	}

	private static class Workout extends RecursiveTreeObject<Workout> {
		private final String date, duration, pulse, type, distance;


		public Workout(String date, String type, String pulse, String duration, String distance) {
			this.date = date;
			this.duration = duration;
			this.pulse = pulse;
			this.type = type;
			this.distance = distance;
		}

		public String getDate() {
			return this.date;
		}

		public String getDuration() {
			return this.duration;
		}

		public String getPulse() {
			return this.pulse;
		}

		public String getType() {
			return this.type;
		}

		public String getDistance() {
			return this.distance;
		}

	}

}
