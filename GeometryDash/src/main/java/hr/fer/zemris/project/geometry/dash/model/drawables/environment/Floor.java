package hr.fer.zemris.project.geometry.dash.model.drawables.environment;

import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class Floor extends Obstacle{
    public Floor(int positionX, int positionY) {
        super(positionX, positionY);
    }


    @Override
    public boolean checkCollisions() {
        return false;
    }

    @Override
    public boolean contains(Vector2D p) {
        return false;
    }


	@Override
	public void update(GraphicsContext graphics, Vector2D cameraPosition) {
		// TODO Auto-generated method stub
		
	}

    
}
