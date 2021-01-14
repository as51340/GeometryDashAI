package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.sun.scenario.effect.impl.state.LinearConvolveKernel;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok.AIAlgorithm;
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

	private Map<Player, List<Obstacle>> closestObjects;

	private Object lockObject;

	private Set<GameObject> levelObjects;

	// reference to the algorithm
	private AIAlgorithm algorithm;

	private boolean levelPassed = false;

	/**
	 * @return the levelPassed
	 */
	public boolean isLevelPassed() {
		return levelPassed;
	}

	/**
	 * @param levelPassed the levelPassed to set
	 */
	public void setLevelPassed(boolean levelPassed) {
		this.levelPassed = levelPassed;
	}

	/**
	 * @return the algorithm
	 */
	public AIAlgorithm getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(AIAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

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
		closestObjects = new HashMap<Player, List<Obstacle>>();
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
	 * 
	 * @throws InterruptedException
	 */
	public boolean update() throws InterruptedException {
		Thread collisionThread = new Thread() {
			@Override
			public void run() {
				checkCollision2();
			}
		};
		collisionThread.setDaemon(true);
		collisionThread.setName("Collision");
		collisionThread.start();
		collisionThread.join();
		if (deaths == players.size()) {
			System.out.println("Svi su mrtvi");
			return false;
		}

		Player maxPlayer = getMaxPlayer();
		if (maxPlayer == null) { // on nebi trebao biti nikad null
			System.out.println("Svi mrtvi");
			return false;
		}

		checkPlayerCamera_X(maxPlayer);
		checkPlayerCamera_Y(maxPlayer);
		checkCameraGround_Y();

		if (renderer.render()) {
			levelPassed = true;
			return false; // toboze svi su mrtvi, ali zapravo nisu, nego je kraj levela
		}
		if (GameEngine.getInstance().getGameState() == GameState.AI_PLAYING_MODE
				|| GameEngine.getInstance().getGameState() == GameState.AI_TRAINING_MODE) {
			for (Player p : closestObjects.keySet()) {
//			System.out.println(closestObjects.get(p).size());
				algorithm.getPlayerNeuralNetworkMap().get(p).inputObstacles(closestObjects.get(p), p);
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
		List<Obstacle> obstacles = new ArrayList<Obstacle>();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player.isDead())
				continue;
			player.setTouchingGround(false);

			obstacles.clear();

			for (GameObject gameObject : gObjects) {
				if (gameObject instanceof Player)
					continue;

				double playerX = player.getCurrentPosition().getX();
				double playerY = player.getCurrentPosition().getY();
				double obstacleX = gameObject.getCurrentPosition().getX();

				if (obstacleX < playerX && !(gameObject instanceof Floor)) // prepreke prije igrača uvijek preskoci
					continue;

				if (obstacleX - playerX > 400) {
					if (player.getPlayingMode() == PlayingMode.HUMAN || obstacles.size() == 4) {
						continue;
					}
				}

				if (obstacles.size() < 4) {
					obstacles.add((Obstacle) gameObject);
				}

				if (obstacleX - playerX <= 100) {
					Obstacle obstacle = (Obstacle) gameObject;
					if (obstacle.playerIsOn(player)) {
						player.touchesGround();
						player.getCurrentPosition()
								.setY(gameObject.getCurrentPosition().getY() - GameConstants.iconHeight);
					}
					if (obstacle.checkCollisions(player)) {
						deaths++;
						player.setGoodness_value(
								gameObject.initialPosition.getX() - player.getCurrentPosition().getX());
						player.setDead(true);
					}
				}

			}

			closestObjects.put(player, obstacles);

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
