package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.music.MusicSettings;
import hr.fer.zemris.project.geometry.dash.visualization.settings.controllers.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;

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
	
	/**
	 * Reference to the music settings
	 */
	private MusicSettings musicSettings = MusicSettings.getInstance();
	
	/**
	 * Initially set music volume to 50 
	 * Register on every change of music
	 */
	public void initialize() {
		music.valueProperty().set(0);
		musicSettings.setVolume(0);
		music.valueProperty().addListener((observable, oldValue, newValue) -> {
			musicSettings.setVolume(newValue.doubleValue() / 100);
		});
	}
	
	@FXML
    private void accountButtonClicked(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/AccountScene.fxml")
    	);
    	loader.load();
    	AccountSceneController controller = loader.getController();
    	
    	controller.setPreviousSceneRoot(this.rootPane);
    	controller.setGameEngine(gameEngine);
    	controller.init();
    }
	
	@FXML
    private void howToPlayButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/HowToPlayScene.fxml")
    	);
    	loader.load();
    	HowToPlaySceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    	controller.setGameEngine(gameEngine);
    }
	
	@FXML
    private void rateButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/RateScene.fxml")
    	);
    	loader.load();
    	RateSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    	controller.setGameEngine(gameEngine);
    }
	
	@FXML
    private void songsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/SongsScene.fxml")
    	);
    	loader.load();
    	SongsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    	controller.setGameEngine(gameEngine);
    }
	
	@FXML
    private void helpButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/HelpScene.fxml")
    	);
    	loader.load();
    	HelpSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    	controller.setGameEngine(gameEngine);
    }
	
	@FXML
    private void optionsButtonClicked(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "settings/OptionsScene.fxml")
    	);
		loader.load();	
		OptionsSceneController controller = loader.<OptionsSceneController>getController();
		controller.setPreviousSceneRoot(rootPane);
		controller.setGameEngine(gameEngine);
    	controller.init();	
    }

}
