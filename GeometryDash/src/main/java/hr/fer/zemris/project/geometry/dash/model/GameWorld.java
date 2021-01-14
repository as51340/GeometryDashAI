package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.sun.scenario.effect.impl.state.LinearConvolveKernel;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.*;
import hr.fer.zemris.project.geometry.dash.model.listeners.GameWorldListener;
import hr.fer.zemris.project.geometry.dash.model.listeners.PlayerListener;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import hr.fer.zemris.project.geometry.dash.visualization.PlayerDeathSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Manages all current objects on the scene.
 *
 * @author Andi Škrgat
 */
public class GameWorld {

	/**
	 * Reference to the {@linkplain GameWorldListener}
	 */
	private final GameWorldListener gameWorldListener;

	/**
	 * Graphics context
	 */
	private GraphicsContext graphics;

	/**
	 * Reference on the floor
	 */
	private GameObject floor;

	/**
	 * Renderer
	 */
	private Renderer renderer;

	/**
	 * List of all players
	 */
	private Set<Player> players;

	/**
	 * Number of death players
	 */
	private int deaths = 0;

	private List<GameObject> closestObjects;

	private Object lockObject;
	
	private Set<GameObject> levelObjects;

	/**
	 * @return the lockObject
	 */
	public Object getLockObject() {
		return lockObject;
	}

	/**
	 * @param lockObject the lockObject to set
	 */
	public void setLockObject(Object lockObject) {
		this.lockObject = lockObject;
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
	 * @return the deaths
	 */
	public int getDeaths() {
		return deaths;
	}

	/**
	 * @param deaths the deaths to set
	 */
	public void setDeaths(int deaths) {
		this.deaths = deaths;
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
	 * @return the gameWorldListener
	 */
	public GameWorldListener getGameWorldListener() {
		return gameWorldListener;
	}

	/**
	 * Initializes creates scene for playing. Temporary for testing collisions and
	 * jumping on platforms
	 */
	public GameWorld() {
		gameWorldListener = new GameWorldListenerImpl();
		players = new TreeSet<Player>(AIConstants.playerComparator);
		closestObjects = new ArrayList<GameObject>();
	}

	/**
	 * Adds player to the game world
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		if (players.contains(player))
			System.out.println("Već sadržava!");
		players.add(player);
	}

	/**
	 * @return the players
	 */
	public Set<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(Set<Player> players) {
		this.players = players;
		for (Player p : players) {
			renderer.addGameObject(p);
		}
	}

	/**
	 * Creates temporary scene
	 */
	public void createScene(String levelName) {
		for (Player player : players) {
			player.setIcon(GameConstants.pathToIcons
					+ GameEngine.getInstance().getDefaultSelector().getSelectedCharacter().getUri());
		}
		Set<GameObject> fromLevel = GameEngine.getInstance().getLevelManager().getLevelByName(levelName)
				.getGameObjects();
//		Set<GameObject> treeset = new TreeSet<GameObject>(AIConstants.obstaclesLevelComparator);
//		treeset.addAll(fromLevel);
//		this.levelObjects = treeset;
		this.levelObjects = fromLevel;
		GameEngine.getInstance().getLevelManager().startLevelWithName(levelName);
		floor = new Floor(new Vector2D(0, GameConstants.floorPosition_Y + GameConstants.levelToWorldOffset));
		levelObjects.add(floor);
		for (Player p : players) {
			levelObjects.add(p);
		}
//		levelObjects.add(players.iterator().next());
		renderer = new Renderer(levelObjects);
		((Floor) floor).setCamera(renderer.getCamera());
		
	}

	/**
	 * Checks for relations between camera, player and ground
	 */
	public boolean update() {
		checkCollision2();

		if (deaths == players.size()) {
			System.out.println("Svi su mrtvi");
			return false;
		}

		Player player = getMaxPlayer();
		if (player == null) { // on nebi trebao biti nikad null
			System.out.println("Svi mrtvi");
			return false;
		}

		checkPlayerCamera_X(player);
		checkPlayerCamera_Y(player);
		checkCameraGround_Y();

		if (renderer.render()) {
			int i = 0;
			for (Player p : players) { // ovo mozes izbaciti samo trosi ciklus
				if (p.isDead() == false)
					i++;
			}
			System.out.println("Zavrsilo ih je: " + i);
			try {
				gameWorldListener.instanceFinished(System.currentTimeMillis() / 1000);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * Camera's final y position. Tweak values!!
	 */
	private void checkCameraGround_Y() {
		double cameraPos_Y = renderer.getCamera().getPosition().getY();
		if (cameraPos_Y > GameConstants.cameraGroundOffset_Y) {
			renderer.getCamera().getPosition().setY(GameConstants.cameraGroundOffset_Y);
		}
	}

	/**
	 * Distance player to camera - y coordinate
	 */
	private void checkPlayerCamera_Y(Player player) {
		double playerPos_Y = player.getCurrentPosition().getY();
		double cameraPos_Y = renderer.getCamera().getPosition().getY();
		if (playerPos_Y - cameraPos_Y > GameConstants.playerPosition_Y) {
			renderer.getCamera().getPosition().setY(playerPos_Y - GameConstants.playerPosition_Y);
		}
	}

	/**
	 * Distance player to camera - x coordinate
	 */
	private void checkPlayerCamera_X(Player player) {
		double playerPos_X = player.getCurrentPosition().getX();
		double cameraPos_X = renderer.getCamera().getPosition().getX();
		if (playerPos_X - cameraPos_X > GameConstants.playerPosition_X) {
			renderer.getCamera().getPosition().setX(playerPos_X - GameConstants.playerPosition_X);
		}
	}

	/**
	 * @return maximum position of players for following
	 */
	private Player getMaxPlayer() {
		double max = -1;
		Player player = null;
		for (Player p : players) {
			if (p.isDead() == false && p.getCurrentPosition().getX() > max) {
				max = p.getCurrentPosition().getX();
				player = p;
			}
		}
		return player;
	}

	private void checkCollision2() {
		Iterator<Player> iterator = players.iterator();
		Set<GameObject> gObjects = GameEngine.getInstance().getLevelManager().getCurrentLevel().getLevelData();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player.isDead())
				continue;
			player.setTouchingGround(false);
			for (GameObject gameObject : gObjects) {
				if (gameObject instanceof Player)
					continue; // ne treba nam player ni floor
//				if(gameObject instanceof Floor) System.out.println("POD");

				double playerX = player.getCurrentPosition().getX();
				double playerY = player.getCurrentPosition().getY();
				double obstacleX = gameObject.getCurrentPosition().getX();

//				System.out.println("X pozicija playera " + playerX);
//				System.out.println("Y pozicija playera " + playerY);

				if (obstacleX - playerX > 400 && !(gameObject instanceof Floor)) //makni drugi uvjet
					continue; // uzmi prepreke u scopeu 400

				if (obstacleX - playerX <= 100 || gameObject instanceof Floor) { //makni drugi uvjet
					// svi bi trebali bit obstaclei
					Obstacle obstacle = (Obstacle) gameObject;
//					if(gameObject instanceof Floor) System.out.println("Provjeravam sa podom!");
					if (obstacle.playerIsOn(player)) {
						player.touchesGround();
						player.getCurrentPosition()
								.setY(gameObject.getCurrentPosition().getY() - GameConstants.iconHeight);
					} else {
						//System.out.println("Player nije na podu, sranje!");
					}
					if (obstacle.checkCollisions(player)) {
						deaths++;
						player.setGoodness_value(
								gameObject.initialPosition.getX() - player.getCurrentPosition().getX());
						player.setDead(true);
					}
				}

			}

		}

	}

	private void checkCollision1() {
		Iterator<Player> iterator = players.iterator();
		int i = 0;

		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player.isDead())
				continue;
			player.setTouchingGround(false);
			int j = 0;
			for (GameObject gameObject : GameEngine.getInstance().getLevelManager().getCurrentLevel().getLevelData()) {
				if (!(gameObject instanceof Player)
						&& (gameObject.getCurrentPosition().getX() - player.getCurrentPosition().getX() <= 400)) {

					if (!(gameObject instanceof Floor)
							&& gameObject.getCurrentPosition().getX() - player.getCurrentPosition().getX() >= 0) {
						if (i == 0 && j < AIConstants.numOfClosestObstacles) {
//								if(closestObjects != null) {
//									closestObjects.add(gameObject.copy());
//									j++;
//								} ovo je sranje koje zablokira sve

						}
					}
					if (gameObject.getCurrentPosition().getX() - player.getCurrentPosition().getX() <= 100) {
						if (gameObject instanceof Obstacle) {
							Obstacle obstacle = (Obstacle) gameObject;
							if (obstacle.playerIsOn(player)) {
								player.touchesGround();
								player.getCurrentPosition()
										.setY(gameObject.getCurrentPosition().getY() - GameConstants.iconHeight);
							}
							if (!player.isDead() && obstacle.checkCollisions(player)) {
								if (((Obstacle) gameObject).checkCollisions(player)) {
									deaths++;
									player.setGoodness_value(
											gameObject.initialPosition.getX() - player.getCurrentPosition().getX());
//										System.out.println("postavljam mrtvaca!");
									player.setDead(true);
								}
							}
						}
					}
				}
			}
			i++;
		}
	}

	/**
	 * Implementation of {@linkplain GameWorldListener}
	 */
	class GameWorldListenerImpl implements GameWorldListener {

		@Override
		public void instanceFinished(double time) throws IOException {
			GameEngine.getInstance().stop();
			GameEngine.getInstance().reset();
			// fuš, premjesti to ako je level gotov
			deaths = 300;
//			System.out.println(Thread.currentThread().getName());
			if (GameEngine.getInstance().getGameState() == GameState.AI_PLAYING_MODE
					|| GameEngine.getInstance().getGameState() == GameState.AI_TRAINING_MODE) {
				synchronized (lockObject) {
					lockObject.notifyAll(); // obavijesti da smo gotovi
				}
			} else {
				if (GameEngine.getInstance().getSettings().getOptions().isAutoRetry()) { // ako je auto retry onda sve
																							// kreni
					// ispocetka
					GameEngine.getInstance().start();
				} else {
					FXMLLoader loader = new FXMLLoader(
							getClass().getResource(GameConstants.pathToVisualization + "PlayerDeathScene.fxml"));
					loader.load();
					PlayerDeathSceneController controller = loader.<PlayerDeathSceneController>getController();
					Stage stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst()
							.orElse(null);
					Pane rootPane = stage == null ? null : (Pane) stage.getScene().lookup("#rootPane");
					controller.setPreviousSceneRoot(rootPane);
					controller.showInformation(
							GameEngine.getInstance().getLevelManager().getCurrentLevel().getLevelName(),
							Long.toString(
									GameEngine.getInstance().getLevelManager().getCurrentLevel().getTotalAttempts()),
							GameEngine.getInstance().getLevelManager().getCurrentLevel()
									.getLevelPercentagePassNormalMode(),
							Long.toString(GameEngine.getInstance().getLevelManager().getCurrentLevel().getTotalJumps()),
							time);
				}
			}
		}

	}

}
