package hr.fer.zemris.project.geometry.dash.model;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;

/**
 * Camera object, stores position, used for rendering objects on the scene and for dragging on level editor
 * @author Andi Skrgat
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
		this.position = new Vector2D(0, 0);
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
	
	/**
	 * Sets new position
	 * @param position new position of camera
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Moves camera in specified direction
	 * @param direction
	 */
	public void moveCamera(Vector2D direction) {
		position.translate(direction);
	}
}
