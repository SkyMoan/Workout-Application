package all.Controllers;

import java.io.IOException;

import all.LayoutHandler;
import all.SuperController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

public class WorkoutController extends SuperController {

	@FXML
	private JFXTextField durationField, distanceField, pulseField;
	@FXML
	private JFXDatePicker dateField;
	@FXML
	private ToggleGroup exerciseType;
	@FXML
	private JFXRadioButton runningRadioButton, swimmingRadioButton, bikingRadioButton;
	@FXML
	private Label invalidLabel;

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
