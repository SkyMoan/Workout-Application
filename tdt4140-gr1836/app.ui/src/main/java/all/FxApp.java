package all;

import java.io.IOException;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Core.App;

@SuppressWarnings("restriction")
public class FxApp extends Application {

	private App app;

	@Override
	public void init() throws IOException {
		app = new App();
		app.getUsersFromDatabase();
	}
	//This is where the main application starts
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(FxApp.class.getResource(LayoutHandler.loginPane));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MemeEx");
		primaryStage.setResizable(false);

		// Set app to controller
		SuperController controller = loader.getController();
		controller.setApp(app);

		primaryStage.show();

	}

	public static void main(String[] args) {
		LauncherImpl.launchApplication(FxApp.class, AppPreloader.class, args);
	}

}
