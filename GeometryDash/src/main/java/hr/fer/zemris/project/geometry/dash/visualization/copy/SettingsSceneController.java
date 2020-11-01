package hr.fer.zemris.project.geometry.dash.visualization.copy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.settings.controllers.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.control.Slider;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class SettingsSceneController extends MenuController{
		
	@FXML
	private AnchorPane showPane;
	
	@FXML
	private Button account;
	
	@FXML
	private Button howToPlay;
	
	@FXML
	private Button rate;
	
	@FXML
	private Button songs;
	
	@FXML
	private Button help;
	
	@FXML
	private Button options;
	
	@FXML
	private Slider music;
	
	@FXML
	private Slider sfx;

	
	private void setVisibility(boolean state) {
		account.setVisible(state);
		howToPlay.setVisible(state);
		rate.setVisible(state);
		songs.setVisible(state);
		help.setVisible(state);
		options.setVisible(state);
		music.setVisible(state);
		sfx.setVisible(state);
	}	
	
	@FXML
    private void accountButtonClicked(MouseEvent event) throws IOException {
   
		FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/AccountScene.fxml")
    	);
    	loader.load();
    	AccountSceneController controller = loader.getController();
    	
    	controller.setPreviousSceneRoot(this.rootPane);
    	
    	
    }
	
	@FXML
    private void howToPlayButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/HowToPlayScene.fxml")
    	);
    	loader.load();
    	HowToPlaySceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
	
	@FXML
    private void rateButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/RateScene.fxml")
    	);
    	loader.load();
    	RateSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
	
	@FXML
    private void songsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/SongsScene.fxml")
    	);
    	loader.load();
    	SongsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
	
	@FXML
    private void helpButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/HelpScene.fxml")
    	);
    	loader.load();
    	HelpSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
	
	@FXML
    private void optionsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/OptionsScene.fxml")
    	);
    	loader.load();
    	OptionsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
	
}
