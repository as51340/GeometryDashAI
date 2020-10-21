package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Point;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.image.Image;

public abstract class Obstacle {
    private int height;
    private int width;
    private int positionX;
    private int positionY;
    private int velocityX;
    private Image icon;

    public Obstacle(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Moves obstacles to the left at a steady pace
     * Should be called at each update
     */
    public void calculateNewPosition(){
        this.positionX= (int) (positionX - velocityX * GameConstants.timeBetweenUpdates);
    }

    /**
     * Draws the position of the obstacle
     */
    public abstract void drawTheirPosition();

    /**
     * Checks if obstacle hitbox is touching player
     * @return true if it is, otherwise false
     */
    public abstract boolean checkCollisions();

    /**
     * Returns some reference point for object from which the hitbox will be calculated
     * @return new reference point
     */
    public Point getPosition() {
        return new Point(positionX,positionY);
    }

    public abstract boolean contains(Point p);

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

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}
