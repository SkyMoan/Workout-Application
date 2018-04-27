package all.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import all.LayoutHandler;
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
import all.MainController;
import Users.UserTempList;

public class ClientsController extends MainController {
	/*
	 * @FXML private Button homeBtn;
	 */
	private ArrayList<UserTempList> myClients = new ArrayList<UserTempList>();
	private ArrayList<String> myClientsNames = new ArrayList<String>();

	@FXML
	private Label clientLabel;

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
			setClients();
		});
	}

	@FXML
	private void onBack() {
		try {
			loadScene(LayoutHandler.mainCoachPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * private ArrayList<User> parseCoaches() { return this.app.getCoachesAsList();
	 * }
	 */

	private void setClients() {
		// fill stuff
		/*
		 * name.setCellValueFactory(new PropertyValueFactory<UserTempList,
		 * String>("name")); city.setCellValueFactory(new
		 * PropertyValueFactory<UserTempList, String>("city"));
		 * age.setCellValueFactory(new PropertyValueFactory<UserTempList,
		 * String>("age")); email.setCellValueFactory(new
		 * PropertyValueFactory<UserTempList, String>("email"));
		 * 
		 * view.getItems().setAll(parseCoaches());
		 */
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
		ObservableList<UserTempList> clients = FXCollections.observableArrayList();

		loadClients(clients);
		// Burde sortere coaches etter username her

		// build tree
		final TreeItem<UserTempList> root = new RecursiveTreeItem<UserTempList>(clients,
				RecursiveTreeObject::getChildren);
		tableView.setRoot(root);
		tableView.setShowRoot(false);

	}

	private void loadClients(ObservableList<UserTempList> clients) {
		myClients = app.getClients();
		for (UserTempList s : myClients) {
			clients.add(s);
			myClientsNames.add(s.getUsername());
		}
	}

	@FXML
	private void viewClient() {
		// Method which when you click on a coach this coach will be set to your user
		TreeItem<UserTempList> selectedRow = tableView.getSelectionModel().getSelectedItem();
		if (selectedRow != null) {
			String client = selectedRow.getValue().getUsername();
			if (myClientsNames.contains(client)) {
				setClient(client);
				try {
					// History checks if user is coach and presents clients data
					loadScene(LayoutHandler.statisticsPane, this.getRoot(), this.app);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				clientLabel.setText("Please select a client");

			}
		} else {
			clientLabel.setText("Please select a client");
		}
	}
}
