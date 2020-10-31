package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class represents a 1x1 block obstacle.
 * 
 * @author mskrabic
 *
 */
public class Block extends Obstacle {
	
	/**
	 * Image of the block.
	 */
	private Image image;
	
	/**
	 * Constructs a <code>Block</code>.
	 * @param position position of the block.
	 * @param image image of the block.
	 */
	public Block(Vector2D position, String image) {
		setCurrentPosition(position);
		setHeight(GameConstants.iconHeight);
		setWidth(GameConstants.iconHeight);
		this.image = new Image(image, getWidth(), getHeight(), false, false);	
	}

	/**
	 * Returns the image of the block.
	 * 
	 * @return image of the block.
	 */
	public Image getImage() {
		return this.image;
	}
	
	/**
	 * Updates the block's position by translating it left after every tick.
	 */
//	@Override
//	public void update(GraphicsContext graphics, Vector2D cameraPosition) {
//		graphics.drawImage(getImage(), getCurrentPosition().getX(), getCurrentPosition().getY());
//		getCurrentPosition().translate(new Vector2D(-GameConstants.timeBetweenUpdates * GameConstants.obstaclesSpeed, 0));
//	}

	@Override
	public boolean contains(Vector2D p) {
		return (p.getX() >= getCurrentPosition().getX() && p.getX() <= getCurrentPosition().getX()+ getWidth()
					&& 	p.getY() >= getCurrentPosition().getY() && p.getY() <= getCurrentPosition().getY()+ getHeight());
			
	}

	@Override
	public boolean checkCollisions(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(this.image,  getCurrentPosition().getX(), getCurrentPosition().getY());
	}
}
