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
import all.SuperController;
import Users.UserTempList;

public class ClientsController extends SuperController {

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


	private void setClients() {

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


		ObservableList<UserTempList> clients = FXCollections.observableArrayList();

		loadClients(clients);



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
				clientLabel.setText("Select a client");

			}
		} else {
			clientLabel.setText("Select a client");
		}
	}
}
