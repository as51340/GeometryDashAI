package hr.fer.zemris.project.geometry.dash.model.drawables.player;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.Vector;

/**
 * The main player class, logics and engine behind the "protagonist" of <strong>Geometry Dash</strong>
 * @author Damjan
 */
public class Player extends GameObject{
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
    private Vector2D position;
    
    /**
     * Rotation angle in degrees
     */
    private double rotation = 45;

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
    public Player(Vector2D position) {
        this.position = position;
        this.character = CharactersSelector.selectedCharacter;
    }

    /**
     * Returns the coordinates of the upper left corner of the player's hurtbox
     * @return <code>Point</code> object containing the player's position
     */
    public Vector2D getPosition() {
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

	@Override
	public void update(GraphicsContext graphics, Vector2D cameraPosition) {
//		System.out.println("Pozvano nacrtaj novi objekt");
		//System.out.println(this.getCharacter().getIcon().getHeight());
		graphics.clearRect(0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
		//graphics.drawImage(this.character.getIcon(), newPosition.getX(), newPosition.getY());
		graphics.drawImage(this.character.getIcon(), position.getX() - cameraPosition.getX(), position.getY() - cameraPosition.getY());
		this.position.translate(new Vector2D(GameConstants.timeBetweenUpdates * 50f, 0));
	}
	
	public void rotate() {
	}
}
