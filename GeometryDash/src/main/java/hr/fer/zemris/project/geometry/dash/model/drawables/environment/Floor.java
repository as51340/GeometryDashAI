package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Floor extends Obstacle{
	
	/**
	 * Ground has to stay with camera, it must not finish
	 */
	private Camera camera;
	
    public Floor(Vector2D position) {
        setCurrentPosition(position);
    }
    
    @Override
    public boolean checkCollisions(Player player) {
        return false;
    }
   
    @Override
    public boolean contains(Vector2D p) {
        return false;
    }
    
	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.setLineWidth(4);
		if(camera != null) { //and it should be
			setCurrentPosition(new Vector2D(camera.getPosition().getX(), getCurrentPosition().getY()));	
		}
		graphicsContext.strokeLine(getCurrentPosition().getX(), getCurrentPosition().getY(), GameConstants.WIDTH + getCurrentPosition().getX(),
		getCurrentPosition().getY());
	}
	
	/**
	 * Sets camera
	 * @param camera
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	@Override
	public GameObject copy() {
		return null;
	}
	
    
}
