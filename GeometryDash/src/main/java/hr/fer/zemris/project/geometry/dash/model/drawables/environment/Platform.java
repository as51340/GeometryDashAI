package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Platform extends Obstacle{
	private Image image;
    public Platform(Vector2D position, int width, String image) {
        setCurrentPosition(position);
        setHeight(GameConstants.iconHeight/2);
        setWidth(width);
        this.image = new Image(image, getWidth(), getHeight(), false, false);
    }


    public Image getImage() {
    	return this.image;
    }


    //provjerava da li se playerov gornje lijevi ili desni kut nalazi "u" platformi
    //tj da li ju je lupio od dole
    @Override
    public boolean checkCollisions(Player player) {
        Vector2D playerUL = player.getCurrentPosition();
        Vector2D playerUR = player.getCurrentPosition().translated(new Vector2D(player.getWidth(), 0));
        return contains(playerUL) || contains(playerUR);
    }

    //provjerava je li tocka "u"platformi
    @Override
    public boolean contains(Vector2D p) {
        return (p.getX() >= getCurrentPosition().getX() && p.getX() <= getCurrentPosition().getX()+ getWidth()
        		&& p.getY() >= getCurrentPosition().getY() && p.getY() <= getCurrentPosition().getY()+ getHeight());
    }

    //provjerava je li player na platformi
    //TODO provjeriti je li radi ispravno
    public boolean playerIsOn(Player player){
        Vector2D playerDL = new Vector2D(player.getCurrentPosition().getX(), player.getCurrentPosition().getY() + player.getHeight());
        Vector2D playerDR = new Vector2D(player.getCurrentPosition().getX() + player.getHeight(), player.getCurrentPosition().getY() + player.getHeight());
        Vector2D platformUL = this.getCurrentPosition();
        Vector2D platformUR = this.getCurrentPosition().translated(new Vector2D(getWidth(),0));

        return playerDL.getY() == this.getCurrentPosition().getY()
                && ((playerDR.getX() > platformUL.getX()
                && playerDR.getX() < platformUR.getX())
                || (playerDL.getX() > platformUL.getX()
                && playerDL.getX() < platformUR.getX()));
    }

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(this.image,  getCurrentPosition().getX(), getCurrentPosition().getY());
	}
    
//	@Override
//	public void update(GraphicsContext graphics, Vector2D cameraPosition) {
//		graphics.drawImage(this.getImage(),getCurrentPosition().getX(), getCurrentPosition().getY());
//		getCurrentPosition().translate(new Vector2D(-GameConstants.timeBetweenUpdates * 50f, 0));
//		
//	}
}
