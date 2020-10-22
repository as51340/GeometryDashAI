package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Point;

public class Floor extends Obstacle{
    public Floor(int positionX, int positionY) {
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
}
