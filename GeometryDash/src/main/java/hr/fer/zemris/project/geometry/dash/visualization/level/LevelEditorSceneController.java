package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Controller for level editor.
 * 
 * @author Andi Skrgat
 * Rijesiti da kamera ne moze ic di su kontrole
 * Da se ne mogu staviti dva objekta na istu poziciju
 */
public class LevelEditorSceneController {

	@FXML
    private Canvas grid;
	
	@FXML
    private GridPane gridPane;

    @FXML
    private ImageView addSpike;

    @FXML
    private ImageView addBlock;

    @FXML
    private ImageView addPlatform;

    @FXML
    private ImageView addGrass;

    @FXML
    private ImageView rotateLeft;

    @FXML
    private ImageView rotateRight;

    @FXML
    private ImageView addTunel;

    @FXML
    private ImageView addFire;

    @FXML
    private ImageView add;

    
    private GameEngine gameEngine;
	
	
    @FXML
    public void initialize() {
    	
    }
    
    
	public void setListeners() {
		setOnMousePressed();
		setOnMouseReleased();
		setOnMouseMoved();
		setOnMouseDragged();
		setActionsForButtons();
	}
	
	private void setActionsForButtons() {
		addBlock.setOnMouseClicked((e) -> {
			
		});
	}

	/**
	 * Sets on mouse pressed listener
	 */
	private void setOnMousePressed() {
		grid.getScene().setOnMousePressed((e) -> {
			gameEngine.getLevelEditor().getMouseHandler().setMousePressedButton(e.getButton());
		});
	}

	/**
	 * Sets on mouse moved listener
	 */
	private void setOnMouseMoved() {
		grid.getScene().setOnMouseMoved((e) -> {
			gameEngine.getLevelEditor().getMouseHandler().setMouse_x(e.getX());
			gameEngine.getLevelEditor().getMouseHandler().setMouse_y(e.getY());
		});
	}

	/**
	 * Sets {@linkplain MouseHandler} properties
	 */
	private void setOnMouseDragged() {
		grid.getScene().setOnMouseDragged((e) -> {
			gameEngine.getLevelEditor().getMouseHandler().setMouseDragged(true);
			gameEngine.getLevelEditor().getMouseHandler().setDeltaDrag_x(e.getX() - gameEngine.getLevelEditor().getMouseHandler().getMouse_x());
			gameEngine.getLevelEditor().getMouseHandler().setDeltaDrag_y(e.getY() - gameEngine.getLevelEditor().getMouseHandler().getMouse_y());
		});
	}

	/**
	 * Sets released action
	 */
	private void setOnMouseReleased() {
		grid.getScene().setOnMouseReleased((e) -> {
			gameEngine.getLevelEditor().getMouseHandler().setMousePressedButton(null);
			gameEngine.getLevelEditor().getMouseHandler().setDeltaDrag_x(0);
			gameEngine.getLevelEditor().getMouseHandler().setDeltaDrag_y(0);
		});
	}

	/**
	 * @return the graphicsContext
	 */
	public GraphicsContext getGraphicsContext() {
		return grid.getGraphicsContext2D();
	}
	
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
}
