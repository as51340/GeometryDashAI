package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;

public class GrassSpike extends Obstacle {

    public GrassSpike(Vector2D position, String uriIcon) {
        setInitialPosition(position.copy());
        setCurrentPosition(position);
        setWidth(GameConstants.iconWidth);
        setHeight(GameConstants.iconHeight);
        setIcon(uriIcon);
        setIconPath(uriIcon);
        setName("GrassSpike");
    }

    /**
     * Returns Vector2D of center position
     *
     * @return Vector2D of center position
     */
    @Override
    public Vector2D getCenterPosition() {
        return this.getCurrentPosition().translated(new Vector2D(this.getWidth() / 2.0, this.getHeight() * 0.75));
    }

    @Override
    public boolean checkCollisions(Player player) {
        Vector2D centerDiff = this.getCenterPosition().translated(player.getCenterPosition().reversed());
        double xDiff = centerDiff.getX();
        double yDiff = centerDiff.getY();

        return Math.abs(xDiff) <= 4.0 * getWidth() / 5.0 && Math.abs(yDiff) <= getHeight() / 2.0;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(this.getIcon(), getCurrentPosition().getX(), getCurrentPosition().getY());
    }

    @Override
    public GameObject copy() {
        return new GrassSpike(getCurrentPosition().copy(), getIconPath());
    }
}
