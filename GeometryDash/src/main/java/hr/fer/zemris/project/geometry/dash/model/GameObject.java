package hr.fer.zemris.project.geometry.dash.model;

import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject extends Node{
	
	/**
	 * Remembers old position
	 */
	private Vector2D oldPosition;

	/**
	 * Draws image for some game object
	 */
	public abstract void update(GraphicsContext graphics, Vector2D cameraPosition);
	
	/**
	 * @return old object's position
	 */
	public Vector2D getOldPosition() {
		return this.oldPosition;
	}
	
	public void setOldPosition(Vector2D oldPosition) {
		this.oldPosition = oldPosition;
	}
	
	
	
	
	
}
