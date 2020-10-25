package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Spike extends Obstacle {
    //note: position here is lowe left corner!
    public Spike(Vector2D position) {
        super(position);
        this.setWidth(GameConstants.iconHeight); //stranica a
        this.setHeight((int) (GameConstants.iconHeight * Math.sqrt(3) / 2)); // visina trokuta
        this.setIcon(new Image("/hr/fer/zemris/project/geom/dash/obstacles/spike/placeholder-spike-icon.png", getWidth(), getHeight(), false, false));
    }


    //TODO check if correct
    @Override
    public boolean checkCollisions(Player player) {
        return this.contains(player.getPosition());
    }

    @Override
    public boolean contains(Vector2D p) {
        double yk = p.getY() + GameConstants.iconHeight;
        double yt = this.getPositionY();
        double xt = this.getPositionX();
        double stranicaL = (yk - yt) / Math.sqrt(3) + xt;
        double stranicaR = (yt - yk) / Math.sqrt(3) + xt + this.getWidth();
        return (stranicaL >= xt) && (stranicaR <= (xt + this.getWidth()));

    }


    @Override
    public void update(GraphicsContext graphics, Vector2D cameraPosition) {
        //graphics.drawImage(this.getIcon(), this.getPositionX(), this.getPositionY());
        graphics.fillPolygon(new double[]{this.getPositionX(),this.getPositionX()+getWidth(), this.getPositionX()+getWidth()/2.0}, new double[]{this.getPositionY()+getHeight(), this.getPositionY()+getHeight(), this.getPositionY()}, 3);
        this.translate();
    }


}
