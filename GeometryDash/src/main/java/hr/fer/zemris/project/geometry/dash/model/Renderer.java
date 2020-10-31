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
 * Class whose job is to render objects on the scene in regards to camera
 * @author Andi Škrgat

 */
public class Renderer {
	
	/**
	 * Camera
	 */
	private Camera camera;
	
	/**
	 * Game objects
	 */
	private List<GameObject> gameObjects;
	
	/**
	 * Graphics context
	 */
	private GraphicsContext graphicsContext;
	
	/**
	 * Gets game objects and inits camera
	 * @param gameObjects game objects
	 */
	public Renderer(List<GameObject> gameObjects) {
		this.camera = new Camera();
		this.gameObjects = gameObjects;
	}
	
	/**
	 * @return camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Sets camera
	 * @param camera camera
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	/**
	 * @return game objects
	 */
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	/**
	 * Set game objects
	 * @param gameObjects
	 */
	public void setGameObjects(List<GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}
	
	/**
	 * Sets tool for drawing objects
	 * @param graphicsContext graphics context
	 */
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
		graphicsContext.clearRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT); //clear screen from last drawing
		for(GameObject gameObject: gameObjects) {
//			gameObject.update(graphicsContext, this.camera.getPosition());
			gameObject.getCurrentPosition().setX(gameObject.getCurrentPosition().getX() - camera.getPosition().getX());
			gameObject.getCurrentPosition().setY(gameObject.getCurrentPosition().getY() - camera.getPosition().getY());
			gameObject.draw(graphicsContext);
		}
	}

}
