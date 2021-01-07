package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

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
     * Reference to the {@linkplain WorldPlayerListener}
     */
//    private final PlayerListener playerListener;
	
	/**
     * Reference to the {@linkplain GameWorldListener}
     */
	private final GameWorldListener gameWorldListener;

    /**
     * Reference to the {@linkplain LevelManager}
     */
//    private final LevelManager levelManager;

    /**
     * Graphics context TODO documentation
     */
    private GraphicsContext graphics;

    /**
     * We need to keep reference on the player for camera moving
     */
//    private final GameObject player;

    /**
     * Reference on the floor
     */
    private GameObject floor;

    /**
     * Renderer
     */
    private Renderer renderer;

//    /**
//     * Current CharacterSelector
//     */
//    private CharactersSelector selector;
    
    /**
     * List of all players
     */
    private Set<Player> players;
    
    /**
     * Number of death players
     */
    private int deaths = 0;

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

//    /**
//     * @return level manager
//     */
//    public LevelManager getLevelManager() {
//        return levelManager;
//    }

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
    
//    /**
//     * @return player
//     */
//    public GameObject getPlayer() {
//        return player;
//    }

//    /**
//     * @return the playerListener
//     */
//    public PlayerListener getPlayerListener() {
//        return playerListener;
//    }

//    public void setCharacterSelector(CharactersSelector selector) {
//        this.selector = selector;
//    }

    /**
	 * @return the gameWorldListener
	 */
	public GameWorldListener getGameWorldListener() {
		return gameWorldListener;
	}

	/**
     * Initializes creates scene for playing. Temporary for testing collisions and jumping on platforms
     */
    public GameWorld() {
       gameWorldListener = new GameWorldListenerImpl();
       players = new TreeSet<Player>((o1, o2) -> Double.compare(o2.getCurrentPosition().getX(),
        		o1.getCurrentPosition().getX()));
//        player = new Player(new Vector2D(0, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5), new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y));
//       levelManager = new LevelManager(player, floor);
    }
    
    /**
     * Adds player to the game world
     * @param player
     */
    public void addPlayer(Player player) {
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
	}

	/**
     * Creates temporary scene
     */
    public void createScene(String levelName) {
        // when we create choose level scene then we will change these lines, maybe create scene will be public and will receive levelName
        // and level manager will have from start predefines levels, you can call levelManeger.startLevelWithName(levelName);
        // but for testing it's okay
    	for(Player player: players) {
    		player.setIcon(GameConstants.pathToIcons + GameEngine.getInstance().getDefaultSelector().getSelectedCharacter().getUri());
    	}
        Set<GameObject> levelObjects = GameEngine.getInstance().getLevelManager().getLevelByName(levelName).getGameObjects();
        GameEngine.getInstance().getLevelManager().startLevelWithName(levelName);
        floor = new Floor(new Vector2D(0, GameConstants.floorPosition_Y + GameConstants.levelToWorldOffset));
        levelObjects.add(floor);
        for(Player p: players) {
        	levelObjects.add(p);
        }
        renderer = new Renderer(levelObjects);
        ((Floor) floor).setCamera(renderer.getCamera());
    }
    
    

    /**
     * Checks for relations between camera, player and ground
     */
    public boolean update() {
    	Thread thread = new Thread(() -> {
    		checkCollision();
    	});
        thread.start();
        try {
			thread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        if(deaths == players.size()) {
        	System.out.println("Svi mrtvi");
        	return false;
        }
        
        Player player = getMaxPlayer();
        if(player == null) {
        	System.out.println("Svi mrtvi");
        	return false;
        }
        checkPlayerCamera_X(player);
        checkPlayerCamera_Y(player);
        checkCameraGround_Y();
        if(renderer.render()) {
        	int i = 0;
        	for(Player p: players) {
        		if(p.isDead() == false)
        		i++;
        	}
        	System.out.println("Zavrilo ih je: " + i);
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
    	System.out.println(playerPos_X);
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
    	for(Player p: players) {
    		if(p.isDead() == false && p.getCurrentPosition().getX() > max) {
    			max = p.getCurrentPosition().getX();
    			player = p;
    		}
    	}
    	return player;
    }

    /**
     * Checks relation between player and ground
     */
    private void checkCollision() {
    	Iterator<Player> iterator = players.iterator();
    	while(iterator.hasNext()) {
//    		if(player.isDead()) continue;
    		Player player = iterator.next();
    		player.setTouchingGround(false);
    	      for (GameObject gameObject : GameEngine.getInstance().getLevelManager().getCurrentLevel().getLevelData()) {
    	        	if(!(gameObject instanceof Player) && gameObject.getCurrentPosition().getX() - player.getCurrentPosition().getX() > 100) {
    	        		break;
    	        	}
    	            if (gameObject instanceof Obstacle) {
    	                Obstacle obstacle = (Obstacle) gameObject;
    	                if (obstacle.playerIsOn(player)) {
    	                    player.touchesGround();
    	                    player.getCurrentPosition().setY(gameObject.getCurrentPosition().getY() - GameConstants.iconHeight);
    	                }
    	                if (!player.isDead() && obstacle.checkCollisions(player)) {
    	                    if (((Obstacle) gameObject).checkCollisions(player)) {
    	                    	deaths++;
    	                    	player.setGoodness_value(gameObject.initialPosition.getX()-player.getCurrentPosition().getX());
    	                    	player.setDead(true);
    	                    } 
    	                }
    	            }
    	      }
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
			 if (GameEngine.getInstance().getSettings().getOptions().isAutoRetry()) { // ako je auto retry onda sve kreni ispocetka
		            GameEngine.getInstance().start();
		        } else { //ako ne otvori scenu u kojoj će moć izabrat da li želi restart ili u game menu
		            FXMLLoader loader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "PlayerDeathScene.fxml"));
		            loader.load();
		            PlayerDeathSceneController controller = loader.<PlayerDeathSceneController>getController();
		            Stage stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
		            Pane rootPane = stage == null ? null : (Pane) stage.getScene().lookup("#rootPane");
		            controller.setPreviousSceneRoot(rootPane);
		            controller.showInformation(
		            		GameEngine.getInstance().getLevelManager().getCurrentLevel().getLevelName(),
		                    Long.toString(GameEngine.getInstance().getLevelManager().getCurrentLevel().getTotalAttempts()),
		                    GameEngine.getInstance().getLevelManager().getCurrentLevel().getLevelPercentagePassNormalMode(),
		                    Long.toString(GameEngine.getInstance().getLevelManager().getCurrentLevel().getTotalJumps()),
		                    time
		            );
		        }
		}
    	
    }
    

}
