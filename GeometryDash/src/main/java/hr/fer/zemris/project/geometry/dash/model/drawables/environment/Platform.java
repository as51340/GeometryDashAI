package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//TODO why to use specific width - every platform is 45
public class Platform extends Obstacle {

    public Platform(Vector2D position, int width, String image) {
        this(position, image);
        setHeight(GameConstants.iconHeight / 2);
        setWidth(width);
        setName("Platform");
    }

    @Override
    public Vector2D getCenterPosition() {
        return getCurrentPosition().translated(new Vector2D(getWidth()/2.0, getHeight()*2.0/3.0));
    }

    public Platform(Vector2D position, String image) {
        setCurrentPosition(position);
        setIconPath(image);
        setIcon(Utils.loadIcon(image, GameConstants.iconWidth, GameConstants.iconHeight));
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
    public Platform(String name, Vector2D currentPosition, int height, int width, String iconPath) {
        setName(name);
        setCurrentPosition(currentPosition);
        setHeight(height);
        setWidth(width);
        setIconPath(iconPath);
        setIcon(Utils.loadIcon(iconPath, GameConstants.iconWidth, GameConstants.iconHeight));
    }

    //provjerava da li se playerov gornje lijevi ili desni kut nalazi "u" platformi
    //tj da li ju je lupio od dole
    @Override
    public boolean checkCollisions(Player player) {
//        Vector2D playerUL = player.getCurrentPosition();
//        Vector2D playerUR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), 0));
//
//        return contains(playerUL) || contains(playerUR);
//
        Vector2D centerDiff = this.getCenterPosition().translated(player.getCenterPosition().reversed());
        double xDiff = centerDiff.getX();
        double yDiff = centerDiff.getY();

        return Math.hypot(xDiff, yDiff*2.8) <= getWidth();


    }

    //provjerava je li tocka "u"platformi
    @Override
    public boolean contains(Vector2D p) {
        return (p.getX() >= getCurrentPosition().getX() && p.getX() <= getCurrentPosition().getX() + getWidth()
                && p.getY() >= getCurrentPosition().getY() && p.getY() <= getCurrentPosition().getY() + getHeight() / 2);
    }

    //provjerava je li player na platformi
    public boolean playerIsOn(Player player) {
        Vector2D playerDL = player.getCurrentPosition().translated(new Vector2D(0, player.getHeight()));
        Vector2D playerDR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), player.getHeight()));
        Vector2D playerUL = player.getCurrentPosition();
        Vector2D platformUL = this.getCurrentPosition();
        Vector2D platformUR = this.getCurrentPosition().translated(new Vector2D(getWidth(), 0));

        return playerDL.getY() >= this.getCurrentPosition().getY()
                && playerDL.getY() <= this.getCurrentPosition().getY() + this.getHeight()
                // ^^^ this used to be UL, which makes collisions impossible
                && ((playerDR.getX() > platformUL.getX()
                && playerDR.getX() < platformUR.getX())
                || (playerDL.getX() > platformUL.getX()
                && playerDL.getX() < platformUR.getX()));
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(getIcon(), getCurrentPosition().getX(), getCurrentPosition().getY());
    }

    @Override
    public GameObject copy() {
        return new Platform(getCurrentPosition().copy(), this.getWidth(), new String(getIconPath()));
    }

}
