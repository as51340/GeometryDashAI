package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class GrassSpike extends Obstacle {
    public GrassSpike(Vector2D position) {
        super(position);
        this.setWidth(2 * GameConstants.iconHeight);
        this.setHeight(GameConstants.iconHeight);
        this.setIcon(new Image("/hr/fer/zemris/project/geom/dash/obstacles/grassspike/placeholder-grassspike-icon.png", getWidth(), getHeight(), false, false));
    }

//    @Override
//    public boolean checkCollisions(Player player) {
//        return this.getBoundsInParent().intersects(player.getBoundsInParent());
//    }

    @Override
    public boolean checkCollisions(Player player) {
        Vector2D playerDL = new Vector2D(GameConstants.playerPosition_X, player.getPosition().getY() + GameConstants.iconHeight);
        Vector2D playerDR = new Vector2D(GameConstants.playerPosition_X + GameConstants.iconHeight, player.getPosition().getY() + GameConstants.iconHeight);

        return this.contains(playerDR) || this.contains(playerDL);
    }

    @Override
    public boolean contains(Vector2D p) {
        return p.getX() >= this.getPositionX()
                && p.getX() <= (this.getPositionX() + this.getWidth())
                && p.getY() <= (this.getPositionY() + this.getHeight())
                && p.getY() >= (this.getPositionY());
        /*
        if(p.getY() <= (this.getPositionY()+getHeight()) && p.getY() >= this.getPositionY()){
            return p.getX() <= (this.getPositionX() + getWidth()) && p.getX() >= this.getPositionX();
        }
        return false;*/
    }

    @Override
    public void update(GraphicsContext graphics, Vector2D cameraPosition) {
        graphics.drawImage(this.getIcon(), this.getPositionX(), this.getPositionY());
        this.translate();
    }
}
