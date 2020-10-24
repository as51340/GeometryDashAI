package hr.fer.zemris.project.geometry.dash.model;

import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;

/**
 * Game camera
 * @author Andi Škrgat
 *
 */
public class Camera {
	
	/**
	 * Current position
	 */
	private Vector2D position;
	
	/**
	 * Initialization constructor
	 */
	public Camera() {
		position = new Vector2D(0, 0);
	}
	
	/**
	 * Constructor that initializes camera's position
	 * @param position
	 */
	public Camera(Vector2D position) {
		this.position = position;
	}
	
	/**
	 * @return current position of camera
	 */
	public Vector2D getPosition() {
		return this.position;
	}
	
	public void moveCamera(Vector2D direction) {
		position.translate(direction);
	}

}
