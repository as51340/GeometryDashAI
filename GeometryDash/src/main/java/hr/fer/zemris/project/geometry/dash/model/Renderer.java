package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Block;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Floor;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.GrassSpike;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Platform;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class whose job is to render objects on the scene. It has reference to the {@linkplain Camera}
 * @author Andi Škrgat
 *
 */
public class Renderer {
	
	private Camera camera;
	
	private List<GameObject> gameObjects;
	
	private GraphicsContext graphicsContext;
	
	public Renderer(List<GameObject> gameObjects) {
		this.camera = new Camera();
		this.gameObjects = gameObjects;
	}
	
	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(List<GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}
	
	public void setGraphicsContext(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
	}

	/**
	 * Adds new object on the scene
	 * @param gameObject game object
	 */
	public void addGameObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}
	
	/**
	 * Renders all objects on the scene
	 * @param graphicsContext
	 */
	public void render() {
		for(GameObject gameObject: gameObjects) {
//			gameObject.update(graphicsContext, this.camera.getPosition());
			Vector2D gameObjectOldPosition = gameObject.getCurrentPosition();
			gameObject.getCurrentPosition().setX(gameObject.getCurrentPosition().getX() - camera.getPosition().getX());
			gameObject.getCurrentPosition().setY(gameObject.getCurrentPosition().getY() - camera.getPosition().getY());
			gameObject.draw(graphicsContext);
			gameObject.setCurrentPosition(gameObjectOldPosition);
		}
	}

}
