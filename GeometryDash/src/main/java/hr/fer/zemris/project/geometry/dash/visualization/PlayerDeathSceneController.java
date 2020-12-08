package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayerDeathSceneController extends MenuController {
	
	@FXML
	TextField jumpField;
	
	@FXML
	Button retryButton, menuButton;
	
	@FXML
	private void retryAction(ActionEvent event) {
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
		gameEngine.reset();
		
	}
	
	@FXML
	private void mainMenuAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml"));
    	Parent root = loader.load();
    	
    	Stage stage = (Stage)(menuButton.getScene().getWindow());
    	Scene scene = GeometryDash.createScaledScene(root, stage);
    	BackgroundSceneController controller = loader.<BackgroundSceneController>getController();
    	controller.setGameEngine(gameEngine);
    	
    	stage.setScene(scene);
    	
	}
	
	public void showInformation(String jumps) {
		jumpField.setText(jumps);	
	}

}
