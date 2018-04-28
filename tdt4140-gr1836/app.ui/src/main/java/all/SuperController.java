package all;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Core.App;

public class SuperController {

	protected App app;
	protected Stage root;
	protected static String convPartner;
	protected static String client;

	// Shows a specified scene
	public void loadScene(String path, Stage stage, App app) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SuperController.class.getResource(path));
		Pane parent = (Pane) loader.load();
		// Set controller
		SuperController controller = loader.getController();
		controller.setApp(app);
		controller.setRoot(stage);

		stage.setScene(new Scene(parent));
	}

	// Loads and shows main stage for app
	public void showMainStage(App app, boolean coach) throws IOException {
		Stage root = new Stage();
		FXMLLoader loader;
		if (coach) {
			loader = new FXMLLoader(SuperController.class.getResource(LayoutHandler.mainCoachPane));
			root.setTitle("Coach Menu");
		}
		else {
			loader = new FXMLLoader(SuperController.class.getResource(LayoutHandler.mainUserPane));
			root.setTitle("Triex");
		}
		
		Pane parent = (Pane) loader.load();

		// Set controller
		SuperController controller = loader.getController();
		controller.setApp(app);
		controller.setRoot(root);

		root.setScene(new Scene(parent));
		root.initModality(Modality.APPLICATION_MODAL);
		root.setResizable(false);
		root.show();

		// Load graph and get Statistics from db
		app.getWorkoutsFromDB();
		app.getStatisticsFromDB();
	}

	// Loads and shows the add new user stage
	public void showRegisterStage(App app) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SuperController.class.getResource(LayoutHandler.signUpPane));
		Pane pane = loader.load();

		Stage stage = new Stage();
		stage.setTitle("Register new user");

		// Set this app to controller
		SuperController controller = loader.getController();
		controller.setApp(app);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.show();
	}

	public void setApp(App app) {
		this.app = app;
	}

	public void setRoot(Stage root) {
		this.root = root;
	}

	public Stage getRoot() {
		return this.root;
	}

	public void setConversation(String person) {
		convPartner = person;
	}

	public String getConvPartner() {
		return convPartner;
	}

	public void setClient(String person) {
		client = person;
	}

	public String getClient() {
		return client;
	}

}
