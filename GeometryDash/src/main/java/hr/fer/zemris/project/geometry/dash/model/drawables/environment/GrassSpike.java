package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class GrassSpike extends Obstacle{
    public GrassSpike(Vector2D position) {
        setCurrentPosition(position);
        this.setWidth(2 * GameConstants.iconHeight);
        this.setHeight(GameConstants.iconHeight);
        this.setIcon(new Image("/hr/fer/zemris/project/geom/dash/obstacles/grassspike/placeholder-grassspike-icon.png", getWidth(), getHeight(), false, false));
    }

    @Override
    public boolean checkCollisions(Player player) {
        Vector2D playerDL = new Vector2D(player.getCurrentPosition().getX(), player.getCurrentPosition().getY() + GameConstants.iconHeight);
        Vector2D playerDR = new Vector2D(player.getCurrentPosition().getX() + GameConstants.iconHeight, player.getCurrentPosition().getY() + GameConstants.iconHeight);

        return this.contains(playerDR) || this.contains(playerDL);
    }

    @Override
    public boolean contains(Vector2D p){
        return p.getX() >= this.getCurrentPosition().getX()
                && p.getX() <= (this.getCurrentPosition().getX() + this.getWidth())
                && p.getY() <= (this.getCurrentPosition().getY() + this.getHeight())
                && p.getY() >= (this.getCurrentPosition().getY());
    }

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(this.getIcon(),  getCurrentPosition().getX(), getCurrentPosition().getY());
		//here it should be image
	}
}
