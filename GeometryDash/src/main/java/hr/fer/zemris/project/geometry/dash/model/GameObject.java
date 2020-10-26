package hr.fer.zemris.project.geometry.dash.model;

import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject{
	
	/**
	 * Draws image for some game object
	 */
	public abstract void update(GraphicsContext graphics, Vector2D cameraPosition);
		
}
