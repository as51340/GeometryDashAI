package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class Spike extends Obstacle {
	
	public Spike(Vector2D position) {
		setCurrentPosition(position);
	}

	@Override
    public boolean checkCollisions(Player player) {
//        Vector2D obstacleUL = new Vector2D(getPositionX(), getPositionY());
//        Vector2D obstacleDR = new Vector2D(getPositionX()+getWidth(), getPositionY()+getHeight());
//    public boolean checkCollisions() {
//        Vector2D obstacleUL = new Vector2D(this.getPosition().getX(), this.getPosition().getY());
//        Vector2D obstacleDR = new Vector2D(this.getPosition().getX() +getWidth(), this.getPosition().getY()+getHeight());
        //Vector2D player = Player.getPosition(); //TODO Player.getPosition()
        Vector2D playerDL = new Vector2D(0, 0); //TODO fix position
        Vector2D playerDR = new Vector2D(0, 0); //TODO fix position

        if(this.contains(playerDR) || this.contains(playerDL)){
            return true;
        }
        return false;
    }

	@Override
	public boolean contains(Vector2D p) {
		return false;
	}

//	@Override
//	public void update(GraphicsContext graphics, Vector2D cameraPosition) {

//	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		// TODO Auto-generated method stub
		
	}

}
