package all.Controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import all.MainController;
import all.LayoutHandler;

public class CoachMenuController extends MainController {

	@FXML
	private Label logoutButton;

	@FXML
	private void initialize() {
		// load username in logout button
		Platform.runLater(() -> {
			logoutButton.setText("Log out (" + app.getUser().getUsername() + ")");
		});
	}

	@FXML
	private void onInbox() {
		try {
			loadScene(LayoutHandler.inboxPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onClients() {
		try {
			loadScene(LayoutHandler.clientsPane, this.getRoot(), this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onLogOut() {
		try {
			this.app.reset();
			loadScene(LayoutHandler.loginPane, this.getRoot(), this.app);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
