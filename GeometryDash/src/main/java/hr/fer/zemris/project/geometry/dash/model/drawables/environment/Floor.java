package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Floor extends Obstacle{
	
    public Floor(Vector2D position) {
        super(position);
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
	public void update(GraphicsContext graphics, Vector2D cameraPosition) {
//		graphics.clearRect(0, 0, 1920, 720);
//		graphics.moveTo(0, GameConstants.floorPosition_Y);
//		graphics.lineTo(GameConstants.WIDTH - cameraPosition.getX(), GameConstants.floorPosition_Y);
//	    graphics.stroke();
//	    System.out.println(GameConstants.WIDTH - cameraPosition.getX());
		graphics.setFill(Color.CHOCOLATE);
		graphics.fillRect(0, GameConstants.floorPosition_Y, 1920, 1080);
	}

    
}
