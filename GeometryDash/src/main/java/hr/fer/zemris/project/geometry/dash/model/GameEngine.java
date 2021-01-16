package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.serialization.GsonFactory;
import hr.fer.zemris.project.geometry.dash.model.serialization.SerializationOfObjects;
import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok.AIAlgorithm;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Floor;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.hash.HashUtil;
import hr.fer.zemris.project.geometry.dash.model.io.FileIO;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.listeners.GameStateListener;
import hr.fer.zemris.project.geometry.dash.model.listeners.UserListener;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.Settings;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import hr.fer.zemris.project.geometry.dash.model.settings.music.SoundSystem;
import hr.fer.zemris.project.geometry.dash.visualization.level.LevelEditor;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Engine that will be used in implementation of game <strong>Geometry
 * dash</strong> It defines only properties that are connected directly with
 * running game, it doesn't have any specific connection with geometry dash, so
 * it means on this "platform" can also be other games played
 * 
 * @author Andi Škrgat
 *
 */
public class GameEngine implements SoundSystem {

	private static final GameEngine GAME_ENGINE = new GameEngine(120, "GeometryDashAI", GameConstants.WIDTH,
			GameConstants.HEIGHT);
	
	/**
	 * Frames per second, default value is 60
	 */
	private int fps;

	/**
	 * Game's title
	 */
	private String title;

	/**
	 * Maybe here, maybe somewhere else
	 */
	private int width;

	/**
	 * Maybe here, maybe somewhere else
	 */
	private int height;

	/**
	 * Reference to game loop
	 */
	private Timeline gameLoop;

	/**
	 * Game world
	 */
	private GameWorld gameWorld = null;

	/**
	 * Reference to the level editor
	 */
	private LevelEditor levelEditor;

	/**
	 * Level manager
	 */
	private LevelManager levelManager;

	/**
	 * {@linkplain Settings}
	 */
	private Settings settings;

	/**
	 * Game state listener
	 */
	private GameStateListener gameStateListener;

	/**
	 * Game state
	 */
	private GameState gameState;

	/**
	 * Reference to the current user session
	 */
	private Session session;

	/**
	 * User listener
	 */
	private UserListener userListener;

	/**
	 * CharacterSelector for when user is not logged in
	 */
	private CharactersSelector defaultSelector;

	/*
	 * Time when gameLoop started
	 */
	private long startTime;
	
	/**
	 * Always returns same instance of game engine
	 * 
	 * @return
	 */
	public static GameEngine getInstance() {
		return GAME_ENGINE;
	}

	/**
	 * Basic constructor that sets game's title Creates game loop and event handler
	 * 
	 * @param title Game's title
	 */
	private GameEngine(int fps, String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = fps;
		settings = new Settings();
		// gameWorld = new GameWorld(); // for now list of obstacles is empty, not focus
		// on that currently
		levelEditor = new LevelEditor();
		gameStateListener = new DefaultGameStateListener();
		levelManager = new LevelManager();
		userListener = new UserListenerImpl();
		defaultSelector = new CharactersSelector();
		createGameLoop();
		// gameWorld.setCharacterSelector(defaultSelector);
	}

	/**
	 * @return the gameWorld
	 */
	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public void setGameWorld() {
		if (gameWorld == null) {
			gameWorld = new GameWorld();
			this.defaultSelector = session == null ? defaultSelector : session.getSelector();
		}
	}

	/**
	 * @return the fps
	 */
	public int getFps() {
		return fps;
	}

	/**
	 * @param fps the fps to set
	 */
	public void setFps(int fps) {
		this.fps = fps;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @param gameState the gameState to set
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * @return the userListener
	 */
	public UserListener getUserListener() {
		return userListener;
	}

	/**
	 * @param defaultSelector the defaultSelector to set
	 */
	public void setDefaultSelector(CharactersSelector defaultSelector) {
		this.defaultSelector = defaultSelector;
	}

	/**
	 * Starts game loop
	 */
	public void start() {
		if (gameLoop.getStatus() != Status.RUNNING) {
			this.startTime = System.currentTimeMillis();
//			System.out.println("Startam");
			gameLoop.play();
		}
	}

	/**
	 * Stops game loop
	 */
	public void stop() {
		if (gameLoop.getStatus() != Status.STOPPED) {
			gameLoop.stop();
		}
	}

	/**
	 * Resets game world
	 */
	public void reset() {
			gameWorld.setDeaths(0);
			Camera newCamera = new Camera();//getGameWorld().getRenderer().getCamera();
			getGameWorld().getRenderer().setCamera(newCamera);
			((Floor) getGameWorld().getFloor()).setCamera(newCamera); 
		
			getGameWorld().getRenderer().getGameObjects().forEach(o -> {
				o.setCurrentPosition(o.initialPosition.copy());
				if (o instanceof Player) {
					((Player) o).setJumpIntent(false);
					((Player) o).setTouchingGround(false);
//					((Player) o).setGoodness_value(0);
					((Player) o).setDead(false);
					((Player) o).setRotation(0);
					((Player) o).setSpeed(new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y));
				}
			});
	}
	/**
	 * Updates game world or level editor
	 * 
	 * @return {@linkplain KeyFrame}
	 */
	private KeyFrame createKeyFrame() {
		Duration frameTime = Duration.millis(1000.0 / getFps()); // for 60 FPS and that is usually standard
		Vector2D camDir = new Vector2D(1, 0);
		camDir.scale(GameConstants.timeBetweenUpdates * 50f);
		// time between update will be approx. 16.67ms, for 10ms we have to provide 100
		// fps as value
		return new KeyFrame(frameTime, event -> {
			if (gameState == GameState.NORMAL_MODE_PLAYING || gameState == GameState.AI_PLAYING_MODE || gameState == GameState.AI_TRAINING_MODE) {				
				try {
					if (!gameWorld.update()) {
						try {
							stop();
							double time = System.currentTimeMillis() - this.startTime;
							this.levelManager.getCurrentLevel().setTotalAttempts();
							gameWorld.getGameWorldListener().instanceFinished(time);
							levelManager.getCurrentLevel().resetTotalJumps();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (gameState == GameState.LEVEL_EDITOR_MODE) {
				Thread thread = new Thread(() -> {
					levelEditor.update();
				});
				thread.setDaemon(true);
				thread.start();
				levelEditor.draw();
			}
		});
	}

	/**
	 * Initializes {@linkplain Timeline} object for looping through game
	 */
	private void createGameLoop() {
		gameLoop = new Timeline();
		gameLoop.setCycleCount(Animation.INDEFINITE);
		gameLoop.getKeyFrames().add(createKeyFrame());
	}

	/**
	 * @return the levelEditor
	 */
	public LevelEditor getLevelEditor() {
		return levelEditor;
	}

	/**
	 * @return the gameStateListener
	 */
	public GameStateListener getGameStateListener() {
		return gameStateListener;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Initializes stage from game-engine data
	 * 
	 * @param stage {@linkplain Stage}
	 */
	public void createStageFromData(Stage stage) {
		stage.setTitle(this.title);
		stage.setWidth(this.width);
		stage.setHeight(this.height);
	}

	@Override
	public void playKillSound() {
		// TODO
	}

	/**
	 * @return the levelManager
	 */
	public LevelManager getLevelManager() {
		return levelManager;
	}

	/**
	 * @return the defaultSelector
	 */
	public CharactersSelector getDefaultSelector() {
		return defaultSelector;
	}

	/**
	 * Implementation of {@linkplain GameStateListener}
	 * 
	 * @author Andi �krgat
	 */
	public class DefaultGameStateListener implements GameStateListener {

		@Override
		public void levelEditorModeEntered(GraphicsContext graphicsContext) {
			levelEditor.setGraphicsContext(graphicsContext);
			GeometryDash.getStage().setResizable(false);
			gameState = GameState.LEVEL_EDITOR_MODE;
			start();
		}

		@Override
		public void levelEditorModeExited() {
			gameState = null;
			stop();
			GeometryDash.getStage().setResizable(true);
		}

		@Override
		public void normalModePlayingStarted() {
			gameState = GameState.NORMAL_MODE_PLAYING;
			start();
		}

		@Override
		public void normalModePlayingExited() {
			stop();
			gameWorld = null;
			gameState = null;
		}

		@Override
		public void AITrainingModePlayingStarted() {
			gameState = GameState.AI_TRAINING_MODE;
			start();
		}

		@Override
		public void AITrainingModePlayingExited() {
			stop();
			gameState = null;
			gameWorld = null;
		}

		@Override
		public void AIPlayingModeStarted() {
			gameState = GameState.AI_PLAYING_MODE;
			start();
		}

		@Override
		public void AIPlayingModeExited() {
			stop();
			gameWorld = null;
			gameState = null;
		}
	}

	/**
	 * Implementation of {@linkplain UserListener} interface
	 * 
	 * @author Andi Škrgat
	 *
	 */
	class UserListenerImpl implements UserListener {

		/**
		 * Serialization class
		 */
		private SerializationOfObjects serObject = new SerializationOfObjects(GsonFactory.createUserGson());

		@Override
		public void userStartedPlaying() {
			if (session != null) {
				session.getStats().setTotalAttempts();
				// here you can check than if user achieved something now, here you obtain that
				// data structure you return from stats
				// ucitaj novu scenu na kojoj ce mu se prikazat da je nesto osvojio
			}
		}

		@Override
		public void userRatedLevel(String levelName, int rating) {
			// store in DB that someone liked this level, rating
		}

		@Override
		public void userFinishedLevel(String levelName) {
			if (session != null) {
				session.getStats().setCompletedLevels(); // same here
			}
		}

		@Override
		public void register(String firstName, String lastName, String username, String password) {
			session = new Session(firstName, lastName, username, password);
			String hashedPassword = HashUtil.hashContent(password);
			session.getAccount().setPassword(hashedPassword);
			String fileName = GameConstants.pathToUsersFolder + "/" + session.getAccount().getUsername() + ".json";
			FileIO.createJsonFile(fileName, serObject.serialize(session));
		}

		@Override
		public boolean login(String username, String password) {
			String fileName = GameConstants.pathToUsersFolder + "/" + username + ".json";
			String json = FileIO.readFromJsonFile(fileName);
			if (json == null) {
				System.out.println("Json je null");
				return false;
			}
			Session newSession = serObject.deserializeUser(json);
			String hashedPassword = HashUtil.hashContent(password);
			if (newSession.getAccount().getPassword().equals(hashedPassword) == true) {
				session = newSession;
				return true;
			} else {
				System.out.println("Wrong password");
				return false;
			}
		}

		@Override
		public void logout() {
			String fileName = GameConstants.pathToUsersFolder + "/" + session.getAccount().getUsername() + ".json";
			FileIO.createJsonFile(fileName, serObject.serialize(session));
			session = null;
		}

		@Override
		public void playerJumped() {
			if (session != null) {
				session.getStats().setTotalJumps(); // you have to check here also
			}
		}

	}
}
