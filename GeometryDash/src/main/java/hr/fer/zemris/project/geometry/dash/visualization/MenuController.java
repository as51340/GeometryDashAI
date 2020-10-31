package hr.fer.zemris.project.geometry.dash.visualization;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MenuController {
	
	private static final int MENU_TRANSITION_DURATION = 500;
	private static final double OVERLAY_OPACITY = 0.5;
	
	private Pane previousSceneRootPane;
	
    @FXML
    protected Rectangle overlay;

    @FXML
    protected ImageView backButton;
    
    @FXML
    protected StackPane rootPane;
	
	public void setPreviousSceneRoot(Pane previousSceneRootPane) {
		this.rootPane.setVisible(true);
		this.previousSceneRootPane = previousSceneRootPane;
		previousSceneRootPane.getChildren().add(rootPane);
    	rootPane.translateYProperty().set(-GameConstants.HEIGHT);
        KeyValue keyValue = new KeyValue(rootPane.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(MENU_TRANSITION_DURATION), keyValue);
        Timeline slideAnimation = new Timeline(keyFrame);
        slideAnimation.play();
        
        FadeTransition fadeTransition = new FadeTransition(
        	Duration.millis(MENU_TRANSITION_DURATION), overlay
        );
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(OVERLAY_OPACITY);
        fadeTransition.play();
	}
	
	
	
    @FXML
    protected void backButtonClicked(MouseEvent event) {
   
    	
    	rootPane.translateYProperty().set(0);
        KeyValue keyValueReverse = new KeyValue(
        	rootPane.translateYProperty(), -GameConstants.HEIGHT, Interpolator.EASE_IN
        );
        KeyFrame keyFrameReverse = new KeyFrame(
        	Duration.millis(MENU_TRANSITION_DURATION), keyValueReverse
        );
        Timeline slideAnimationReverse = new Timeline(keyFrameReverse);
        slideAnimationReverse.setOnFinished(evt -> {
	    	previousSceneRootPane.getChildren().remove(rootPane);
	    });
        slideAnimationReverse.play();
        
        FadeTransition fadeTransitionReverse = new FadeTransition(
        	Duration.millis(MENU_TRANSITION_DURATION), overlay
        );
        fadeTransitionReverse.setFromValue(OVERLAY_OPACITY);
        fadeTransitionReverse.setToValue(0.0);
        fadeTransitionReverse.play();
    }

}
