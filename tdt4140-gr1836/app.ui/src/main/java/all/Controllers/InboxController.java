package all.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Inbox.Message;
import all.SuperController;
import all.LayoutHandler;
import Users.User;
import Users.UserTempList;

@SuppressWarnings("restriction")
public class InboxController extends SuperController {

	@FXML
	private JFXTextField messageField, searchField;

	@FXML
	private VBox messageFrame, people, assigned, latestConversations, infoPanel;

	@FXML
	private ScrollPane scrollpane;

	@FXML
	private Label coachorclientLabel;

	@FXML
	private String currentChat = "default";

	@FXML
	private Image profile;

	@FXML
	private boolean first = false;

	@FXML
	private Map<String, UserTempList> nodeMap = new HashMap<>();

	public void initialize() {

		Platform.runLater(() -> {
			// Sets label for client or coach
			if (app.getUser().getIsCoach()) {
				coachorclientLabel.setText("Your clients");
			}
			generatePeople();

			// For auto scroll on new message
			scrollpane.vvalueProperty().bind(messageFrame.heightProperty());

			initSearchListener();
		});
	}

	@FXML
	private void send() { 
		app.sendMessage(messageField.getText(), currentChat);
		addMessage(new Message(messageField.getText(), currentChat, app.getUser().getUsername()));
		messageField.setText("");
	}

	@FXML
	private void goBack() throws IOException { // go to main menu
		if (app.getUser().getIsCoach())
			this.loadScene(LayoutHandler.mainCoachPane, getRoot(), this.app);
		else
			this.loadScene(LayoutHandler.mainUserPane, getRoot(), this.app);
	}

	private void initSearchListener() {
		searchField.textProperty().addListener(this::changed);
	}

	private void search(String newValue) {

		for (Node u : assigned.getChildren()) {
			if (nodeMap.get(u.getId()).getName().toLowerCase().contains(newValue.toLowerCase())) {
				u.setManaged(true);
				u.setVisible(true);
			} else {
				u.setManaged(false);
				u.setVisible(false);
			}
		}
		for (Node n : latestConversations.getChildren()) {
			if (nodeMap.get(n.getId()).getName().toLowerCase().contains(newValue.toLowerCase())) {
				n.setManaged(true);
				n.setVisible(true);
			} else {
				n.setManaged(false);
				n.setVisible(false);
				System.out.println(nodeMap.get(n.getId()).getName().toLowerCase() + " / " + newValue.toLowerCase());
			}
		}
		for (Node m : people.getChildren()) {
			if (nodeMap.get(m.getId()).getName().toLowerCase().contains(newValue.toLowerCase())) {
				m.setManaged(true);
				m.setVisible(true);
			} else {
				m.setManaged(false);
				m.setVisible(false);
			}
		}

	}

	private void generatePeople() {
		ArrayList<UserTempList> otherUsers = parsePeople();
		ArrayList<UserTempList> recentConv = getConvInList(app.getConversations());
		ArrayList<UserTempList> myCoachOrClients = app.getUser().getIsCoach() ? app.getClients() : getCoachInList();
		// remove latestConv people from other users
		for (UserTempList k : recentConv) {
			for (UserTempList u : otherUsers) {
				if (k.getUsername().equals(u.getUsername())) {
					otherUsers.remove(u);
					break;
				}
			}
		}

		// account image
		profile = new Image(SuperController.class.getResourceAsStream("images/ic_account_circle_white_24dp_2x.png"));

		// add myCoach
		for (UserTempList u : myCoachOrClients) {
			addDude(u, assigned);
		}
		// add recent conversations
		for (UserTempList u : recentConv) {
			addDude(u, latestConversations);
		}
		// Add all users
		for (UserTempList u : otherUsers) {
			addDude(u, people);
		}
	}

	private ArrayList<UserTempList> getConvInList(ArrayList<User> conversations) {
		ArrayList<UserTempList> temp = new ArrayList<>();
		if (conversations == null)
			return new ArrayList<>();
		for (User u : conversations) {
			temp.add(new UserTempList(u.getUsername(), u.getName(), u.getCity(), u.getAge() + ""));
		}
		return temp;
	}

	private ArrayList<UserTempList> getCoachInList() {
		User temp = app.getMyCoach();
		if (temp != null)
			return new ArrayList<>(Arrays.asList(temp.toTempUser()));
		else
			return new ArrayList<>();
	}

	private void addDude(UserTempList u, VBox where) {
		ImageView imageView = new ImageView(profile);
		HBox hbox = new HBox();
		Label label = new Label();

		imageView.setFitHeight(31);
		imageView.setPreserveRatio(true);

		label.textProperty().setValue(u.getName());
		label.setStyle("-fx-padding: 15 10 15 10;");

		hbox.setStyle("-fx-pref-height: 40; -fx-pref-width: 219;");
		hbox.setPadding(new Insets(0, 0, 0, 30));
		hbox.getStyleClass().add("btn");
		hbox.setMaxHeight(40);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setScaleShape(false);

		hbox.getChildren().add(imageView);
		hbox.getChildren().add(label);

		where.getChildren().add(hbox);

		// map to find nodes when searching
		hbox.setId(randomString(5));
		nodeMap.put(hbox.getId(), u);

		// event listener
		hbox.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
			loadChat(u);
		});
		// load first coach as default, but not if convPartner is set!
		if (!first && getConvPartner() == null) {
			first = true;
			loadChat(u);
		}
		// loads chat if convPartner is set
		else if (getConvPartner() == u.getUsername()) {
			loadChat(u);
		}

	}

	private String randomString(final int length) {
		Random r = new Random(); // perhaps make it a class variable so you don't make a new one every time
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char c = (char) (r.nextInt((int) (Character.MAX_VALUE)));
			sb.append(c);
		}
		return sb.toString();
	}

	private void loadChat(UserTempList u) {

		// Check if not clicked on same thing
		if (!currentChat.equals(u.getUsername())) {
			currentChat = u.getUsername();

			// remove children
			messageFrame.getChildren().clear();
			infoPanel.getChildren().clear();

			// Set infoPanel
			ImageView imv = new ImageView(profile);
			Label label = new Label(u.getName());
			infoPanel.getChildren().add(imv);
			infoPanel.getChildren().add(label);

			// Get chatlog from database
			ArrayList<Message> messages = app.getMessages(u.getUsername());

			// change styling of messageframe to fit messages
			messageFrame.setAlignment(Pos.BOTTOM_CENTER);
			messageFrame.setSpacing(20);

			// Then add hboxes with chatboxes n imageframes nais

			if (messages != null) {
				for (Message m : messages) {
					addMessage(m);
				}
			}

		}
	}

	private void addMessage(Message m) {
		HBox hbox = new HBox();
		ImageView imv = new ImageView(profile);
		Label label = new Label();

		// only difference is alignment and where children are placed
		if (app.getUser().getUsername().equals(m.getFrom())) {
			hbox.getChildren().add(label);
			hbox.getChildren().add(imv);
			hbox.setAlignment(Pos.TOP_RIGHT);

		} else {
			hbox.getChildren().add(imv);
			hbox.getChildren().add(label);
			hbox.setAlignment(Pos.TOP_LEFT);
		}
		hbox.setSpacing(10);
		hbox.setFillHeight(true);
		hbox.setScaleShape(false);

		imv.setFitHeight(31);
		imv.setPreserveRatio(true);

		label.setWrapText(true);
		label.setMaxWidth(250);
		label.setScaleShape(false);

		label.setText(m.getMessage());
		// label.setPrefHeight(Math.ceil(width/250)*height+10);

		label.getStyleClass().add("message");

		messageFrame.getChildren().add(hbox);
	}

	private ArrayList<UserTempList> parsePeople() {
		return app.getUsersAsList();
	}

	private void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if (!oldValue.equals(newValue)) {
			search(newValue);
		}
	}
}
