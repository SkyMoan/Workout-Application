package all.Controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import all.SuperController;
import all.LayoutHandler;

public class CoachMenuController extends SuperController {


	@FXML
	private void onInbox() throws IOException{

			loadScene(LayoutHandler.inboxPane, this.getRoot(), this.app);

	}

	@FXML
	private void onClients() throws IOException {

			loadScene(LayoutHandler.clientsPane, this.getRoot(), this.app);

	}

	@FXML
	private void onLogOut() throws IOException{
			this.app.reset();
			loadScene(LayoutHandler.loginPane, this.getRoot(), this.app);


		}
	}


