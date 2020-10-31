package hr.fer.zemris.project.geometry.dash.model;

import javafx.scene.canvas.GraphicsContext;

/**
 * Specifies that some object can be drawn
 * @author Andi Škrgat
 *
 */
public interface Drawable {

	/**
	 * Method for drawing objects
	 * @param graphicsContext tool for drawing
	 */
	void draw(GraphicsContext graphicsContext);
	
}
