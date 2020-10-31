package hr.fer.zemris.project.geometry.dash.visualization;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.sun.glass.ui.Window;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class GameSceneController {
	
	private static final int BACKGROUND_WIDTH = 800;
	private static final int BACKGROUND_TRANSITION_DURATION = 30000;
	private static final int COLOR_TRANSITION_DURATION = 7000;
	
	@FXML
	private ImageView background1;

	@FXML
	private ImageView background2;

	@FXML
	private ImageView background3;
	
	@FXML
	private Rectangle overlay;

	@FXML
	private Canvas canvas;
	
	@FXML
    private StackPane rootPane;

	/**
	 * Reference to the game engine
	 */
	private GameEngine gameEngine;
	
	private Map<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
	

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		//gameEngine.getGameWorld().getRenderer().setGraphicsContext(canvas.getGraphicsContext2D());
	}
	
	@FXML
	public void initialize() {
		ParallelTransition backgroundCyclingAnimation = new ParallelTransition(
			createBackgroundTransition(background1), 
			createBackgroundTransition(background2), 
			createBackgroundTransition(background3)
		);
		backgroundCyclingAnimation.setCycleCount(Animation.INDEFINITE);
		backgroundCyclingAnimation.play();
		
		SequentialTransition colorCyclingAnimation = new SequentialTransition(
			createColorTransition(overlay, Color.DODGERBLUE, Color.BLUEVIOLET),
			createColorTransition(overlay, Color.BLUEVIOLET, Color.PURPLE),
			createColorTransition(overlay, Color.PURPLE, Color.CRIMSON),
			createColorTransition(overlay, Color.CRIMSON, Color.ORANGE), 
			createColorTransition(overlay, Color.ORANGE, Color.YELLOWGREEN), 
			createColorTransition(overlay, Color.YELLOWGREEN, Color.LIGHTGREEN),
			createColorTransition(overlay, Color.LIGHTGREEN, Color.CYAN),
			createColorTransition(overlay, Color.CYAN, Color.DODGERBLUE)
		);
		colorCyclingAnimation.setCycleCount(Animation.INDEFINITE);
		colorCyclingAnimation.play();
	}
	
	private static TranslateTransition createBackgroundTransition(Node background) {
		TranslateTransition transition = new TranslateTransition(
			Duration.millis(BACKGROUND_TRANSITION_DURATION), background
		);
		transition.setFromX(0);
		transition.setToX(-BACKGROUND_WIDTH);
		transition.setInterpolator(Interpolator.LINEAR);
		return transition;
	}
	
	private static FillTransition createColorTransition(Shape shape, Color fromColor, Color toColor) {
		return new FillTransition(
			Duration.millis(COLOR_TRANSITION_DURATION), 
			shape, 
			fromColor, 
			toColor
		);
	}
	
}
