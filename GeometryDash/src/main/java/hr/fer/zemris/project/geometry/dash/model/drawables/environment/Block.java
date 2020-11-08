package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class represents a 1x1 block obstacle.
 *
 * @author mskrabic
 */
public class Block extends Obstacle {

    /**
     * Constructs a <code>Block</code>.
     * @param position position of the block.
     * @param image    image of the block.
     */
    public Block(Vector2D position, String iconPath) {
        setCurrentPosition(position);
        setHeight(GameConstants.iconHeight);
        setWidth(GameConstants.iconHeight);
        setIconPath(iconPath);
        setIcon(Utils.loadIcon(iconPath));
        setName("Block");
    }
    
    /**
     * Constructor that accepts all {@linkplain GameObject}'s parameters 
     * @param name object name
     * @param currentPosition current position
     * @param height height
     * @param width width
     * @param iconPath path to icon
     */
    public Block(String name, Vector2D currentPosition, int height, int width, String iconPath) {
    	setName(name);
    	setCurrentPosition(currentPosition);
    	setHeight(height);
    	setWidth(width);
    	setIconPath(iconPath);
    	setIcon(Utils.loadIcon(iconPath));
    }
    
    
    //za jednu tocku provjerava je li između lijeve i desne strane bloka
    @Override
    public boolean contains(Vector2D p) {
        return (p.getX() >= getCurrentPosition().getX() && p.getX() <= (getCurrentPosition().getX() + getWidth())
                && p.getY() > getCurrentPosition().getY() && p.getY() < (getCurrentPosition().getY() + getHeight()));

    }

    //provjerava je li player down left i down right corner izmedu up left i up right cornera blocka
    //trenutno mozda problem ako imamo piramidicu blokova jer player ne umre ako takve lijevi rub gornjeg bloka
    public boolean playerIsOn(Player player) {
        Vector2D playerDL = player.getCurrentPosition().translated(new Vector2D(0, player.getHeight()));
        Vector2D playerDR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), player.getHeight()));
        Vector2D playerUL = player.getCurrentPosition();
        Vector2D blockUL = this.getCurrentPosition();
        Vector2D blockUR = this.getCurrentPosition().translated(new Vector2D(getWidth(), 0));

//        iskljuceno zbog testiranja
//        return playerDL.getY() >= blockUL.getY()
//                && playerUL.getY() < this.getCurrentPosition().getY() + this.getHeight()
//                && ((playerDR.getX() >= blockUL.getX()
//                && playerDR.getX() <= blockUR.getX())
//                || (playerDL.getX() >= blockUL.getX()
//                && playerDL.getX() <= blockUR.getX()));

		return (contains(playerDR) || contains(playerDL)) && checkPlayerAngle(player);

	}

    //provjerava je li UR corner ili DR corner "u" bloku
    //TODO promijeni kao provjeru s pravcima
    @Override
    public boolean checkCollisions(Player player) {
        Vector2D playerUR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), 0));
        Vector2D playerUL = player.getCurrentPosition();
        Vector2D playerDR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), player.getHeight()));
        Vector2D obstacleUL = this.getCurrentPosition();

//        iskoljuceno zbog testiranja
//        if(contains(playerUR) || contains(playerUL)) return true;
//        else if (playerDR.getX() >= getCurrentPosition().getX() && playerDR.getX() <= getCurrentPosition().getX() + getWidth()
//                && playerDR.getY() >= 6.0/5.0*getCurrentPosition().getY() && playerDR.getY() <= getCurrentPosition().getY() + getHeight()){
//            return true;
//        }

		return (contains(playerUR) || contains(playerUL) || contains(playerDR)) && !checkPlayerAngle(player);

//        return false;

    }

    private boolean checkPlayerAngle(Player player) {
    	double yCrit = player.getCurrentPosition().getX()
				- this.getCurrentPosition().getX()
				+ this.getCurrentPosition().getY();

    	return player.getCurrentPosition().getY() < yCrit;
	}

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(getIcon(), getCurrentPosition().getX(), getCurrentPosition().getY());
    }

	@Override
	public GameObject copy() {
		return new Block(getCurrentPosition().copy(), new String(getIconPath()));
	}
}
