package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.atomic.AtomicInteger;

import com.sun.scenario.effect.impl.state.LinearConvolveKernel;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.AiPair;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok.AIAlgorithm;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.GeneticFunctionality;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Tree;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.TreeUtil;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.*;
import hr.fer.zemris.project.geometry.dash.model.listeners.GameWorldListener;
import hr.fer.zemris.project.geometry.dash.model.listeners.PlayerListener;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import hr.fer.zemris.project.geometry.dash.visualization.PlayerDeathSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

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
	 * Genetic programming player
	 */
	private AiPair<Tree> gpPlayer;

	/**
	 * Genetic or elman player
	 */
	private AiPair<NeuralNetwork> aiPlayer;

	/**
	 * Graphics context
	 */
	private GraphicsContext graphics;

	/**
	 * Reference on the floor
	 */
	private Floor floor;

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

	/**
	 * Closest objects
	 */
	private Map<Player, List<Obstacle>> closestObjects;

	/**
	 * Lock object
	 */
	private Object lockObject;

	/**
	 * Level objects
	 */
	private List<GameObject> levelObjects;

	/**
	 * From level objects
	 */
	private Set<GameObject> fromLevelObjects;

	/**
	 * reference to the regular and elman algorithm
	 */
	private AIAlgorithm algorithm = null;

	/**
	 * For genetic programming training
	 */
	private GeneticFunctionality gpAlgorithm = null;

	/**
	 * @return the gpAlgorithm
	 */
	public GeneticFunctionality getGpAlgorithm() {
		return gpAlgorithm;
	}

	/**
	 * @param gpAlgorithm the gpAlgorithm to set
	 */
	public void setGpAlgorithm(GeneticFunctionality gpAlgorithm) {
		this.gpAlgorithm = gpAlgorithm;
	}

	/**
	 * for ai
	 */
	private boolean unlockingCondition = false;

	/**
	 * for everything
	 */
	private boolean levelPassed = false;

	/**
	 * Position of last level obstacle
	 */
	private double lastPosition;

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
	 * @return the unlockingCondition
	 */
	public boolean isUnlockingCondition() {
		return unlockingCondition;
	}

	/**
	 * @param unlockingCondition the unlockingCondition to set
	 */
	public void setUnlockingCondition(boolean unlockingCondition) {
		this.unlockingCondition = unlockingCondition;
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
	 * @return the gpPlayer
	 */
	public AiPair<Tree> getGpPlayer() {
		return gpPlayer;
	}

	/**
	 * @param gpPlayer the gpPlayer to set
	 */
	public void setGpPlayer(AiPair<Tree> gpPlayer) {
		this.gpPlayer = gpPlayer;
	}

	/**
	 * @return the aiPlayer
	 */
	public AiPair<NeuralNetwork> getAiPlayer() {
		return aiPlayer;
	}

	/**
	 * @param aiPlayer the aiPlayer to set
	 */
	public void setAiPlayer(AiPair<NeuralNetwork> aiPlayer) {
		this.aiPlayer = aiPlayer;
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
		levelObjects = new ArrayList<GameObject>();
	}

	public void createAIScene() { // delete old players from renderer, add new
		Set<Player> newPlayers = null;
		if (algorithm != null) {
			newPlayers = algorithm.getPlayerNeuralNetworkMap().keySet();
		} else if (gpAlgorithm != null) {
			newPlayers = gpAlgorithm.getPopulation().keySet();
		}
		if (newPlayers.size() != players.size()) {
			throw new IllegalStateException("Not same number of players after new generation!");
		}
		Iterator<Player> itr = players.iterator();

		while (itr.hasNext()) {
			Player p = itr.next();
			renderer.getGameObjects().remove(p);
			itr.remove();
		}
		for (Player p : newPlayers) {
			addPlayer(p);
		}
		this.closestObjects.clear();
	}

	/**
	 * Adds player to the game world
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		player.setIcon(GameConstants.pathToIcons
				+ GameEngine.getInstance().getDefaultSelector().getSelectedCharacter().getUri());
		if (players.contains(player))
			System.out.println("Već sadržava!");
		players.add(player);
		renderer.addGameObject(player);
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
	 * Removes player
	 * 
	 * @param p
	 */
	public void removePlayer(Player p, Player newP) {
		System.out.println("Sadržava stari " + levelObjects.contains(p));
		this.players.remove(p);
		renderer.getGameObjects().remove(p);
	}

	/**
	 * @return the closestObjects
	 */
	public Map<Player, List<Obstacle>> getClosestObjects() {
		return closestObjects;
	}

	/**
	 * @param closestObjects the closestObjects to set
	 */
	public void setClosestObjects(Map<Player, List<Obstacle>> closestObjects) {
		this.closestObjects = closestObjects;
	}

	/**
	 * Creates temporary scene
	 */
	public void createScene(String levelName) {
		this.fromLevelObjects = GameEngine.getInstance().getLevelManager().getLevelByName(levelName).getGameObjects();
//		this.levelObjects = fromLevel;
//		this.levelObjects = new TreeSet<GameObject>(AIConstants.obstaclesLevelComparator);
		this.levelObjects.addAll(fromLevelObjects);
		Collections.sort(levelObjects, AIConstants.obstaclesLevelComparator);
		lastPosition = levelObjects.get(levelObjects.size() - 1).getInitialPosition().getX();
		GameEngine.getInstance().getLevelManager().startLevelWithName(levelName);
		floor = new Floor(new Vector2D(0, GameConstants.floorPosition_Y + GameConstants.levelToWorldOffset));
		this.levelObjects.add(floor);
		renderer = new Renderer(levelObjects);
		floor.setCamera(renderer.getCamera());

//
////			System.out.println("Uspješno dodan floor");
//		for (Player p : players) {
//			levelObjects.add(p);
//		}

//		for(GameObject go: this.levelObjects) {
//			System.out.println(go.getClass() + " " + go.getCurrentPosition().getX() + " " + go.getCurrentPosition().getY());
//		}
//		for(GameObject go: this.players) {
//			System.out.println(go.getClass() + " " + go.getCurrentPosition().getX() + " " + go.getCurrentPosition().getY());
//		}
//		GameEngine.getInstance().getGameStateListener().normalModePlayingStarted();

	}

	/**
	 * Checks for relations between camera, player and ground
	 * 
	 * @throws InterruptedException
	 */
	public boolean update() throws InterruptedException {
		checkCollision2();
		Player maxPlayer = getMaxPlayer();
		if (maxPlayer == null) { // on nebi trebao biti nikad null
			unlockingCondition = true;
			return false;
		}
		checkPlayerCamera_X(maxPlayer);
		checkPlayerCamera_Y(maxPlayer);
		checkCameraGround_Y();

		if (deaths == players.size()) {
			unlockingCondition = true;
			return false;
		}

		if (gpAlgorithm != null) { // training mode is, genetic programming mode
			//System.out.println("GP training");
			for (Player p : closestObjects.keySet()) {
				List<Obstacle> obst = closestObjects.get(p);
				if (gpAlgorithm.getPopulation().get(p) == null) {
					System.out.println("Player id " + p.getId()); // debug
				}
				gpAlgorithm.getPopulation().get(p).changeInputs(obst, p);
				double output = TreeUtil.calculateOutput(gpAlgorithm.getPopulation().get(p));
				// System.out.println(output);
				if (output >= 0.8) {
					p.jump();
				}
			}
		} else if (gpPlayer != null) { //gp playing mode
			//System.out.println("GP playing");
			Player p = gpPlayer.getPlayer();
			List<Obstacle> obst = closestObjects.get(p);
			Tree t = gpPlayer.getAiObject();
			t.changeInputs(obst, p);
			double output = TreeUtil.calculateOutput(t);
			if (output >= 0.8) {
				p.jump();
			}
		} else if (algorithm != null) {
			//System.out.println("AI training");//ai training mode
			for (Player p : closestObjects.keySet()) {
				List<Obstacle> obst = closestObjects.get(p);
				algorithm.getPlayerNeuralNetworkMap().get(p).inputObstacles(obst, p);
				double output = algorithm.getPlayerNeuralNetworkMap().get(p).getOutput().calculateOutput();
				if (output >= 0.5)
					p.jump();
			}
		} else if (aiPlayer != null) { // ai playing mode
			System.out.println("AI playing");
			Player player = aiPlayer.getPlayer();
			NeuralNetwork nn = aiPlayer.getAiObject();
			List<Obstacle> obst = closestObjects.get(player);
			nn.inputObstacles(obst, player);
			double output = nn.getOutput().calculateOutput();
			if (output >= 0.5)
				player.jump();
		}
		if (renderer.render()) {
			unlockingCondition = true;
			levelPassed = true;
			return false; // toboze svi su mrtvi, ali zapravo
							// nisu, nego je kraj levela
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
//			renderer.getCamera().getPosition().setX(0);
//			System.out.println(renderer.getCamera().getPosition().getX());
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
		List<Obstacle> obstacles = new ArrayList<Obstacle>();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player.isDead()) {
				if (player.getPlayingMode() != PlayingMode.HUMAN)
					closestObjects.remove(player);
				continue;
			}
			player.setTouchingGround(false);

			obstacles.clear();

			for (GameObject gameObject : levelObjects) {

				if (gameObject instanceof Player) {
					continue;
				}

				double playerX = player.getCurrentPosition().getX();

				double obstacleX = gameObject.getCurrentPosition().getX();

				if (obstacleX < playerX - 45 && !(gameObject instanceof Floor)) // prepreke prije igrača uvijek //
																				// preskoci
					continue;

				if (obstacleX - playerX > 45) {
					if (player.getPlayingMode() == PlayingMode.HUMAN || obstacles.size() == AIConstants.obstForAI) {
						continue;
					}
				}

				if (player.getPlayingMode() != PlayingMode.HUMAN && obstacles.size() < AIConstants.obstForAI) {
					obstacles.add((Obstacle) gameObject);
				}

//					if (obstacleX - playerX <= 100) {
				Obstacle obstacle = (Obstacle) gameObject;
				if (obstacle.playerIsOn(player)) {
					player.touchesGround();
					player.getCurrentPosition().setY(gameObject.getCurrentPosition().getY() - GameConstants.iconHeight);
				}

				if (!player.isDead() && obstacle.checkCollisions(player)) {
					deaths++;
					double value = gameObject.initialPosition.getX() - player.getCurrentPosition().getX();
					double percentage = gameObject.initialPosition.getX() / lastPosition;
					Level level = GameEngine.getInstance().getLevelManager().getCurrentLevel();
					level.setCurrentLevelPercentagePassNormalMode(percentage);
					if (percentage > level.getLevelPercentagePassNormalMode())
						level.setLevelPercentagePassNormalMode(percentage);
					player.setGoodness_value(value);
					player.setDead(true);
				}
//					}

			}
			if (player.getPlayingMode() != PlayingMode.HUMAN && !player.isDead()) {
				closestObjects.put(player, obstacles);
			}

		}

	}

	/**
	 * Implementation of {@linkplain GameWorldListener}
	 */
	class GameWorldListenerImpl implements GameWorldListener {

		@Override
		public void instanceFinished(double time) throws IOException, InterruptedException {
//			System.out.println("KRAJ jedne scene");
			GameEngine.getInstance().stop();
			int finished_deaths = deaths;
			GameEngine.getInstance().reset();

			if (GameEngine.getInstance().getGameState() == GameState.NORMAL_MODE_PLAYING
					|| GameEngine.getInstance().getGameState() == GameState.AI_PLAYING_MODE) {
				if (finished_deaths == players.size()) { // ako su svi mrtvi
					if (GameEngine.getInstance().getSettings().getOptions().isAutoRetry()) {
						GameEngine.getInstance().start();
					} else {
						openScene(GameEngine.getInstance().getLevelManager().getCurrentLevel().getLevelName(), time);
					}
				} else {
//					System.out.println("Number of deaths = " + finished_deaths);
					if (levelPassed) {
						levelPassed = false;
						Level level = GameEngine.getInstance().getLevelManager().getCurrentLevel();
						level.setCurrentLevelPercentagePassNormalMode(1);
						level.setLevelPercentagePassNormalMode(1);
						openScene("Level " + level.getLevelName() + " successfully finished!", time);
					} else {
						throw new IllegalStateException(
								"Not all death and level not finished but normal mode or AI playing mode!");
					}
				}
			} else if (GameEngine.getInstance().getGameState() == GameState.AI_TRAINING_MODE) {
				if (finished_deaths == players.size()) {
//					System.out.println("Deaths ai training " + finished_deaths);
					synchronized (lockObject) { // ovo bi trebalo stimat i za tree
						lockObject.notifyAll(); // obavijesti da smo gotovi
					}
				} else if (levelPassed) { // svim playerima postavi novi goodness value
//						levelPassed = false;
//					System.out.println("Tu smo!");
					for (Player p : players) {
						if (!p.isDead())
							p.setGoodness_value(lastPosition);
					}
					synchronized (lockObject) {
						lockObject.notifyAll(); // obavijesti da smo gotovi
					}
				} else {
					throw new IllegalStateException("Nije level passed?");
				}

			} else {
				throw new IllegalStateException("Unknown playing mode in game world!");
			}
		}

		private void openScene(String message, double time) throws IOException {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource(GameConstants.pathToVisualization + "PlayerDeathScene.fxml"));
			loader.load();
			PlayerDeathSceneController controller = loader.<PlayerDeathSceneController>getController();
			Stage stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
			Pane rootPane = stage == null ? null : (Pane) stage.getScene().lookup("#rootPane");
			controller.setPreviousSceneRoot(rootPane);

			controller.showInformation(message,
					Long.toString(GameEngine.getInstance().getLevelManager().getCurrentLevel().getTotalAttempts()),
					GameEngine.getInstance().getLevelManager().getCurrentLevel()
							.getCurrentLevelPercentagePassNormalMode(),
					Long.toString(GameEngine.getInstance().getLevelManager().getCurrentLevel().getTotalJumps()), time);
		}
	}

}
