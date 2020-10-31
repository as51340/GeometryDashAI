package hr.fer.zemris.project.geometry.dash.model;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * 
 * @author Andi Škrgat
 *
 */
public abstract class GameObject implements Drawable{
	
	/**
	 * Object's current position
	 */
	private Vector2D currentPosition;
	
	private int height;
	
	private int width;
	
	private Vector2D speed;
	
	private Image icon;
		
	/**
    * Returns some reference {@linkplain Vector2D} for object from which the hitbox will be calculated
    * @return new reference Vector2D
    */
	public Vector2D getCurrentPosition() {
		return this.currentPosition;
	}
	
	public void setCurrentPosition(Vector2D newPosition) {
		this.currentPosition = newPosition;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Vector2D getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2D speed) {
		this.speed = speed;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}
	
}
