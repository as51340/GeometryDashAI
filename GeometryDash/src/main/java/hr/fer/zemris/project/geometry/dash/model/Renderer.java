package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Block;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Floor;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.GrassSpike;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Obstacle;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Platform;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class whose job is to render objects on the scene in regards to camera
 * 
 * @author Andi Škrgat
 * 
 */
public class Renderer {

	/**
	 * Camera
	 */
	private Camera camera;

	/**
	 * Game objects
	 */
//	private Set<GameObject> gameObjects;
	private List<GameObject> gameObjects = null;

	/**
	 * Graphics context
	 */
	private GraphicsContext graphicsContext;

	/**
	 * Gets game objects and inits camera
	 * 
	 * @param gameObjects game objects
	 */
	public Renderer(List<GameObject> gameObjects) {
		this();
		this.gameObjects = gameObjects;
	}
	
	public Renderer() {
		this.camera = new Camera();
	}

	/**
	 * @return camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Sets camera
	 * 
	 * @param camera camera
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

//	/**
//	 * @return game objects
//	 */
//	public Set<GameObject> getGameObjects() {
//		return gameObjects;
//	}
//
//	/**
//	 * Set game objects
//	 * 
//	 * @param gameObjects
//	 */
//	public void setGameObjects(Set<GameObject> gameObjects) {
//		this.gameObjects = gameObjects;
//	}
//	
	
	public void clearObjects() {
		this.gameObjects.clear();
	}


	/**
	 * Sets tool for drawing objects
	 * 
	 * @param graphicsContext graphics context
	 */
	public void setGraphicsContext(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
	}

	/**
	 * @return the gameObjects
	 */
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	/**
	 * @param gameObjects the gameObjects to set
	 */
	public void setGameObjects(List<GameObject> gameObjects) {
		this.gameObjects.clear();
		this.gameObjects = gameObjects;
	}

	/**
	 * Adds new object on the scene
	 * 
	 * @param gameObject game object
	 */
	public void addGameObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}
	
	public List<Obstacle> getClosestObstacles(Player player) {
		return null;
	}

	/**
	 * Renders all objects on the scene
	 */
	public boolean render() {
		graphicsContext.clearRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT); // clear screen from last drawing
		boolean finished = true;
		for (GameObject gameObject : gameObjects) {
			if ((gameObject instanceof Player) && ((Player) gameObject).isDead()) {
				continue;
			} else {
				gameObject.getCurrentPosition()
						.setX(gameObject.getCurrentPosition().getX() - camera.getPosition().getX());

				gameObject.getCurrentPosition()
						.setY(gameObject.getCurrentPosition().getY() - camera.getPosition().getY());
				
				if (!(gameObject instanceof Player) && gameObject.getCurrentPosition().getX()
						+ GameConstants.LEVEL_END_OFFSET > GameConstants.playerPosition_X) {
					finished = false;
				}
					
				if(gameObject.getCurrentPosition().getX() >= -50 && gameObject.getCurrentPosition().getX() <= 1300) {
					gameObject.draw(graphicsContext); // sliding window
				}
			}
		}
		return finished;
	}

}
