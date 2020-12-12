package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.serialization.GsonFactory;
import hr.fer.zemris.project.geometry.dash.model.serialization.SerializationOfObjects;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Floor;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.hash.HashUtil;
import hr.fer.zemris.project.geometry.dash.model.io.FileIO;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.listeners.GameStateListener;
import hr.fer.zemris.project.geometry.dash.model.listeners.UserListener;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.Settings;
import hr.fer.zemris.project.geometry.dash.model.settings.music.SoundSystem;
import hr.fer.zemris.project.geometry.dash.threads.DaemonicThreadFactory;
import hr.fer.zemris.project.geometry.dash.visualization.level.GridAttaching;
import hr.fer.zemris.project.geometry.dash.visualization.level.LevelEditor;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
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

	private static final GameEngine GAME_ENGINE = new GameEngine(60, "GeometryDashAI",
			GameConstants.WIDTH, GameConstants.HEIGHT);
	
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
	private GameWorld gameWorld;

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
	 * Time when gameLoop started
	 */
	private long startTime;
	
	/**
	 * Always returns same instance of game engine
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
		gameWorld = new GameWorld(); // for now list of obstacles is empty, not focus on that currently
		levelEditor = new LevelEditor();
		gameStateListener = new DefaultGameStateListener();
//		levelManager = new LevelManager();
		userListener = new UserListenerImpl();
		createGameLoop();
	}
	

//	/**
//	 * @return the levelManager
//	 */
//	public LevelManager getLevelManager() {
//		return levelManager;
//	}

	/**
	 * @return the gameWorld
	 */
	public GameWorld getGameWorld() {
		return gameWorld;
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
	 * @return the gameLoop
	 */
	public Timeline getGameLoop() {
		return gameLoop;
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
	 * Starts game loop
	 */
	public void start() {
		this.startTime = System.currentTimeMillis();
		gameLoop.play();
	}

	public void reset() {
		// TODO find how to completely reset a GameWorld
		Camera newCamera = getGameWorld().getRenderer().getCamera();
		newCamera.setPosition(new Vector2D(0, 0));
		((Floor)getGameWorld().getFloor()).setCamera(newCamera);
		getGameWorld().getRenderer().getGameObjects().forEach(o -> {
			o.setCurrentPosition(o.initialPosition.copy());
			if (o instanceof Player) {
				((Player)o).setRotation(0);
				((Player)o).setSpeed(new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y));
			}
		});
//		start();
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
			if (gameState == GameState.NORMAL_MODE_PLAYING ) {
				Player player = (Player) getGameWorld().getPlayer();
				if (player.isJumpIntent()) {
					getGameWorld().getLevelManager().getCurrentLevel().setTotalJumps();
				}
				if(!gameWorld.update()) {
					try {
						double time = System.currentTimeMillis() - this.startTime;
						gameLoop.stop();
						gameWorld.getLevelManager().getCurrentLevel().setTotalAttempts();
						gameWorld.getPlayerListener().playerIsDead(time);
						gameWorld.getLevelManager().getCurrentLevel().resetTotalJumps();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					
				//tu nekako stopiraj
			} else if (gameState == GameState.LEVEL_EDITOR_MODE) {
//				Thread updateThread = DaemonicThreadFactory.getInstance().newThread(() -> {
					levelEditor.update();
//				});
//				updateThread.start();
//				try {
//					updateThread.join();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
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
	 * Implementation of {@linkplain GameStateListener}
	 * 
	 * @author Andi �krgat
	 */
	public class DefaultGameStateListener implements GameStateListener {

		@Override
		public void levelEditorModeEntered(GraphicsContext graphicsContext) {
			levelEditor.setGraphicsContext(graphicsContext);
			gameState = GameState.LEVEL_EDITOR_MODE;
		}

		@Override
		public void levelEditorModeExited() {
			// TODO Auto-generated method stub

		}

		@Override
		public void characterSelectorModeEntered() {
			// TODO Auto-generated method stub

		}

		@Override
		public void characterSelectorModeExited() {
			// TODO Auto-generated method stub

		}
		
		@Override
		public void normalModePlayingStarted() {
			gameState = GameState.NORMAL_MODE_PLAYING;
		}

		@Override
		public void normalModePlayingExited() {
			// TODO Auto-generated method stub

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
			if(json == null) {
				System.out.println("Json je null");
				return false;
			}
			Session newSession = serObject.deserializeUser(json);
			String hashedPassword = HashUtil.hashContent(password);
			if(newSession.getAccount().getPassword().equals(hashedPassword) == true) {
				session = newSession;
				return true;
			} else {
				System.out.println("Wrong password");
				return false;
			}
		}

		@Override
		public void logout() {
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
