
package all.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import all.SuperController;

public class SignUpController extends SuperController {

	@FXML
	private JFXTextField usernameField, nameField, ageField, heightField, weightField, cityField;


	@FXML
	private JFXRadioButton maleRadioButton, coachRadioButton;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXPasswordField passwordConfirmationField;

	@FXML
	private JFXButton cancelButton;

	@FXML
	private Label invalidLabel;

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


	@FXML
	private void onCancel() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}
