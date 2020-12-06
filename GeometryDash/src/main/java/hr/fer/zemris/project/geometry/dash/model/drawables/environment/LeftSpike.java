package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//handle width and height
public class LeftSpike extends Obstacle {
    @Override
    public Vector2D getCenterPosition() {
        return getCurrentPosition().translated(new Vector2D(getWidth()/2.0, getHeight()/2.0));
    }

    //note: position here is lower left corner!
    public LeftSpike(Vector2D position, String uriIcon) {
        setCurrentPosition(position);
        this.setWidth(GameConstants.iconHeight); //visina trokuta
        this.setHeight((int) (GameConstants.iconHeight * Math.sqrt(3) / 2)); // baza trokuta
        setIconPath(uriIcon);
        setIcon(Utils.loadIcon(uriIcon, GameConstants.iconWidth, GameConstants.iconHeight));
        setName("LeftSpike");
    }

    /**
     * Constructor that accepts all {@linkplain GameObject}'s parameters
     * @param name object name
     * @param currentPosition current position
     * @param height height
     * @param width width
     * @param iconPath path to icon
     */
    public LeftSpike(String name, Vector2D currentPosition, int height, int width, String iconPath) {
        setName(name);
        setCurrentPosition(currentPosition);
        setHeight(height);
        setWidth(width);
        setIconPath(iconPath);
        setIcon(Utils.loadIcon(iconPath, GameConstants.iconWidth, GameConstants.iconHeight));
    }

    //daje se donji lijevi kut playera
    @Override
    public boolean checkCollisions(Player player) {
        Vector2D centerDiff = this.getCenterPosition().translated(player.getCenterPosition().reversed());
        double xDiff = centerDiff.getX();   // ako je xDiff pozitivan, player se nalazi ~lijevo od blocka
        double yDiff = centerDiff.getY();   // ako je yDiff pozitivan, player se nalazi ~iznad blocka

        return  (((Math.abs(xDiff) + (2.5 * Math.abs(yDiff))) <= getHeight() * 1.5) && Math.abs(xDiff) <= getHeight()
                && Math.abs(yDiff) <= getHeight());


//		return this.contains(player.getCurrentPosition().translated(new Vector2D(0, getHeight())));
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
        double a = this.getHeight();
        double sx = this.getCurrentPosition().getX();
        double sy = this.getCurrentPosition().getY()+a;

        double x1 = sx + sy/2 - (yp)/2;
        double x2 = sx + a + (yp-sy)/2;

        return (yp>=sy-a && yp<=sy)&&
                ((x1>=p.getX() && x1<=p.getX()+a) || (x2>=p.getX() && x2<=p.getX()+a));
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(this.getIcon(), this.getCurrentPosition().getX(), this.getCurrentPosition().getY());
    }

    @Override
    public GameObject copy() {
        return new Spike(getCurrentPosition().copy(), new String(getIconPath()));
    }

}
