package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Block;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Floor;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Obstacle;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Platform;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
	 * We need to keep reference on the player for camera moving
	 */
	private GameObject player;
	
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
		createScene();
	}
	
	private void createScene() {
		gameObjects = new ArrayList<>();
		player = new Player(new Vector2D(GameConstants.playerPosition_X, GameConstants.playerPosition_Y));
		// isprobavao postavljanje blokova i platformi
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+5*GameConstants.iconHeight, GameConstants.floorPosition_Y-GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+5*GameConstants.iconHeight, GameConstants.floorPosition_Y-2*GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+5*GameConstants.iconHeight, GameConstants.floorPosition_Y-3*GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+4*GameConstants.iconHeight, GameConstants.floorPosition_Y-GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+4*GameConstants.iconHeight, GameConstants.floorPosition_Y-2*GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+3*GameConstants.iconHeight, GameConstants.floorPosition_Y-GameConstants.iconHeight), GameConstants.blockImage));	
		gameObjects.add(new Platform(new Vector2D(GameConstants.playerPosition_X+10*GameConstants.iconHeight, GameConstants.floorPosition_Y-5*GameConstants.iconHeight), GameConstants.iconWidth * 5, GameConstants.platformImage));
		GameObject floor = new Floor(new Vector2D(0, GameConstants.floorPosition_Y));
		gameObjects.add(player);
		gameObjects.add(floor);
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
		graphics.clearRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT); // za ocistit ekran
		if(((Player) player).getPosition().getX() - camera.getPosition().getX() > GameConstants.playerPosition_X) {
			camera.getPosition().setPos(((Player) player).getPosition().getX() - GameConstants.playerPosition_X, camera.getPosition().getY());
		}
		//ovo osigurava da su player i kamera uvijek jednako udaljeni
		if(((Player) player).getPosition().getY() - camera.getPosition().getY() > GameConstants.playerPosition_Y) {
			camera.getPosition().setPos(camera.getPosition().getX(), ((Player) player).getPosition().getY() - GameConstants.playerPosition_Y);
		}
		// nakon treceg skoka
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
