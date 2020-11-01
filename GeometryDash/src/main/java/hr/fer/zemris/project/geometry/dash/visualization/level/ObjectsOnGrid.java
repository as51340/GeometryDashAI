package hr.fer.zemris.project.geometry.dash.visualization.level;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.Drawable;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ObjectsOnGrid implements Drawable{

	private Map<Image, Vector2D> objPosition;
	
	private Camera camera;
	
	public ObjectsOnGrid(Camera camera) {
		objPosition = new HashMap<Image, Vector2D>();
		this.camera = camera;
	}
	
	public void addMapping(Image imageEntry, Vector2D position) {
		objPosition.put(imageEntry, position);
	}
	
	
	@Override
	public void draw(GraphicsContext graphicsContext) {
		for(Image image: objPosition.keySet()) {
			Vector2D position = objPosition.get(image);
			graphicsContext.drawImage(image, position.getX() - camera.getPosition().getX(), position.getY() - camera.getPosition().getY());
		}
	}

	
	
}
