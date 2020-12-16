package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayerDeathSceneController extends MenuController {
	
	@FXML
	Label totalJumps, attempt, time;
	
	@FXML
	Text levelName;
	
	@FXML
	ProgressBar progressBar;
	
	@FXML
	Button retryButton, chooseLevelButton, menuButton;
	
	@FXML
	private void retryAction(ActionEvent event) {
		GameEngine.getInstance().reset();
		GameEngine.getInstance().getUserListener().userStartedPlaying();
    	rootPane.translateYProperty().set(0);
    	
        KeyValue keyValueReverse = new KeyValue(
        	rootPane.translateYProperty(), -GameConstants.HEIGHT, Interpolator.EASE_IN
        );
        KeyFrame keyFrameReverse = new KeyFrame(
        	Duration.millis(MENU_TRANSITION_DURATION), keyValueReverse
        );
        Timeline slideAnimationReverse = new Timeline(keyFrameReverse);
        slideAnimationReverse.setOnFinished(evt -> {
	    	((StackPane)retryButton.getScene().getRoot()).getChildren().remove(rootPane);
	    });
        slideAnimationReverse.play();
        
        FadeTransition fadeTransitionReverse = new FadeTransition(
        	Duration.millis(MENU_TRANSITION_DURATION), overlay
        );
        fadeTransitionReverse.setFromValue(OVERLAY_OPACITY);
        fadeTransitionReverse.setToValue(0.0);
        fadeTransitionReverse.play();
		//((Stage)retryButton.getScene().getWindow()).close();
        
        // otherwise up and space keys won't work after clicking on retry button
        rootPane.getScene().getRoot().requestFocus();
//        rootPane.getScene().setOnKeyPressed((e) -> {
//    		if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W || e.getCode() == KeyCode.SPACE) {
//        		GameEngine.getInstance().getGameWorld().getPlayerListener().playerJumped();
//        		GameEngine.getInstance().getUserListener().playerJumped();
//    		}
//    	});
//
//        rootPane.getScene().setOnMouseClicked((e) -> {
//    		if(e.getButton() == MouseButton.PRIMARY) {
//    			GameEngine.getInstance().getGameWorld().getPlayerListener().playerJumped();
//    			GameEngine.getInstance().getUserListener().playerJumped();
//    		}
//    	});
		GameEngine.getInstance().start();
	}
	
	@FXML
	private void mainMenuAction(ActionEvent event) throws IOException {
		GameEngine.getInstance().getGameStateListener().normalModePlayingExited();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml"));
    	Parent root = loader.load();
    	
    	Stage stage = (Stage)(menuButton.getScene().getWindow());
    	Scene scene = GeometryDash.createScaledScene(root, stage);
    	
//    	 otherwise window will reset its size to default; this will keep current window width and height
    	double width = menuButton.getScene().getWidth();
    	double height = menuButton.getScene().getHeight();
    	stage.setWidth(width);
    	stage.setHeight(height);
    	
		BackgroundSceneController controller = loader.<BackgroundSceneController>getController();
        controller.init();
    	
    	stage.setScene(scene);
	}
	
	@FXML
	private void chooseLevelAction(ActionEvent event) throws IOException {
		GameEngine.getInstance().getGameStateListener().normalModePlayingExited();
		
		// needed so that back button in ChooseLevelScene works after coming back from level 
		FXMLLoader backgroundSceneLoader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml"));
    	Parent root = backgroundSceneLoader.load();
		BackgroundSceneController backgroundSceneController = backgroundSceneLoader.<BackgroundSceneController>getController();
		backgroundSceneController.init();
		
		FXMLLoader chooseLevelSceneloader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "level/ChooseLevelScene.fxml"));
		chooseLevelSceneloader.load();
    	ChooseLevelController chooseLevelSceneController = chooseLevelSceneloader.getController();
    	chooseLevelSceneController.setPreviousSceneRoot(backgroundSceneController.getRootPane());
		
		Stage stage = (Stage)(chooseLevelButton.getScene().getWindow());
		Scene scene = GeometryDash.createScaledScene(root, stage);
		
//    	 otherwise window will reset its size to default; this will keep current window width and height
    	double width = chooseLevelButton.getScene().getWidth();
    	double height = chooseLevelButton.getScene().getHeight();
    	stage.setWidth(width);
    	stage.setHeight(height);
		
		stage.setScene(scene);
	}
	
	public void showInformation(String levelName, String attempt, short percentage, String totalJumps, double time) {
		int minutes = ((int)time) / 60_000;
		int seconds = (((int)time) % 60_000)/1000;
		this.levelName.setText(levelName);
		this.attempt.setText("Attempt " + attempt);
		this.progressBar.setProgress(0.5);
		this.totalJumps.setText(totalJumps);	
		this.time.setText(String.format("%02d:%02d", minutes, seconds));
	}

}
