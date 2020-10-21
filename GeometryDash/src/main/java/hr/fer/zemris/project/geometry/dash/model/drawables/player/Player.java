package hr.fer.zemris.project.geometry.dash.model.drawables.player;

import hr.fer.zemris.project.geometry.dash.model.drawables.Point;

/**
 * The main player class, logics and engine behind the "protagonist" of <strong>Geometry Dash</strong>
 * @author Damjan
 */
public class Player {

    //dimenzije lika su zadane u model.settings GameConstants (default 30x03px)

    /**
     * Where the player is positioned, (stationary) x and (movable) y coordinate
     */
    private Point position;

    /**
     * Returns the coordinates of the upper left corner of the player's hurtbox
     * @return <code>Point</code> object containing the player's position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Makes the player character "jump" - adds upward force
     */
    public void jump() {

    }
}
