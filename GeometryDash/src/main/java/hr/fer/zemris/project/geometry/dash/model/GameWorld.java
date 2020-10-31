package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Block;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Floor;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.GrassSpike;
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
	
	private List<GameObject> gameObjects;
	
	/**
	 * Reference to the {@linkplain LevelManager}
	 */
	private LevelManager levelManager;
	
	/**
	 * {@linkplain CharactersSelector}
	 */
	private CharactersSelector charactersSelector;
	
	public GameObject getPlayer() {
		return player;
	}

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
	 * Renderer
	 */
	private Renderer renderer;
	
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
		createScene();
	}
	
	private void createScene() {
		gameObjects = new ArrayList<>();
		GrassSpike travica = new GrassSpike(new Vector2D(500, GameConstants.playerPosition_Y));
//		 isprobavao postavljanje blokova i platformi
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+5*GameConstants.iconHeight, GameConstants.floorPosition_Y-GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+5*GameConstants.iconHeight, GameConstants.floorPosition_Y-2*GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+5*GameConstants.iconHeight, GameConstants.floorPosition_Y-3*GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+4*GameConstants.iconHeight, GameConstants.floorPosition_Y-GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+4*GameConstants.iconHeight, GameConstants.floorPosition_Y-2*GameConstants.iconHeight), GameConstants.blockImage));
		gameObjects.add(new Block(new Vector2D(GameConstants.playerPosition_X+3*GameConstants.iconHeight, GameConstants.floorPosition_Y-GameConstants.iconHeight), GameConstants.blockImage));	
		gameObjects.add(new Platform(new Vector2D(GameConstants.playerPosition_X+10*GameConstants.iconHeight, GameConstants.floorPosition_Y-2*GameConstants.iconHeight), GameConstants.iconWidth * 5, GameConstants.platformImage));
		player = new Player(new Vector2D(0, GameConstants.floorPosition_Y), new Vector2D(50, 0));
		floor = new Floor(new Vector2D(0, GameConstants.floorPosition_Y));
		gameObjects.add(player);
		gameObjects.add(floor);
		gameObjects.add(travica);
		renderer = new Renderer(gameObjects);
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
			if(gameObject instanceof Obstacle){
				if(((Obstacle) gameObject).checkCollisions((Player) player)){
					((Obstacle) gameObject).setWidth(GameConstants.iconHeight);

				}
			}
//			gameObject.update(graphics, renderer.getCamera().getPosition());
		}
	}
	
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public CharactersSelector getCharactersSelector() {
		return charactersSelector;
	}

	public GameObject getFloor() {
		return floor;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	private void checkCameraGround_Y() {
		double cameraPos_Y = renderer.getCamera().getPosition().getY();
		if(cameraPos_Y > GameConstants.cameraGroundOffset_Y ) {
			renderer.getCamera().getPosition().setY(GameConstants.cameraGroundOffset_Y);
		}
	}
	
	/**
	 * Distance player to camera - y coordinate
	 */
	private void checkPlayerCamera_Y() {
//		double playerPos_Y =((Player) player).getPosition().getY();
//		double cameraPos_Y = camera.getPosition().getY();
//		if(playerPos_Y - cameraPos_Y > GameConstants.cameraPlayerOffset_Y) {
//			camera.getPosition().setY(playerPos_Y- cameraPos_Y);
//		}
	}
	
	/**
	 * Distance player to camera - x coordinate
	 */
	private void checkPlayerCamera_X() {
		double playerPos_X = player.getCurrentPosition().getX();
		double cameraPos_X = renderer.getCamera().getPosition().getX();
		if(playerPos_X - cameraPos_X > GameConstants.playerPosition_X) {
			renderer.getCamera().getPosition().setX(playerPos_X - cameraPos_X);
		}
	}
	
	/**
	 * Checks relation between player and ground
	 */
	private void checkPlayerGround() {
		double playerPos_Y = player.getCurrentPosition().getY();
		double floorPos_Y = floor.getCurrentPosition().getY();
		if(playerPos_Y + GameConstants.playerGroundOffset_Y > floorPos_Y) {
			((Player) player).touchesGround();
			player.getCurrentPosition().setY(floorPos_Y - GameConstants.playerGroundOffset_Y);
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
