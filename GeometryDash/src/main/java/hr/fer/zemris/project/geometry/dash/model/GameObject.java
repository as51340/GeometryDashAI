package hr.fer.zemris.project.geometry.dash.model;

import com.google.gson.annotations.Expose;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.ValueAxis;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Game object. Every game object has its current position, width, height, speed and icon
 *
 * @author Andi Skrgat
 */
public abstract class GameObject implements Drawable {

    /**
     * Object's current position
     */
    @Expose
    private Vector2D currentPosition;
    
    protected Vector2D initialPosition;


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

    @Expose
    private String name;

    /**
     * Returns some reference {@linkplain Vector2D} for object from which the hitbox will be calculated
     *
     * @return new reference Vector2D
     */
    public Vector2D getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Returns Vector2D of center position
     *
     * @return Vector2D of center position
     */
    public Vector2D getCenterPosition() {
        return this.getCurrentPosition().translated(new Vector2D(this.getWidth()/2.0, this.getHeight()/2.0));
    }

    /**
     * Sets current position of the object
     *
     * @param newPosition new position
     */
    public void setCurrentPosition(Vector2D newPosition) {
        this.currentPosition = newPosition;
    }
    
    public void setInitialPosition(Vector2D init) {
    	this.initialPosition = init;
		
	}

    /**
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set height
     *
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
     *
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
     *
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for copying object
     *
     * @return
     */
    public abstract GameObject copy();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currentPosition == null) ? 0 : currentPosition.hashCode());
        result = prime * result + height;
        result = prime * result + ((iconPath == null) ? 0 : iconPath.hashCode());
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof GameObject))
            return false;
        GameObject other = (GameObject) obj;
        if (currentPosition == null) {
            if (other.currentPosition != null)
                return false;
        } else if (!currentPosition.equals(other.currentPosition))
            return false;
        if (height != other.height)
            return false;
        if (iconPath == null) {
            if (other.iconPath != null)
                return false;
        } else if (!iconPath.equals(other.iconPath))
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GameObject [currentPosition=" + currentPosition.toString() + ", height=" + height + ", width=" + width + ", icon="
                + icon + ", iconPath=" + iconPath + ", name=" + name + "]";
    }


}
