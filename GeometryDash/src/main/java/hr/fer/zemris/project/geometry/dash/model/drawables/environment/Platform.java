package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Platform extends Obstacle{
	private Image image;
    public Platform(Vector2D position, int width, String image) {
        setCurrentPosition(position);
        setHeight(GameConstants.iconHeight/2);
        setWidth(width);
        this.image = new Image(image, getWidth(), getHeight(), false, false);
    }


    public Image getImage() {
    	return this.image;
    }
    
    @Override
    public boolean checkCollisions(Player player) {
        return false;
    }

    @Override
    public boolean contains(Vector2D p) {
        return (p.getX() >= getCurrentPosition().getX() && p.getX() <= getCurrentPosition().getX()+ getWidth()
        		&& p.getY() >= getCurrentPosition().getY() && p.getY() <= getCurrentPosition().getY()+ getHeight());
    }

    public boolean playerIsOn(Player player){
        Vector2D playerDL = new Vector2D(player.getCurrentPosition().getX(), player.getCurrentPosition().getY() + GameConstants.iconHeight);
        Vector2D playerDR = new Vector2D(player.getCurrentPosition().getX() + GameConstants.iconHeight, player.getCurrentPosition().getY() + GameConstants.iconHeight);


        return playerDL.getY() == this.getCurrentPosition().getY()
                && playerDR.getX() > this.getCurrentPosition().getX()
                && playerDL.getX() < (this.getCurrentPosition().getX()+this.getWidth());
    }

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(this.image,  getCurrentPosition().getX(), getCurrentPosition().getY());
	}
    
//	@Override
//	public void update(GraphicsContext graphics, Vector2D cameraPosition) {
//		graphics.drawImage(this.getImage(),getCurrentPosition().getX(), getCurrentPosition().getY());
//		getCurrentPosition().translate(new Vector2D(-GameConstants.timeBetweenUpdates * 50f, 0));
//		
//	}
}
