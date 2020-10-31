package hr.fer.zemris.project.geometry.dash.model;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Game object. Every game object has its current position, width, height, speed and icon
 * @author Andi Škrgat
 *
 */
public abstract class GameObject implements Drawable{
	
	/**
	 * Object's current position
	 */
	private Vector2D currentPosition;
	
	/**
	 * Object's height
	 */
	private int height;
	
	/**
	 * Object's width
	 */
	private int width;
	
	/**
	 * Object's speed
	 */
	private Vector2D speed;
	
	/**
	 * Object's icon
	 */
	private Image icon;
		
	/**
    * Returns some reference {@linkplain Vector2D} for object from which the hitbox will be calculated
    * @return new reference Vector2D
    */
	public Vector2D getCurrentPosition() {
		return this.currentPosition;
	}
	
	/**
	 * Sets current position of the object
	 * @param newPosition new position
	 */
	public void setCurrentPosition(Vector2D newPosition) {
		this.currentPosition = newPosition;
	}

	/**
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set height
	 * @param height height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * set width
	 * @param width width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return speed
	 */
	public Vector2D getSpeed() {
		return speed;
	}

	/**
	 * Sets speed
	 * @param speed
	 */
	public void setSpeed(Vector2D speed) {
		this.speed = speed;
	}

	/**
	 * @return icon
	 */
	public Image getIcon() {
		return icon;
	}

	/**
	 * set icon
	 * @param icon
	 */
	public void setIcon(Image icon) {
		this.icon = icon;
	}
	
}
