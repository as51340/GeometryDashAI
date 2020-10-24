package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Obstacle;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * Manages all current objects on scene. It also encapsulates behaving of renderer. 
 * @author Andi Škrgat
 *
 */
public class GameWorld {
	
	/**
	 * Reference to the all existing obstacles on the scene(player + obstacles)
	 */
	private List<GameObject> gameObjects; 
	
	/**
	 * Reference to the {@linkplain LevelManager}
	 */
	private LevelManager levelManager;
	
	/**
	 * {@linkplain CharactersSelector}
	 */
	private CharactersSelector charactersSelector;
	
	/**
	 * Graphics context TODO documentation
	 */
	private GraphicsContext graphics;
	
	/**
	 * Reference to the camera
	 */
	private Camera camera;
	
	/**
	 * @return the graphics
	 */
	public GraphicsContext getGraphics() {
		return graphics;
	}

	/**
	 * @param graphics the graphics to set
	 */
	public void setGraphics(GraphicsContext graphics) {
		this.graphics = graphics;
	}

	/**
	 * @param obstacles ili ce ih primit u konstruktoru ili ce ih nekako citat iz level managera
	 */
	public GameWorld() {
		charactersSelector = new CharactersSelector();
		camera = new Camera();
		loadPlayerAndObstaclesOnTheScene();
	}
	
	private void loadPlayerAndObstaclesOnTheScene() {
		gameObjects = new ArrayList<>();
		GameObject player = new Player(new Vector2D(300, 400));
		gameObjects.add(player);
	}
		
	/**
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Updates GUI
	 */
	public void UpdateGUI() {
		for(GameObject gameObject: gameObjects) {
			gameObject.update(graphics, camera.getPosition());
			
		}
	}
	
	/**
	 * Checks if some object is out of the scene and deletes him if neccessary
	 */
	public void CleanObjectsFromScene() {
		
	}
	
	/**
	 * Adds game object to the list of game objects
	 * @param gameObject game object
	 */
	public void addGameObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}
	

}
