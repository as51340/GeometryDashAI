package hr.fer.zemris.project.geometry.dash.visualization.settings.controllers;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.Session;
import hr.fer.zemris.project.geometry.dash.model.listeners.UserListener;
import hr.fer.zemris.project.geometry.dash.model.settings.Account;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AccountSceneController extends MenuController {

	@FXML 
	AnchorPane notLoggedInPane;
	
	@FXML
	AnchorPane loggedInPane;
	
	@FXML
	AnchorPane registerPane;
	
	@FXML
	TextField firstName;
	
	@FXML
	TextField lastName;
	
	@FXML
	TextField username;
	
	@FXML
	TextField password;
	
	@FXML
	Label welcomeLabel;
	
	@FXML
	AnchorPane logInPane;
	
	@FXML
	TextField usernameLogIn;
	
	@FXML
	TextField passwordLogIn;
		
	@FXML
	private void registerButtonClicked(MouseEvent event) {
		gameEngine.getUserListener().register(firstName.getText(), lastName.getText(), username.getText(), password.getText());
		//store in file or DB
		
		registerPane.setVisible(false);
		loggedInPane.setVisible(true);
		welcomeLabel.setText("Welcome " + username.getText());
	}
	
	@FXML
	private void logInButtonClicked(MouseEvent event) { //zasad samo hardkodirano. Možemo povezat s bazom
		gameEngine.getUserListener().login(usernameLogIn.getText(), passwordLogIn.getText());
		//retrieve from DB or file
		//if everything is okay than create session
		//else dont
		logInPane.setVisible(false);
		loggedInPane.setVisible(true);
//		welcomeLabel.setText("Welcome " + session.getAccount().getUsername());
	}
	
	@FXML
	private void registerMeButtonClicked(MouseEvent event) {
		notLoggedInPane.setVisible(false);
		registerPane.setVisible(true);
	}
	
	@FXML
	private void logInMeButtonClicked(MouseEvent event) {
		notLoggedInPane.setVisible(false);
		logInPane.setVisible(true);
	}
	
	public void init() {
		if(gameEngine.getSession() == null) {
			loggedInPane.setVisible(false);
			registerPane.setVisible(false);
			logInPane.setVisible(false);
		} else {
			notLoggedInPane.setVisible(false);
			registerPane.setVisible(false);
			logInPane.setVisible(false);
			welcomeLabel.setText("Welcome " + gameEngine.getSession().getAccount().getUsername());
		}
	}

}
