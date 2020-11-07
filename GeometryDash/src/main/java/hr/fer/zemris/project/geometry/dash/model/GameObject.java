package hr.fer.zemris.project.geometry.dash.model;

import com.google.gson.annotations.Expose;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Game object. Every game object has its current position, width, height, speed and icon
 * @author Andi Skrgat
 *
 */
public abstract class GameObject implements Drawable{
	
	/**
	 * Object's current position
	 */
	@Expose
	private Vector2D currentPosition;
	
	/**
	 * Object's height
	 */
	@Expose
	private int height;
	
	/**
	 * Object's width
	 */
	@Expose
	private int width;
	
	/**
	 * Object's icon
	 */
	private Image icon;
	
	/**
	 * Path to icon
	 */
	@Expose
	private String iconPath;
		
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
	
	
	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}
	
	

	/**
	 * @param iconPath the iconPath to set
	 */
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	/**
	 * Method for copying object
	 * @return
	 */
	public abstract GameObject copy();
	
}
