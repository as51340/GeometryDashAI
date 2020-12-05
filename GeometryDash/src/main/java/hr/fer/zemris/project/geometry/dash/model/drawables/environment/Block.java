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
     *
     * @param position position of the block.
     */
    public Block(Vector2D position, String iconPath) {
        setCurrentPosition(position);
        setHeight(GameConstants.iconHeight);
        setWidth(GameConstants.iconWidth);
        setCenterPosition(new Vector2D(position.getX() / 2, position.getY() / 2));
        setIconPath(iconPath);
        setIcon(Utils.loadIcon(iconPath, GameConstants.iconWidth, GameConstants.iconHeight));
        setName("Block");
    }

    /**
     * Constructor that accepts all {@linkplain GameObject}'s parameters
     *
     * @param name            object name
     * @param currentPosition current position
     * @param height          height
     * @param width           width
     * @param iconPath        path to icon
     */
    public Block(String name, Vector2D currentPosition, int height, int width, String iconPath) {
        setName(name);
        setCurrentPosition(currentPosition);
        setCenterPosition(new Vector2D(currentPosition.getX() / 2, currentPosition.getY() / 2));
        setHeight(height);
        setWidth(width);
        setIconPath(iconPath);
        setIcon(Utils.loadIcon(iconPath, GameConstants.iconWidth, GameConstants.iconHeight));
    }


    //za jednu tocku provjerava je li između lijeve i desne strane bloka
    @Override
    public boolean contains(Vector2D p) {
        return (p.getX() >= getCurrentPosition().getX() && p.getX() <= (getCurrentPosition().getX() + getWidth())
                && p.getY() > getCurrentPosition().getY() && p.getY() < (getCurrentPosition().getY() + getHeight()));
    }

    @Override
    public boolean contains(Player player) {
        Vector2D centerDiff = player.getCenterPosition().translated(this.getCenterPosition().reversed());
        return Math.hypot(centerDiff.getX(), centerDiff.getY()) <= getHeight();
    }

    public boolean playerIsOn(Player player) {

        return false;
    }

    //TODO promijeni kao provjeru sa sredistima
    @Override
    public boolean checkCollisions(Player player) {
        // od koord. centra blocka (generalno vece) se oduzmu koord. centra playera (generalno manje)
        Vector2D centerDiff = this.getCenterPosition().translated(player.getCenterPosition().reversed());
        double xDiff = centerDiff.getX();   // ako je xDiff pozitivan, player se nalazi ~lijevo od blocka
        double yDiff = centerDiff.getY();   // ako je yDiff pozitivan, player se nalazi ~iznad blocka

        if (Math.hypot(xDiff, yDiff) <= getWidth()) {
            if (Math.abs(xDiff) <= Math.abs(yDiff) && yDiff >= 0) {
                // ON ZIZI (zivi al na meg jeziku)
                player.touchesGround();
                player.getCurrentPosition().setY(this.getCurrentPosition().getY() - GameConstants.iconHeight);
            } else {
                // ON MRI
                return true;
            }
        }

        return false;

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
