package all.Controllers;

import java.io.IOException;
import java.util.Map;

import all.LayoutHandler;
import all.MainController;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import Users.User;
import Users.UserTempList;

public class AvailableCoachesController extends MainController {
	/*
	 * @FXML private Button homeBtn;
	 */
	private Map<String, User> allCoaches;
	@FXML
	private Label coachLabel;

	@FXML
	private JFXTreeTableView<UserTempList> tableView;

	@FXML
	private TreeTableColumn<UserTempList, String> usernameColumn;

	@FXML
	private TreeTableColumn<UserTempList, String> nameColumn;

	@FXML
	private TreeTableColumn<UserTempList, String> cityColumn;

	@FXML
	private TreeTableColumn<UserTempList, String> ageColumn;

	@FXML
	public void initialize() {
		Platform.runLater(() -> {
			setCoaches();
		});
	}

	@FXML
	private void onBack() {
		try {
			loadScene(LayoutHandler.mainUserPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * private ArrayList<User> parseCoaches() { return this.app.getCoachesAsList();
	 * }
	 */

	private void setCoaches() {
		String myCoach = this.app.getUser().getMyCoach();
		if (!myCoach.equals("")) {
			coachLabel.setText("Your current coach is " + myCoach);
		}
		usernameColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<UserTempList, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getUsername()));
		nameColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<UserTempList, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getName()));
		cityColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<UserTempList, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getCity()));
		ageColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<UserTempList, String> param) -> new ReadOnlyStringWrapper(
						param.getValue().getValue().getAge()));

		// data
		ObservableList<UserTempList> coaches = FXCollections.observableArrayList();

		loadCoaches(coaches);
		// Burde sortere coaches etter username her

		// build tree
		final TreeItem<UserTempList> root = new RecursiveTreeItem<UserTempList>(coaches,
				RecursiveTreeObject::getChildren);
		tableView.setRoot(root);
		tableView.setShowRoot(false);

	}

	private void loadCoaches(ObservableList<UserTempList> coaches) {
		try {
			allCoaches = app.getCoaches();

			// userList.sort(null);
			for (String s : allCoaches.keySet()) {
				coaches.add(new UserTempList(allCoaches.get(s).getUsername(), allCoaches.get(s).getName(),
						allCoaches.get(s).getCity(), Integer.toString(allCoaches.get(s).getAge())));
			}
		} catch (Exception e) {
			// Label: "No coaches found"
		}
	}

	@FXML
	private void updateCoach() {
		// Method which when you click on a coach this coach will be set to your user
		TreeItem<UserTempList> selectedRow = tableView.getSelectionModel().getSelectedItem();
		if (selectedRow != null) {
			String coachUsername = selectedRow.getValue().getUsername();
			User updatedCoach = allCoaches.get(coachUsername);
			if (updatedCoach != null) { // user exists in database
				app.setMyCoach(coachUsername);
				coachLabel.setText("Your new coach is " + selectedRow.getValue().getName());
			} else {
				coachLabel.setText("Please select a coach.");
			}
		}
		else {
			coachLabel.setText("Please select a coach.");
		}
	}
}
