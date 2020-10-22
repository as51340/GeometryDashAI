package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Point;

public class Platform extends Obstacle{
    public Platform(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void drawTheirPosition() {

    }

    @Override
    public boolean checkCollisions() {
        return false;
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }
    //uska platforma, moze se ici po njoj ali ne kroz nju od dolje?
}
