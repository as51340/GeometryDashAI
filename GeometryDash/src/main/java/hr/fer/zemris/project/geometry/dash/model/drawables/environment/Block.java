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
	 * @return image of the block.
	 */
	public Image getImage() {
		return this.image;
	}
	
	@Override
	public boolean contains(Vector2D p) {
		return (p.getX() > getCurrentPosition().getX() && p.getX() < getCurrentPosition().getX()+ getWidth()
					&& 	p.getY() > getCurrentPosition().getY() && p.getY() < getCurrentPosition().getY()+ getHeight());
			
	}

	@Override
	public boolean checkCollisions(Player player) {
		Vector2D playerUR = new Vector2D(player.getCurrentPosition().getX() + GameConstants.iconHeight, player.getCurrentPosition().getY());
		Vector2D playerDR = new Vector2D(player.getCurrentPosition().getX() + GameConstants.iconHeight, player.getCurrentPosition().getY() + GameConstants.iconHeight);

		return this.contains(playerDR) || this.contains(playerUR);
	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(this.image,  getCurrentPosition().getX(), getCurrentPosition().getY());
	}
}
