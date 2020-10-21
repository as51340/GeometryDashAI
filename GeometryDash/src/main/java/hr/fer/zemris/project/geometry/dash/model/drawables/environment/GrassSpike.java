package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Point;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;

public class GrassSpike extends Obstacle{
    public GrassSpike(int positionX, int positionY) {
        super(positionX, positionY);
        this.setHeight(GameConstants.iconHeight);
        this.setWidth(2*GameConstants.iconWidth);
        //this.setIcon();
    }

    @Override
    public void drawTheirPosition() {
        //from positionX positionY to the right for width and down height
    }

    @Override
    public boolean checkCollisions() {
        Point obstacleUL = new Point(getPositionX(), getPositionY());
        Point obstacleDR = new Point(getPositionX()+getWidth(), getPositionY()+getHeight());
        //Point player = Player.getPosition(); //TODO Player.getPosition()
        Point playerDL = new Point(0, 0); //TODO fix position
        Point playerDR = new Point(0, 0); //TODO fix position

        if(this.contains(playerDR) || this.contains(playerDL)){
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Point p){
        if(p.getY() < (this.getPositionY()+getHeight()) && p.getY() > this.getPositionY()){
            return p.getX() < (this.getPositionX() + getWidth()) && p.getX() > this.getPositionX();
        }
        return false;
    }
}
