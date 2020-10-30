package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.image.Image;

public abstract class Obstacle extends GameObject{
	
    private int height;
    private int width;
    private Vector2D position;
    private final int velocityX = 50;
    private Image icon;

    public Obstacle(Vector2D position) {
        this.position = position;
    }
    
    /**
     * Checks if obstacle hitbox is touching player
     * @return true if it is, otherwise false
     */
    public abstract boolean checkCollisions(Player player);

    /**
     * Returns some reference Vector2D for object from which the hitbox will be calculated
     * @return new reference Vector2D
     */
    public Vector2D getPosition() {
        return this.position;
    }

    public abstract boolean contains(Vector2D p);

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

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public void translate(){
        this.position.translate(new Vector2D(-GameConstants.timeBetweenUpdates * velocityX, 0));
    }

}
