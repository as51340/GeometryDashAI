package hr.fer.zemris.project.geometry.dash.visualization;

import java.util.HashMap;
import java.util.Map;

import com.sun.glass.ui.Window;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class GameSceneController {

	@FXML
	private Canvas canvas;
	
	@FXML
    private AnchorPane anchorPane;

	/**
	 * Reference to the game engine
	 */
	private GameEngine gameEngine;
	
	private Map<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
	

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		gameEngine.getGameWorld().setGraphics(canvas.getGraphicsContext2D());
	}
	
	
}
