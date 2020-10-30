package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Platform extends Obstacle{
	private Image image;
    public Platform(Vector2D position, int width, String image) {
        super(position);
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
        return (p.getX() >= this.getPosition().getX() && p.getX() <= this.getPosition().getX()+this.getWidth()
        		&& p.getY() >= this.getPosition().getY() && p.getY() <= this.getPosition().getY()+this.getHeight());
    }
    
	@Override
	public void update(GraphicsContext graphics, Vector2D cameraPosition) {
		graphics.drawImage(this.getImage(), this.getPosition().getX(), this.getPosition().getY());
		this.getPosition().translate(new Vector2D(-GameConstants.timeBetweenUpdates * 50f, 0));
		
	}
}
