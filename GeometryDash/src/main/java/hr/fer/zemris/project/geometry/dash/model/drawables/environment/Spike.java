package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Spike extends Obstacle {
	//note: position here is lowe left corner!
	public Spike(Vector2D position) {
		setCurrentPosition(position);
		this.setWidth(GameConstants.iconHeight); //stranica a
		this.setHeight(GameConstants.iconHeight); // visina trokuta
		this.setIcon(new Image("/hr/fer/zemris/project/geom/dash/obstacles/spike/placeholder-spike-icon.png", getWidth(), getHeight(), false, false));
	}

	//daje se donji lijevi kut playera
	@Override
    public boolean checkCollisions(Player player) {
		return this.contains(player.getCurrentPosition().translated(new Vector2D(0, getHeight())));
    }

	//pravac playera yp = p.y+getHeight
	//sx = x pozicija donjeg lijevog kuta trokuta
	//sy = y pozicija donjeg lijevog kuta trokuta
	//a = stranica/visina trokuta
	//x1 je tocka presjecista sa stanicom spikea koja je bliza lijevom rubu
	//x2 je tocka presjecista sa stanicom spikea koja je bliza desnom rubu
	//ako se barem jedno presjeciste nalazi unutar trokuta i unutar playera - kolizija
	@Override
	public boolean contains(Vector2D p) {
		double yp = p.getY();
		double sx = this.getCurrentPosition().getX();
		double sy = this.getCurrentPosition().getY();
		double a = this.getHeight();

		double x1 = sx + sx/(-2.0*a)*(yp-sy);
		double x2 =(yp - sy)*(sx-2.0*a)/(-2.0*a)+sx+a;

		return (yp>=sy-a && yp<=sy)&&
                ((x1>=p.getX() && x1<=p.getX()+a)
                || (x2>=p.getX() && x2<=p.getX()+a));
	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(this.getIcon(), this.getCurrentPosition().getX(), this.getCurrentPosition().getY());
	}

}
