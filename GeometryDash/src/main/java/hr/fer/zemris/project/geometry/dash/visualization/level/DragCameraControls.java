package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.Changeable;
import hr.fer.zemris.project.geometry.dash.model.Drawable;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class DragCameraControls implements Drawable, Changeable{

	private Camera camera;
	
	private MouseHandler mouseHandler;
	
	private Vector2D lastMousePosition;
	
	public DragCameraControls(Camera camera, MouseHandler mouseHandler) {
		this.camera = camera;
		this.mouseHandler = mouseHandler;
		lastMousePosition = new Vector2D(0, 0);
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
	}
	
	
	/**
	 * Draws vertical lines on grid
	 */
	private void drawYLines(GraphicsContext graphicsContext) {
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
	private void drawXLines(GraphicsContext graphicsContext) {
		double y_start = Math.floor(camera.getPosition().getY() / GameConstants.cell_height)
				* GameConstants.cell_height - camera.getPosition().getY();
		graphicsContext.setLineWidth(0.5);
		graphicsContext.setStroke(Color.DIMGRAY);
		for (int i = 0; i < GameConstants.linesLevelEditor_X; i++) {
			graphicsContext.strokeLine(0, y_start, GameConstants.WIDTH, y_start);
			y_start += GameConstants.cell_height;
		}
	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		drawYLines(graphicsContext);
		drawXLines(graphicsContext);
	}


}
