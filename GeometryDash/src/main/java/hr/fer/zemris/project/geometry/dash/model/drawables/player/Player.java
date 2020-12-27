package hr.fer.zemris.project.geometry.dash.model.drawables.player;

import com.google.gson.annotations.Expose;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

/**
 * The main player class, logics and engine behind the "protagonist" of <strong>Geometry Dash</strong>
 *
 * @author Damjan, Andi
 */
public class Player extends GameObject {
	
	private static int lastId = 1;
	
	/**
	 * Unique id of the player
	 */
	private int id;
	
    private double gravityTimer = GameConstants.gravity_Y;
    
    /**
     * Result of goodness function
     */
    private double goodness_value = 0;

    /**
     * Rotation angle in degrees
     */
    @Expose
    private double rotation;

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
     * Remembers player's state
     */
    private boolean isDead = true;

    /**
     * Object's speed
     */
    @Expose
    private Vector2D speed;

    
    /**
     * Specifies type of player
     */
    private PlayingMode playingMode;
   
    /**
     * Player's constructor
     */
    public Player(double rotation, Vector2D speed, String icon, PlayingMode playingMode) {
    	this.id = lastId++;
    	isDead = false;
    	setInitialPosition(new Vector2D(0, 0));
        this.rotation = rotation;
        this.speed = speed;
        setIcon(icon);
        this.playingMode = playingMode;
    }

    /**
     * @return the rotation
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * @return the jumpIntent
     */
    public boolean isJumpIntent() {
        return jumpIntent;
    }

    /**
     * @param jumpIntent the jumpIntent to set
     */
    public void setJumpIntent(boolean jumpIntent) {
        this.jumpIntent = jumpIntent;
    }

    /**
     * @return the isTouchingGround
     */
    public boolean isTouchingGround() {
        return isTouchingGround;
    }

    /**
     * @param isTouchingGround the isTouchingGround to set
     */
    public void setTouchingGround(boolean isTouchingGround) {
        this.isTouchingGround = isTouchingGround;
        if (isTouchingGround) {
            this.gravityTimer = GameConstants.gravity_Y;
        }
    }

    /**
     * @return the speed
     */
    public Vector2D getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(Vector2D speed) {
        this.speed = speed;
    }

    /**
	 * @return the playingMode
	 */
	public PlayingMode getPlayingMode() {
		return playingMode;
	}

	/**
	 * @param playingMode the playingMode to set
	 */
	public void setPlayingMode(PlayingMode playingMode) {
		this.playingMode = playingMode;
	}
	
	/**
	 * @return the goodness_value
	 */
	public double getGoodness_value() {
		return goodness_value;
	}

	/**
	 * @param goodness_value the goodness_value to set
	 */
	public void setGoodness_value(double goodness_value) {
		this.goodness_value = goodness_value;
	}

	/**
     * Constructs a <code>Player</code>
     *
     * @param position
     */
    public Player(Vector2D position, Vector2D speed, PlayingMode playingMode) {
    	this.id = lastId++;
    	isDead = false;
    	setInitialPosition(position.copy());
        setCurrentPosition(position);
        this.setWidth(GameConstants.iconWidth);
        this.setHeight(GameConstants.iconHeight);
        setSpeed(speed);
        this.playingMode = playingMode;
    }

    /**
	 * @return the isDead
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * @param isDead the isDead to set
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	/**
     * Makes the player character "jump" - adds upward force
     */
    public void jump() {
        if (!isDead && isTouchingGround) {  	
            jumpIntent = true;
        }
    }

    /**
     * Calculates player physcics - gravity, speed and position
     */
    private void calculatePlayerPhysics() {
        getCurrentPosition().translate(new Vector2D(getSpeed().getX() * GameConstants.timeBetweenUpdates, getSpeed().getY() * GameConstants.timeBetweenUpdates + gravityTimer/1200));
        getSpeed().translate(new Vector2D(GameConstants.acceleration_X * GameConstants.timeBetweenUpdates,
                GameConstants.gravity_Y * GameConstants.timeBetweenUpdates));
        gravityTimer += 10;
        if (getSpeed().getY() >= GameConstants.playerFinalSpeed_Y) {
            getSpeed().setY(GameConstants.playerFinalSpeed_Y);
        }
        if (getSpeed().getX() >= GameConstants.playerFinalSpeed_X) {
            getSpeed().setX(GameConstants.playerFinalSpeed_X);
        }
    }

    /**
     * Sets isTouchingGround to true
     * TODO change that listener changes this propery
     */
    public void touchesGround() {
        isTouchingGround = true;
        gravityTimer = GameConstants.gravity_Y;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        if (isTouchingGround) {
            if (jumpIntent) {
                getSpeed().setY(GameConstants.playerJumpingOffset);
                jumpIntent = false;
                isTouchingGround = false;
            } else {
                getSpeed().setY(0);
            }
        }
//        System.out.println("Is touching u draw: " + isTouchingGround);
        if (!isTouchingGround) {
            setRotation(getRotation() + GameConstants.playerRotationSpeed* GameConstants.timeBetweenUpdates);
            Vector2D v = getCenterPosition().translated(getCurrentPosition().reversed()).rotated(getRotation());
//			System.err.println("U zraku: " + getRotation());	// za testiranje
        } else {
            this.rotation = (int) this.rotation % 360;
            if (this.rotation >= 45 && this.rotation < 135) {
                this.rotation = 90;
            } else if (this.rotation >= 135 && this.rotation < 225) {
                this.rotation = 180;
            } else if (this.rotation >= 225 && this.rotation < 315) {
                this.rotation = 270;
            } else if (this.rotation >= 315 || this.rotation < 45) {
                this.rotation = 0;
            }
//			System.err.println("Stojim: " + getRotation());	// za testiranje
        }

//        System.out.println("Player x: " + rotatingPoint.getX());
//        System.out.println("Player y: " + rotatingPoint.getY());
//        System.out.println("Rotation: " + getRotation());
        Rotate r = new Rotate(getRotation(), getCurrentPosition().getX() + GameConstants.iconWidth / 2.0, getCurrentPosition().getY() + GameConstants.iconHeight / 2.0);
        graphicsContext.save();
        graphicsContext.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        graphicsContext.drawImage(getIcon(), getCurrentPosition().getX(), getCurrentPosition().getY());
        graphicsContext.restore();
        calculatePlayerPhysics();
    }

    @Override
    public GameObject copy() {
        return new Player(this.rotation, this.speed, this.getIconPath(), this.playingMode);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playingMode == null) ? 0 : playingMode.hashCode());
		result = prime* result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (playingMode != other.playingMode)
			return false;
		if(this.id != other.id)
			return false;
		return true;
	}


    
}
