package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.image.Image;

public abstract class Obstacle extends GameObject {

    /**
     * Checks if obstacle hitbox is touching player
     *
     * @return true if it is, otherwise false
     */
    public abstract boolean checkCollisions(Player player);

    public abstract boolean playerIsOn(Player player);

}
