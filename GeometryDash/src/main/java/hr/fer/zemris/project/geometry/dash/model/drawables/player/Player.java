package hr.fer.zemris.project.geometry.dash.model.drawables.player;

import hr.fer.zemris.project.geometry.dash.model.drawables.Point;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;

import java.util.Vector;

/**
 * The main player class, logics and engine behind the "protagonist" of <strong>Geometry Dash</strong>
 * @author Damjan
 */
public class Player {
    //dimenzije lika su zadane u model.settings GameConstants (default 30x03px)

    /**
     * Constant used in position calculation - pushes player down
     */
    private final double gravity = 0.3;  //placeholder

    /**
     * The sum of (vertical) forces on the player -> jump - gravity
     */
    private double force = -gravity;

    /**
     * Where the player is positioned, (stationary) x and (movable) y coordinate
     */
    private Point position;

    /**
     * Flag that signifies the player wants to jump
     * Used to prevent multiple jumps in one tick
     */
    private boolean jumpIntent = false;
    
    /**
     * Character
     */
    private CharacterObject character;
    
    /**
	 * @return the character
	 */
	public CharacterObject getCharacter() {
		return character;
	}

	/**
	 * @param character the character to set
	 */
	public void setCharacter(CharacterObject character) {
		this.character = character;
	}

	/**
     * Constructs a <code>Player</code>
     * @param position
     */
    public Player(Point position) {
        this.position = position;
    }

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
        jumpIntent = true;
    }

    public boolean touchingGround() {

        return false;
    }

    public void tick(){
        if (jumpIntent && touchingGround()) {

        }
    }
}
