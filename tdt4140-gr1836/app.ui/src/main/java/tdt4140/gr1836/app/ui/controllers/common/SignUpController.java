
package tdt4140.gr1836.app.ui.controllers.common;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tdt4140.gr1836.app.ui.NavigationHandler;

public class SignUpController extends NavigationHandler {

	@FXML
	private JFXTextField usernameField;

	@FXML
	private JFXTextField nameField;

	@FXML
	private JFXTextField ageField;

	@FXML
	private JFXTextField heightField;

	@FXML
	private JFXTextField weightField;

	@FXML
	private JFXTextField cityField;

	@FXML
	private JFXRadioButton maleRadioButton;

	@FXML
	private JFXRadioButton coachRadioButton;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXPasswordField passwordConfirmationField;

	@FXML
	private JFXButton cancelButton;

	@FXML
	private Label invalidLabel;

	/*
	 * This function is called when user presses submit button. Check is username
	 * field and both password fields have text in them and if password is equal in
	 * the two password fields. If required fields are valid user is created and
	 * window is closed. If form is not valid a labels text will tell the user to
	 * fill out required fields.
	 * Edit: Checks if username is allready taken
	 */
	@FXML
	private void onSubmit() {

		int age = -1;
		int height = -1;
		int weight = -1;

		boolean validForm = true;

		try {
			age = Integer.parseInt(ageField.getText());
			height = Integer.parseInt(heightField.getText());
			weight = Integer.parseInt(weightField.getText());
		} catch (NumberFormatException e) {
			validForm = false;
		}
		boolean nameTaken = app.getAllUsers().containsKey(usernameField.getText());
		
		if (usernameField.getText().equals("") || passwordField.getText().equals("")
				|| !passwordField.getText().equals(passwordConfirmationField.getText()) || age < 0 || height < 0
				|| weight < 0 || nameTaken) {
			validForm = false;
		}

		if (validForm) {
			this.app.register(usernameField.getText(), nameField.getText(), age, height, weight, cityField.getText(),
					maleRadioButton.isSelected(), coachRadioButton.isSelected(), passwordField.getText());

			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();

		} else {
			if (nameTaken) {
				invalidLabel.setText("Username already taken");
			}
			else {
				invalidLabel.setText("Please fill out all required fields");
				}
		}
	}

	/*
	 * Closes window when user press cancel button.
	 */
	@FXML
	private void onCancel() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}
