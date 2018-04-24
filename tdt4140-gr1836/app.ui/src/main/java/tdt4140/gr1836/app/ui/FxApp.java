package tdt4140.gr1836.app.ui;

import java.io.IOException;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tdt4140.gr1836.app.core.App;

@SuppressWarnings("restriction")
public class FxApp extends Application {

	private App app;

	@Override
	public void init() throws IOException {
		app = new App();
		app.getUsersFromDatabase();
	}
	//Main functionality for running our applicaaiton
	@Override
	public void start(Stage primaryStage) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(FxApp.class.getResource(LayoutHandler.loginPane));

		Parent root = loader.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Triex");
		
		//Set icon for application
		//primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/dogecon.png")));
		
		primaryStage.setResizable(false);
	

		// Set app to controller
		NavigationHandler controller = loader.getController();
		controller.setApp(app);

		primaryStage.show();

	}

	public static void main(String[] args) {
		LauncherImpl.launchApplication(FxApp.class, AppPreloader.class, args);
	}

}
