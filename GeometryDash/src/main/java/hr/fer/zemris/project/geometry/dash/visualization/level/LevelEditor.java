package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Full featured level editor
 * @author Andi Škrgat
 *
 */
public class LevelEditor {

	private Vector2D lastMousePosition;
	
	private GraphicsContext graphicsContext;
	
	private Camera camera;
	
	private MouseHandler mouseHandler = MouseHandler.getInstance();
	
	public LevelEditor() {
		lastMousePosition = new Vector2D(0, 0);
		camera = new Camera();
	}

	/**
	 * @return the lastMousePosition
	 */
	public Vector2D getLastMousePosition() {
		return lastMousePosition;
	}

	/**
	 * @param lastMousePosition the lastMousePosition to set
	 */
	public void setLastMousePosition(Vector2D lastMousePosition) {
		this.lastMousePosition = lastMousePosition;
	}
	
	public void setGraphicsContext(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
	}
	
	/**
	 * On every tick level editor will be updated but only if user is on level editor
	 */
	public void update() {
		if(mouseHandler.getMousePressedButton() == MouseButton.SECONDARY) {
			double delta_x = mouseHandler.getMouse_x() + mouseHandler.getDeltaDrag_x() - lastMousePosition.getX();
			double delta_y = mouseHandler.getMouse_y() + mouseHandler.getDeltaDrag_y() - lastMousePosition.getY();
			camera.moveCamera(new Vector2D(-delta_x, -delta_y));
		}
		lastMousePosition = new Vector2D(mouseHandler.getMouse_x() + mouseHandler.getDeltaDrag_x(), mouseHandler.getMouse_y() + mouseHandler.getDeltaDrag_y());
		graphicsContext.clearRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);
		drawYLines();
		drawXLines();
	}
	
	/**
	 * Draws vertical lines on grid
	 */
	private void drawYLines() {
		double x_start = Math.floor(camera.getPosition().getX() / GameConstants.cell_width)
				* GameConstants.cell_width - camera.getPosition().getX();
		graphicsContext.setLineWidth(0.5);
		graphicsContext.setStroke(Color.DIMGRAY);
		for (int i = 0; i < GameConstants.linesLevelEditor_Y; i++) {
			graphicsContext.strokeLine(x_start, 0, x_start, GameConstants.floorPosition_Y);
			x_start += GameConstants.cell_width;
			
		}
	}

	/**
	 * Draws horizontal lines on grid
	 */
	private void drawXLines() {
		double y_start = Math.floor(camera.getPosition().getY() / GameConstants.cell_height)
				* GameConstants.cell_height - camera.getPosition().getY();
		graphicsContext.setLineWidth(0.5);
		graphicsContext.setStroke(Color.DIMGRAY);
		for (int i = 0; i < GameConstants.linesLevelEditor_X; i++) {
			graphicsContext.strokeLine(0, y_start, GameConstants.WIDTH, y_start);
			y_start += GameConstants.cell_height;
		}
	}
	
	
}
