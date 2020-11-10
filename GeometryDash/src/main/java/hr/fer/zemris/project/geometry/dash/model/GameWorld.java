package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.project.geometry.dash.model.drawables.environment.*;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Manages all current objects on the scene. 
 * @author Andi Škrgat
 *
 */
public class GameWorld {
	
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
	 * Renderer
	 */
	private Renderer renderer;
	
	/**
	 * Currently selected level
	 */
	private Level currentLevel;
	
	
	/**
	 * @return the currentLevel
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * @param currentLevel the currentLevel to set
	 */
	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

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
	 * @return level manager
	 */
	public LevelManager getLevelManager() {
		return levelManager;
	}

	/**
	 * @return characters selector
	 */
	public CharactersSelector getCharactersSelector() {
		return charactersSelector;
	}

	/**
	 * @return get floor
	 */
	public GameObject getFloor() {
		return floor;
	}

	/**
	 * @return renderer
	 */
	public Renderer getRenderer() {
		return renderer;
	}

	/**
	 * @return player
	 */
	public GameObject getPlayer() {
		return player;
	}

	/**
	 * Initializes characters selector and creates scene for playing. Temporary for testing collisions and jumping on platforms
	 */
	public GameWorld() {
		charactersSelector = new CharactersSelector();
		levelManager = new LevelManager();
		createScene();
	}
	
	/**
	 * Creates temporary scene
	 */
	private void createScene() {
		player = new Player(new Vector2D(0,GameConstants.floorPosition_Y - GameConstants.iconHeight - 5), new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y));
		floor = new Floor(new Vector2D(0, GameConstants.floorPosition_Y + 50));
		Set<GameObject> levelObjects = new SerializeUtil().deserialize(ZipUtil.openZipFile(GameConstants.pathToLevelsFolder, "StereoMadness"));
		levelObjects.add(player);
		levelObjects.add(floor);
		this.currentLevel = levelManager.addLevel("StereoMadness", levelObjects);
		renderer = new Renderer(levelObjects);
		((Floor) floor).setCamera(renderer.getCamera());
	}

	/**
	 * Checks for relations between camera, player and ground
	 */
	public void update() {
		checkPlayerGround();
		checkPlayerCamera_X();
		checkPlayerCamera_Y();
		checkCameraGround_Y();
		checkCollision();
		renderer.render();
	}

	private void checkCollision(){
		for(GameObject gameObject: currentLevel.getLevelData()) {
			if(gameObject instanceof Obstacle){
				if(((Obstacle) gameObject).checkCollisions((Player) player)){
					System.err.println("PLAYER JE MERTAV");
				}
			}
		}
	}
	
	/**
	 * Camera's final y position. Tweak values!!
	 */
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
		double playerPos_Y = player.getCurrentPosition().getY();
		double cameraPos_Y = renderer.getCamera().getPosition().getY();
		if(playerPos_Y - cameraPos_Y > GameConstants.playerPosition_Y) {
			renderer.getCamera().getPosition().setY(playerPos_Y- GameConstants.playerPosition_Y);
		}
	}
	
	/**
	 * Distance player to camera - x coordinate
	 */
	private void checkPlayerCamera_X() {
		double playerPos_X = player.getCurrentPosition().getX();
		double cameraPos_X = renderer.getCamera().getPosition().getX();
		if(playerPos_X - cameraPos_X > GameConstants.playerPosition_X) {
			renderer.getCamera().getPosition().setX(playerPos_X - GameConstants.playerPosition_X);
		}
	}
	
	/**
	 * Checks relation between player and ground
	 */
	private void checkPlayerGround() {
		double playerPos_Y = player.getCurrentPosition().getY();
		double floorPos_Y = floor.getCurrentPosition().getY();
		if(playerPos_Y + GameConstants.playerGroundOffset_Y > floorPos_Y ) {
			((Player) player).touchesGround();
			player.getCurrentPosition().setY(floorPos_Y - GameConstants.playerGroundOffset_Y);
			((Player) player).setTouchingGround(true);
		}

		//prolazi sve gameObjects na levelu i ako je neki od njih blok ili platforma te ako je player na njemu kaze
		//playeru da smije skociti
		for(GameObject gameObject: currentLevel.getLevelData()) {
			if(gameObject instanceof Block){
				if(((Block) gameObject).playerIsOn((Player) player)){
					((Player) player).touchesGround();
					player.getCurrentPosition().setY(gameObject.getCurrentPosition().getY()-GameConstants.iconHeight);
				}
			} else if(gameObject instanceof Platform){
				if(((Platform) gameObject).playerIsOn((Player) player)){
					((Player) player).touchesGround();
					player.getCurrentPosition().setY(gameObject.getCurrentPosition().getY()-GameConstants.iconHeight);
				}
			}
		}
	}
}
