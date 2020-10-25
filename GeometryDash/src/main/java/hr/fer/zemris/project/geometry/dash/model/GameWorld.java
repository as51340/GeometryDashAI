package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Floor;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Obstacle;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
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
	 * We need to keep reference on the player for camera moving
	 */
	private GameObject player;
	
	/**
	 * Reference on the floor
	 */
	private GameObject floor;
	
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
		player = new Player(new Vector2D(GameConstants.playerPosition_X, 100), new Vector2D(30, 30));
		floor = new Floor(new Vector2D(0, GameConstants.floorPosition_Y));
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
		checkPlayerGround();
		checkPlayerCamera_X();
		checkPlayerCamera_Y();
//		checkCameraGround_Y();
		for(GameObject gameObject: gameObjects) {
			gameObject.update(graphics, camera.getPosition());
		}	
	}
	
	private void checkCameraGround_Y() {
		double cameraPos_Y = camera.getPosition().getY();
		if(cameraPos_Y > GameConstants.cameraGroundOffset_Y ) {
			camera.getPosition().setY(GameConstants.cameraGroundOffset_Y);
		}
	}
	
	/**
	 * Distance player to camera - y coordinate
	 */
	private void checkPlayerCamera_Y() {
		double playerPos_Y =((Player) player).getPosition().getY();
		double cameraPos_Y = camera.getPosition().getY();
		if(playerPos_Y - cameraPos_Y > GameConstants.cameraPlayerOffset_Y) {
			camera.getPosition().setY(playerPos_Y- cameraPos_Y);
		}
	}
	
	/**
	 * Distance player to camera - x coordinate
	 */
	private void checkPlayerCamera_X() {
		double playerPos_X =((Player) player).getPosition().getX();
		double cameraPos_X = camera.getPosition().getX();
		if(playerPos_X - cameraPos_X > GameConstants.playerPosition_X) {
			camera.getPosition().setX(playerPos_X - cameraPos_X);
		}
	}
	
	/**
	 * Checks relation between player and ground
	 */
	private void checkPlayerGround() {
		double playerPos_Y = ((Player) player).getPosition().getY();
		double floorPos_Y = ((Obstacle) floor).getPosition().getY();
		if(playerPos_Y + GameConstants.playerGroundOffset_Y > floorPos_Y) {
			((Player) player).getPosition().setY(floorPos_Y - GameConstants.playerGroundOffset_Y);
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
