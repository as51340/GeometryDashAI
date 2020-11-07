package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GrassSpike extends Obstacle{
	
    public GrassSpike(Vector2D position) {
        setCurrentPosition(position);
        this.setWidth(GameConstants.iconHeight);
        this.setHeight(GameConstants.iconHeight);
      //  this.setIcon(new Image("/hr/fer/zemris/project/geom/dash/obstacles/grassspike/blue.png", getWidth(), getHeight(), false, false));
    }
    
    /**
     * Constructor that accepts only image
     * @param image
     */
    public GrassSpike(Image icon) {
    	setIcon(icon);
    }

    @Override
    public boolean checkCollisions(Player player) {
        Vector2D playerDL = player.getCurrentPosition().translated(new Vector2D(0,player.getHeight()));
        Vector2D playerDR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), player.getHeight()));

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

	@Override
	public GameObject copy() {
		return new GrassSpike(getIcon());
	}
}
