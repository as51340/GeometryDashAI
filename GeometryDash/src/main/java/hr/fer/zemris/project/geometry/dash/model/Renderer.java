package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	private Set<GameObject> gameObjects;
	
	/**
	 * Graphics context
	 */
	private GraphicsContext graphicsContext;
	
	/**
	 * Gets game objects and inits camera
	 * @param gameObjects game objects
	 */
	public Renderer(Set<GameObject> gameObjects) {
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
	public Set<GameObject> getGameObjects() {
		return gameObjects;
	}

	/**
	 * Set game objects
	 * @param gameObjects
	 */
	public void setGameObjects(Set<GameObject> gameObjects) {
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
	 */
	public boolean render() {
		graphicsContext.clearRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT); //clear screen from last drawing
		boolean finished = true;
		for(GameObject gameObject: gameObjects) {
			if((gameObject instanceof Player) && ((Player) gameObject).isDead()) {
				gameObject.getCurrentPosition().setX(GameConstants.playerPosition_X);
			} else {
				gameObject.getCurrentPosition().setX(gameObject.getCurrentPosition().getX() - camera.getPosition().getX());
			}  	
			gameObject.getCurrentPosition().setY(gameObject.getCurrentPosition().getY() - camera.getPosition().getY());
			if (!(gameObject instanceof Player) && gameObject.getCurrentPosition().getX() + GameConstants.LEVEL_END_OFFSET > GameConstants.playerPosition_X) {
                finished = false;
          }
			gameObject.draw(graphicsContext); //sliding window
		}
		return finished;
	}

}
