package hr.fer.zemris.project.geometry.dash.model;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.LevelManager;
import hr.fer.zemris.project.geometry.dash.model.listeners.GameStateListener;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.Settings;
import hr.fer.zemris.project.geometry.dash.model.settings.music.SoundSystem;
import hr.fer.zemris.project.geometry.dash.visualization.level.GridAttaching;
import hr.fer.zemris.project.geometry.dash.visualization.level.LevelEditor;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * Engine that will be used in implementation of game <strong>Geometry dash</strong>
 * It defines only properties that are connected directly with running game, it doesn't have any specific
 * connection with geometry dash, so it means on this "platform" can also be other games played
 * @author Andi Å krgat
 *
 */
public class GameEngine implements SoundSystem{
	
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
	 * Basic constructor that sets game's title
	 * Creates game loop and event handler
	 * @param title Game's title
	 */
	public GameEngine(int fps, String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = fps;
		settings = new Settings();
		gameWorld = new GameWorld(); //for now list of obstacles is empty, not focus on that currently
		levelEditor = new LevelEditor();
		gameStateListener = new DefaultGameStateListener();
		levelManager = new LevelManager();
		createGameLoop();
	}

	/**
	 * @return the levelManager
	 */
	public LevelManager getLevelManager() {
		return levelManager;
	}

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
	 * Starts game loop
	 */
	public void start() {
		gameLoop.play();
	}
	
	/**
	 * Updates game world or level editor
	 * @return {@linkplain KeyFrame}
	 */
	private KeyFrame createKeyFrame() {
		Duration frameTime = Duration.millis(1000.0/getFps()); //for 60 FPS and that is usually standard
		Vector2D camDir = new Vector2D(1,0);
        camDir.scale(GameConstants.timeBetweenUpdates * 50f);
		//time between update will be approx. 16.67ms, for 10ms we have to provide 100 fps as value
        return new KeyFrame(frameTime, event -> {
			if(gameState == GameState.NORMAL_MODE_PLAYING || gameState == GameState.PRACTISE_MODE_PLAYING) {
				gameWorld.update();
			} else if(gameState == GameState.LEVEL_EDITOR_MODE) {
				levelEditor.update();
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
	 * Initializes stage from game-engine data
	 * @param stage {@linkplain Stage}
	 */
	public void createStageFromData(Stage stage) {
		stage.setTitle(this.title);
		stage.setWidth(this.width);
		stage.setHeight(this.height);
	}

	@Override
	public void playKillSound() {
		//TODO
	}
	
	/**
	 * Implementation of {@linkplain GameStateListener}
	 * @author Andi Škrgat
	 */
	public class DefaultGameStateListener implements GameStateListener{

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
		public void practiseModePlayingEntered() {
			gameState = GameState.PRACTISE_MODE_PLAYING;
		}

		@Override
		public void practiseModePlayingExited() {
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
}
