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

	//za jednu tocku provjerava je li između lijeve i desne strane bloka
	@Override
	public boolean contains(Vector2D p) {
		return (p.getX() >= getCurrentPosition().getX() && p.getX() <= getCurrentPosition().getX()+ getWidth()
					&& 	p.getY() >= getCurrentPosition().getY() && p.getY() <= getCurrentPosition().getY()+ getHeight());
			
	}

	//provjerava je li player down left i down right corner izmedu up left i up right cornera blocka
	//trenutno mozda problem ako imamo piramidicu blokova jer player ne umre ako takve lijevi rub gornjeg bloka
	public boolean playerIsOn(Player player){
		Vector2D playerDL = player.getCurrentPosition().translated(new Vector2D(0,player.getHeight()));
		Vector2D playerDR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), player.getHeight()));
		Vector2D blockUL = this.getCurrentPosition();
		Vector2D blockUR = this.getCurrentPosition().translated(new Vector2D(getWidth(),0));

		return playerDL.getY() >= blockUL.getY()
				&& ((playerDR.getX() > blockUL.getX()
					&& playerDR.getX() < blockUR.getX())
					|| (playerDL.getX() > blockUL.getX()
					&& playerDL.getX() < blockUR.getX()));
	}

	//provjerava je li UR corner ili DR corner "u" bloku
	@Override
	public boolean checkCollisions(Player player) {
//		Vector2D playerUR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), 0));
//		Vector2D playerDR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), player.getHeight()));
//
//		return this.contains(playerDR) || this.contains(playerUR);
		return false;
	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(this.image,  getCurrentPosition().getX(), getCurrentPosition().getY());
	}
}
