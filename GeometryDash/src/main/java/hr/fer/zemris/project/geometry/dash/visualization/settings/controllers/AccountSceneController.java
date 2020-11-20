package hr.fer.zemris.project.geometry.dash.visualization.settings.controllers;


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
	
	Account account;
	
	@FXML
	private void registerButtonClicked(MouseEvent event) {
		account.setFirstName(firstName.getText());
		account.setLastName(lastName.getText());
		account.setUsername(username.getText());
		account.setPassword(password.getText());
		
		registerPane.setVisible(false);
		loggedInPane.setVisible(true);
		welcomeLabel.setText("Welcome " + account.getUsername());
	}
	
	@FXML
	private void registerMeButtonClicked(MouseEvent event) {
		notLoggedInPane.setVisible(false);
		registerPane.setVisible(true);
	}
	
	public void init() {
		account = gameEngine.getSettings().getAccount();
		if(account.getUsername() == null) {
			loggedInPane.setVisible(false);
			registerPane.setVisible(false);
		} else {
			notLoggedInPane.setVisible(false);
			registerPane.setVisible(false);
			welcomeLabel.setText("Welcome " + account.getUsername());
		}
	}

}
