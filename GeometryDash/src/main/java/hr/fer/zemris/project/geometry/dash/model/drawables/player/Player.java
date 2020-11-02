package hr.fer.zemris.project.geometry.dash.model.drawables.player;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.scene.canvas.GraphicsContext;

/**
 * The main player class, logics and engine behind the "protagonist" of <strong>Geometry Dash</strong>
 *
 * @author Damjan, Andi
 */
public class Player extends GameObject {

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
     * Is touching ground
     */
    private boolean isTouchingGround = false;

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
     *
     * @param position
     */
    public Player(Vector2D position, Vector2D speed) {
        setCurrentPosition(position);
        this.character = CharactersSelector.selectedCharacter;
        setSpeed(speed);
    }

    /**
     * Makes the player character "jump" - adds upward force
     */
    public void jump() {
        if (isTouchingGround) {
            jumpIntent = true;
        }
    }

    /**
     * Calculates player physcics - gravity, speed and position
     */
    private void calculatePlayerPhysics() {
        if (isTouchingGround) {

        } else {
            getCurrentPosition().translate(new Vector2D(getSpeed().getX() * GameConstants.timeBetweenUpdates, getSpeed().getY() * GameConstants.timeBetweenUpdates));
        }
//        getSpeed().translate(new Vector2D(GameConstants.acceleration_X * GameConstants.timeBetweenUpdates, GameConstants.gravity_Y * GameConstants.timeBetweenUpdates));
//        if (getSpeed().getY() >= GameConstants.playerFinalSpeed_Y) {
//            getSpeed().setY(GameConstants.playerFinalSpeed_Y);
//        }
    }

    /**
     * Sets isTouchingGround to true
     * TODO change that listener changes this propery
     */
    public void touchesGround() {
        isTouchingGround = true;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        if (jumpIntent) {
            getCurrentPosition().translate(new Vector2D(0, GameConstants.playerJumpingOffset)); //change so listeners are listening for that
            jumpIntent = false;
            isTouchingGround = false;
        }
        graphicsContext.drawImage(this.character.getIcon(), getCurrentPosition().getX(), getCurrentPosition().getY());
        calculatePlayerPhysics();
    }

}
