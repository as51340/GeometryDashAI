package hr.fer.zemris.project.geometry.dash.visualization;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class GameSceneController {
	
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
		gameEngine.getGameWorld().getRenderer().setGraphicsContext(canvas.getGraphicsContext2D());
	}
	
	@FXML
	public void initialize() {
		Utils.animateBackground(overlay, background1, background2, background3);
	}
	
}
