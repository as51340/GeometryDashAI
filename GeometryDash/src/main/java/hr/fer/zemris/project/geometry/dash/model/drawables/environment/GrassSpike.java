package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class GrassSpike extends Obstacle{
    public GrassSpike(Vector2D position) {
        super(position);
        this.setWidth(2*GameConstants.iconHeight);
        this.setHeight(GameConstants.iconHeight);
    }

//    @Override
//    public boolean checkCollisions(Player player) {
//        return this.getBoundsInParent().intersects(player.getBoundsInParent());
//    }

    @Override
    public boolean checkCollisions(Player player) {
        Vector2D playerDL = new Vector2D(player.getPosition().getX(), player.getPosition().getY()+GameConstants.iconHeight);
        Vector2D playerDR = new Vector2D(player.getPosition().getX()+GameConstants.iconHeight, player.getPosition().getY()+GameConstants.iconHeight);


        return this.contains(playerDR) || this.contains(playerDL);
    }

    @Override
    public boolean contains(Vector2D p){
        if(p.getY() <= (this.getPosition().getY()+getHeight()) && p.getY() >= this.getPosition().getY()){
            return p.getX() <= (this.getPosition().getX() + getWidth()) && p.getX() >= this.getPosition().getX();
        }
        return false;
    }

    @Override
    public void update(GraphicsContext graphics, Vector2D cameraPosition) {
        graphics.fillRect(this.getPosition().getX(), this.getPosition().getY(), this.getWidth(), this.getHeight());
        this.translate();
    }
}
