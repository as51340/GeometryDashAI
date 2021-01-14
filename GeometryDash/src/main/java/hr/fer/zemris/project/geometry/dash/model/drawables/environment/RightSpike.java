package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;

public class RightSpike extends Obstacle {
    @Override
    public Vector2D getCenterPosition() {
        return getCurrentPosition().translated(new Vector2D(getWidth() / 2.0, getHeight() / 2.0));
    }

    //note: position here is lower left corner!
    public RightSpike(Vector2D position, String uriIcon) {
        setCurrentPosition(position);
        setInitialPosition(position.copy());
        this.setWidth(GameConstants.iconWidth); //visina trokuta
        this.setHeight(GameConstants.iconHeight); // baza trokuta
        setIconPath(uriIcon);
        setIcon(uriIcon);
        setName("RightSpike");
    }

    @Override
    public boolean checkCollisions(Player player) {
        Vector2D centerDiff = this.getCenterPosition().translated(player.getCenterPosition().reversed());
        double xDiff = centerDiff.getX();   // ako je xDiff pozitivan, player se nalazi ~lijevo od blocka
        double yDiff = centerDiff.getY();   // ako je yDiff pozitivan, player se nalazi ~iznad blocka
//      LEGACY, ne radi bas lijepo
//        return  (((Math.abs(yDiff) - xDiff) <= getHeight()) && xDiff < getHeight() * 5 / 6.0
//                && Math.abs(xDiff) <= getHeight() * 0.9 && Math.abs(yDiff) <= getHeight()*0.9);

        return ((((1.2 * Math.abs(yDiff)) - xDiff) <= getHeight()) && Math.abs(xDiff) <= getHeight() * 0.9
                && Math.abs(yDiff) <= getHeight() * 0.85 && yDiff < getHeight() * 0.8);
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(this.getIcon(), this.getCurrentPosition().getX(), this.getCurrentPosition().getY());
    }

    @Override
    public GameObject copy() {
        return new Spike(getCurrentPosition().copy(), getIconPath());
    }

    @Override
    public boolean playerIsOn(Player player) {
        return false;
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
