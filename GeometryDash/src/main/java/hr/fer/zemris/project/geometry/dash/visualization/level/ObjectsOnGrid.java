package hr.fer.zemris.project.geometry.dash.visualization.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.Drawable;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;

public class ObjectsOnGrid implements Drawable{

	private List<GameObject> listGameObjects;
	
	private Camera camera;
	
	public ObjectsOnGrid(Camera camera) {
		listGameObjects = new ArrayList<GameObject>();
		this.camera = camera;
	}
	
	public void addGameObject(GameObject gameObject ) {
		listGameObjects.add(gameObject);
	}
	
	
	@Override
	public void draw(GraphicsContext graphicsContext) {
		for(GameObject gameObject: listGameObjects) {
			graphicsContext.drawImage(gameObject.getIcon(),  gameObject.getCurrentPosition().getX() - camera.getPosition().getX(),
					gameObject.getCurrentPosition().getY() - camera.getPosition().getY());
		}
	}

	
	
}
