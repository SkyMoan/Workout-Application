package tdt4140.gr1836.app.ui.controllers.common;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tdt4140.gr1836.app.ui.NavigationHandler;
import tdt4140.gr1836.app.users.User;

public class LoginController extends NavigationHandler {

	@FXML
	private JFXTextField usernameField;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXButton loginButton;

	@FXML
	private JFXButton signUpButton;

	@FXML
	private Label invalidLabel;

	/*
	 * This function is called when user presses login button
	 */
	@FXML
	private void onLogin() {
		if (!usernameField.getText().equals("")) { /* Checks if username is something */
			User user = this.app.login(usernameField.getText(), passwordField.getText());
			if (user == null) {
				invalidLabel.setText("Invalid login");
			} else {
				Stage stage = (Stage) loginButton.getScene().getWindow();
				stage.close();

				if (user.getIsCoach()) {
					try {
						// user is coach
						showMainStage(this.app, true);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					try {
						// user is not coach
						showMainStage(this.app, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			invalidLabel.setText("Invalid login");
		}
	}

	@FXML
	private void onSignUp() {
		try {
			showRegisterStage(this.app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
