package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;

public class Platform extends Obstacle {

    @Override
    public Vector2D getCenterPosition() {
        return getCurrentPosition().translated(new Vector2D(getWidth() / 2.0, getHeight() / 6.0));
    }

    public Platform(Vector2D position, String image) {
        setInitialPosition(position.copy());
        setCurrentPosition(position);
        setHeight(GameConstants.iconHeight);
        setWidth(GameConstants.iconWidth);
        setIconPath(image);
        setIcon(image);
        setName("Platform");
    }


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

        return Math.abs(xDiff) <= getWidth() && Math.abs(yDiff) <= getHeight() / 2.0;
    }

    @Override
    public boolean playerIsOn(Player player) {
        Vector2D playerDL = player.getCurrentPosition().translated(new Vector2D(0, player.getHeight()));
        Vector2D playerDR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), player.getHeight()));
        Vector2D platformUL = this.getCurrentPosition();
        Vector2D platformUR = this.getCurrentPosition().translated(new Vector2D(getWidth(), 0));

        return playerDL.getY() >= this.getCurrentPosition().getY()
                && playerDL.getY() <= this.getCurrentPosition().getY() + this.getHeight() / 2.0
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
        return new Platform(getCurrentPosition().copy(), getIconPath());
    }

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
    
    

}
