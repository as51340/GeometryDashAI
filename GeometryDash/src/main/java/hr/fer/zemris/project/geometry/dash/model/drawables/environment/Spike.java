package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Point;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;

public class Spike extends Obstacle {
    public Spike(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void drawTheirPosition() {

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
    public boolean contains(Point p) {
        return false;
    }


}
