package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;

import com.google.gson.annotations.Expose;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;

/**
 * Floor object
 *
 * @author Andi Skrgat
 */
public class Floor extends Obstacle {

    /**
     * Ground has to stay with camera, it must not finish
     */
    @Expose
    private Camera camera;

    /**
     * Constructor that takes its position
     *
     * @param position floor position
     */
    public Floor(Vector2D position) {
        setInitialPosition(position.copy());
        setCurrentPosition(position);
        setName("Floor");
    }

    @Override
    public boolean checkCollisions(Player player) {
        return false;
    }


    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(4);
        if (camera != null) { //and it should be
            setCurrentPosition(new Vector2D(camera.getPosition().getX(), getCurrentPosition().getY()));
        }
        graphicsContext.strokeLine(getCurrentPosition().getX(), getCurrentPosition().getY(), GameConstants.WIDTH + getCurrentPosition().getX(),
                getCurrentPosition().getY());
    }

    /**
     * Sets camera
     *
     * @param camera camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public GameObject copy() {
        Floor floor = new Floor(initialPosition.copy());
        floor.setCamera(new Camera());
        return floor;
    }

    @Override
    public boolean playerIsOn(Player player) {
        double playerPos_Y = player.getCurrentPosition().getY();
        double floorPos_Y = getCurrentPosition().getY();
        return playerPos_Y + GameConstants.playerGroundOffset_Y >= floorPos_Y;

    }
}
