package hr.fer.zemris.project.geometry.dash.model.drawables.player;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.scene.canvas.GraphicsContext;

/**
 * The main player class, logics and engine behind the "protagonist" of <strong>Geometry Dash</strong>
 * @author Damjan
 */
public class Player extends GameObject{
	    
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
    	if(isTouchingGround == true) {
    		jumpIntent = true;	
    	}
    }

//	@Override
//	public void update(GraphicsContext graphics, Vector2D cameraPosition) {
//		if(jumpIntent == true) {
//			getCurrentPosition().translate(new Vector2D(0, GameConstants.playerJumpingOffset));
//			jumpIntent = false;
//			isTouchingGround = false;
//		}
////		System.out.println("Pozvano nacrtaj novi objekt");
//		//System.out.println(this.getCharacter().getIcon().getHeight());
//		//graphics.clearRect(0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
//		//graphics.drawImage(this.character.getIcon(), newPosition.getX(), newPosition.getY());
//		graphics.drawImage(this.character.getIcon(), getCurrentPosition().getX() - cameraPosition.getX(), getCurrentPosition().getY() - cameraPosition.getY());
////		this.position.translate(new Vector2D(GameConstants.timeBetweenUpdates * 50f, 0));
////		this.position.translate(new Vector2D(GameConstants.timeBetweenUpdates * 10f, GameConstants.timeBetweenUpdates * 50f));
////		this.position.translate(new Vector2D(0, GameConstants.timeBetweenUpdates * 40f));
////		this.position.translate(new Vector2D(GameConstants.timeBetweenUpdates * 40f, 0));
//		calculatePlayerPhysics();
//	}
	
	/**
	 * Calculates player physcics - gravity, speed and position
	 */
	private void calculatePlayerPhysics() {
		getCurrentPosition().translate(new Vector2D(getSpeed().getX() * GameConstants.timeBetweenUpdates,  getSpeed().getY() * GameConstants.timeBetweenUpdates));
		getSpeed().translate(new Vector2D(0,  GameConstants.GRAVITY * GameConstants.timeBetweenUpdates));
		if(getSpeed().getY() >= 100) {
			getSpeed().setY(100);
		}
	}
	
	/**
	 * Sets isTouchingGround to true
	 */
	public void touchesGround() {
		isTouchingGround = true;
	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(this.character.getIcon(), getCurrentPosition().getX(), getCurrentPosition().getY());
		calculatePlayerPhysics();
	}
	
}
