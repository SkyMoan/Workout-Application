package all.Controllers;

import java.io.IOException;

import all.LayoutHandler;
import all.MainController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

public class WorkoutController extends MainController {

	@FXML
	private JFXTextField durationField;
	@FXML
	private JFXTextField distanceField;
	@FXML
	private JFXTextField pulseField;
	@FXML
	private JFXDatePicker dateField;
	@FXML
	private ToggleGroup exerciseType;
	@FXML
	private JFXRadioButton runningRadioButton;
	@FXML
	private JFXRadioButton swimmingRadioButton;
	@FXML
	private JFXRadioButton bikingRadioButton;
	@FXML
	private Label invalidLabel;

	/*
	 * Checks that user have typed a positive duration and chosen a date for new
	 * cardio workout. Then submits the data to database and send user back to main
	 * menu. Edit: Also checks if your pulse is less than your max and positive
	 */
	@FXML
	private void onSubmit() throws IOException {
		double duration = -1;
		double distance = -1;
		double pulse = 0;
		try {
			duration = Double.parseDouble(durationField.getText());
			distance = Double.parseDouble(distanceField.getText());
			pulse = Double.parseDouble(pulseField.getText());

		} catch (NumberFormatException e) {
			invalidLabel.setText("Please fill out all required fields");
		}

		if (duration > 0 && dateField.getValue() != null && distance > 0 && pulse > 40
				&& pulse < (220 - app.getUser().getAge())) {
			String type;
			if (runningRadioButton.isSelected()) {
				type = "Running";
			} else if (swimmingRadioButton.isSelected()) {
				type = "Swimming";

			} else {
				type = "Biking";

			}
			this.app.submitCardioWorkout(type, duration, distance, pulse, dateField.getValue().toString());

			loadScene(LayoutHandler.mainUserPane, this.getRoot(), this.app);
		} else {
			if (pulse < 0 || pulse > (220 - app.getUser().getAge())) {
				invalidLabel.setText("Your pulse can't be above your max pulse or negative");
			} else {
				invalidLabel.setText("Check your input");
			}
		}
	}

	@FXML
	public void onCancel() throws IOException {
		loadScene(LayoutHandler.mainUserPane, this.getRoot(), this.app);
	}

}
