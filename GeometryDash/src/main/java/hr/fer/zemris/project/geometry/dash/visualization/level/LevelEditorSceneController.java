package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

/**
 * Controller for level editor.
 * @author Andi Škrgat
 *
 */
public class LevelEditorSceneController {

	@FXML
	private Canvas grid;

	@FXML
	private Button addBlockButton;

	@FXML
	private Button addPlatformButton;

	@FXML
	private Button addSpikeButton;

	@FXML
	private Button addGrassButtton;
	
	private MouseHandler mouseHandler = MouseHandler.getInstance();
	
	public void setListeners() {
		setOnMousePressed();
		setOnMouseReleased();
		setOnMouseMoved();
		setOnMouseDragged();
	}
	
	/**
	 * Sets on mouse pressed listener
	 */
	private void setOnMousePressed() {
		grid.getScene().setOnMousePressed((e) -> {
			System.out.println("Mouse pressed");
			mouseHandler.setMousePressedButton(e.getButton());
		});
	}
	
	/**
	 * Sets on mouse moved listener
	 */
	private void setOnMouseMoved() {
		grid.getScene().setOnMouseMoved((e) -> {
			System.out.println("Mouse moved");
			mouseHandler.setMouse_x(e.getX());
			mouseHandler.setMouse_y(e.getY());
		});
	}
	
	/**
	 * Sets {@linkplain MouseHandler} properties
	 */
	private void setOnMouseDragged()  {
		grid.getScene().setOnMouseDragged((e) -> {
			mouseHandler.setMouseDragged(true);
			System.out.println("Mouse dragged");
			mouseHandler.setDeltaDrag_x(e.getX() - mouseHandler.getMouse_x());
			mouseHandler.setDeltaDrag_y(e.getY() - mouseHandler.getMouse_y());
		});
	}
	
	/**
	 * Sets released action
	 */
	private void setOnMouseReleased() {
		grid.getScene().setOnMouseReleased((e) -> {
			System.out.println("Mouse released");
			mouseHandler.setMousePressedButton(null);
			mouseHandler.setDeltaDrag_x(0);
			mouseHandler.setDeltaDrag_y(0);
		});
	}

	/**
	 * @return the graphicsContext
	 */
	public GraphicsContext getGraphicsContext() {
		return grid.getGraphicsContext2D();
	}
}
