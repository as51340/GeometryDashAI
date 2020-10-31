package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class GrassSpike extends Obstacle{
    public GrassSpike(Vector2D position) {
        setCurrentPosition(position);
        setWidth(2*GameConstants.iconHeight);
        setHeight(GameConstants.iconHeight);
    }

//    @Override
//    public boolean checkCollisions(Player player) {
//        return this.getBoundsInParent().intersects(player.getBoundsInParent());
//    }

    @Override
    public boolean checkCollisions(Player player) {
        Vector2D playerDL = new Vector2D(player.getCurrentPosition().getX(), player.getCurrentPosition().getY()+GameConstants.iconHeight);
        Vector2D playerDR = new Vector2D(player.getCurrentPosition().getX()+GameConstants.iconHeight, player.getCurrentPosition().getY()+GameConstants.iconHeight);


        return this.contains(playerDR) || this.contains(playerDL);
    }

    @Override
    public boolean contains(Vector2D p){
        if(p.getY() <= (getCurrentPosition().getY()+getHeight()) && p.getY() >= getCurrentPosition().getY()){
            return p.getX() <= (getCurrentPosition()).getX() + getWidth() && p.getX() >= getCurrentPosition().getX();
        }
        return false;
    }

//    @Override
//    public void update(GraphicsContext graphics, Vector2D cameraPosition) {
//        graphics.fillRect(getCurrentPosition().getX(), getCurrentPosition().getY(), getWidth(), getHeight());
//    }

	@Override
	public void draw(GraphicsContext graphicsContext) {
		//graphicsContext.drawImage(this.image,  getCurrentPosition().getX(), getCurrentPosition().getY());
		//here it should be image
	}
}
