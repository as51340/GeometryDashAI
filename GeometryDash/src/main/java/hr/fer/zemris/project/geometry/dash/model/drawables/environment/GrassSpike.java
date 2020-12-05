package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GrassSpike extends Obstacle {

    public GrassSpike(Vector2D position, String uriIcon) {
        setCurrentPosition(position);
        this.setWidth(GameConstants.iconHeight);
        this.setHeight(GameConstants.iconHeight);
        setCenterPosition(new Vector2D(position.getX() / 2, position.getY() / 2));
        setIcon(Utils.loadIcon(uriIcon, GameConstants.iconWidth, GameConstants.iconHeight));
        setIconPath(uriIcon);
        setName("GrassSpike");
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
    public GrassSpike(String name, Vector2D currentPosition, int height, int width, String iconPath) {
        setName(name);
        setCurrentPosition(currentPosition);
        setHeight(height);
        setWidth(width);
        setIconPath(iconPath);
        setCenterPosition(new Vector2D(currentPosition.getX() / 2, currentPosition.getY() / 2));
        setIcon(Utils.loadIcon(iconPath, GameConstants.iconWidth, GameConstants.iconHeight));
    }

    @Override
    public boolean checkCollisions(Player player) {
        Vector2D playerDL = player.getCurrentPosition().translated(new Vector2D(0, player.getHeight()));
        Vector2D playerDR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), player.getHeight()));

        return this.contains(playerDR) || this.contains(playerDL);
    }

    @Override
    public boolean contains(Vector2D p) {
        return p.getX() >= this.getCurrentPosition().getX()
                && p.getX() <= (this.getCurrentPosition().getX() + this.getWidth())
                && p.getY() <= (this.getCurrentPosition().getY() + this.getHeight())
                && p.getY() >= (this.getCurrentPosition().getY());
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(this.getIcon(), getCurrentPosition().getX(), getCurrentPosition().getY());
        //here it should be image
    }

    @Override
    public GameObject copy() {
        return new GrassSpike(getCurrentPosition().copy(), new String(getIconPath()));
    }
}
