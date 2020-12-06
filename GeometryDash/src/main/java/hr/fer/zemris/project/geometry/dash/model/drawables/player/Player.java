package hr.fer.zemris.project.geometry.dash.model.drawables.player;


import com.google.gson.annotations.Expose;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

/**
 * The main player class, logics and engine behind the "protagonist" of <strong>Geometry Dash</strong>
 *
 * @author Damjan, Andi
 */
public class Player extends GameObject {
    private double gravityTimer = GameConstants.gravity_Y;

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
     * Object's speed
     */
    @Expose
    private Vector2D speed;

    private Vector2D rotatingPoint;

    /**
     * Player's constructor
     */
    public Player(double rotation, Vector2D speed, String icon) {
        this.rotation = rotation;
        this.speed = speed;
        setIcon(Utils.loadIcon(icon, GameConstants.iconWidth, GameConstants.iconHeight));
    }

    public Vector2D getRotatingPoint() {
        return rotatingPoint;
    }

    public void setRotatingPoint(Vector2D rotatingPoint) {
        this.rotatingPoint = rotatingPoint;
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
     * Constructs a <code>Player</code>
     *
     * @param position
     */
    public Player(Vector2D position, Vector2D speed) {
        setCurrentPosition(position);
        rotatingPoint = position.copy();
        setIcon(CharactersSelector.selectedCharacter.getIcon());
        this.setWidth(GameConstants.iconWidth);
        this.setHeight(GameConstants.iconHeight);
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
        getCurrentPosition().translate(new Vector2D(getSpeed().getX() * GameConstants.timeBetweenUpdates, getSpeed().getY() * GameConstants.timeBetweenUpdates + gravityTimer/1200));
        getSpeed().translate(new Vector2D(GameConstants.acceleration_X * GameConstants.timeBetweenUpdates,
                GameConstants.gravity_Y * GameConstants.timeBetweenUpdates));
        gravityTimer += 10;
        System.out.println(gravityTimer);
        if (getSpeed().getY() >= GameConstants.playerFinalSpeed_Y) {
            getSpeed().setY(GameConstants.playerFinalSpeed_Y);
        }
        if (getSpeed().getX() >= GameConstants.playerFinalSpeed_X) {
            getSpeed().setX(GameConstants.playerFinalSpeed_X);
        }
        rotatingPoint = getCenterPosition().copy();
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
        if (!isTouchingGround) {
            setRotation(getRotation() + GameConstants.playerRotationSpeed* GameConstants.timeBetweenUpdates);
            Vector2D v = getCenterPosition().translated(getCurrentPosition().reversed()).rotated(getRotation());
            rotatingPoint = getCenterPosition().translated(v);
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
            rotatingPoint = getCenterPosition().copy();
//			System.err.println("Stojim: " + getRotation());	// za testiranje
        }

        System.out.println("Player x: " + rotatingPoint.getX());
        System.out.println("Player y: " + rotatingPoint.getY());
        System.out.println("Rotation: " + getRotation());
        Rotate r = new Rotate(getRotation(), getCurrentPosition().getX() + GameConstants.iconWidth / 2.0, getCurrentPosition().getY() + GameConstants.iconHeight / 2.0);
        graphicsContext.save();
        graphicsContext.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        graphicsContext.drawImage(getIcon(), getCurrentPosition().getX(), getCurrentPosition().getY());
        graphicsContext.restore();
        calculatePlayerPhysics();
    }

    @Override
    public GameObject copy() {
        return new Player(this.rotation, this.speed, this.getIconPath());
    }


}
