package hr.fer.zemris.project.geometry.dash.model.settings;

import javafx.scene.image.Image;

/**
 * All constants used in game
 * @author Andi Škrgat
 *
 */
public class GameConstants {

	/**
	 * Sets icon's width
	 */
	public static final short iconWidth = 45;
	
	/**
	 * Sets icon's height
	 */
	public static final short iconHeight = 45; 	// Mislim da bi ovo trebalo maknuti jer
												// ne želimo pravokutnike nego kvadrate
	/**
	 * Path to the character's icons
	 */
	public static final String pathToIcons = "/hr/fer/zemris/project/geom/dash/player/icons/";
	
	/**
	 * Path to game's songs
	 */
	public static final String pathToSongs = "/hr/fer/zemris/project/geom/dash/settings/songs/";

	/**
	 * Sets time between updates
	 */
	public static final double timeBetweenUpdates = 0.01667;
	
	/**
	 * Path to game's scenes
	 */
	public static final String pathToVisualization = "/hr/fer/zemris/project/geom/dash/visualization/";
	
	/**
	 * Width
	 */
	public static final int WIDTH = 1280;
	
	/**
	 * Height
	 */
    public static final int HEIGHT = 720;
    
    /**
     * X position of player
     */
    public static final int playerPosition_X = 300	;
    
    /**
     * Y position of player
     */
    public static final int playerPosition_Y = 560;
	
    /**
     * Floor position
     */
    public static final int floorPosition_Y = 495;
    
   
    /**
     * Path to block image
     */
    public static final String blockImage = "/hr/fer/zemris/project/geom/dash/obstacles/block/placeholder-block-icon.jpg";

    /**
     * 	Path to platform image
     */
    public static final String platformImage = "/hr/fer/zemris/project/geom/dash/obstacles/platform/placeholder-platform-icon.jpg";
    
    /**
     * Offset camera to ground
     */
    public static final int cameraGroundOffset_Y = 450;
    
    /**
     * Gravity constant
     */
    public static final int gravity_Y = 200;
    
    /**
     * Accelaration in x direction
     */
    public static final int acceleration_X = 100;
    

    /**
     * Offset player to ground
     */
    public static final int playerGroundOffset_Y = 45;
    
    /**
     * Jumping offset
     */
    public static final int playerJumpingOffset = -175;
    
    /**
     * Cell width for level editor
     */
    public static final int cell_width = 45;
    
    /**
     * Cell height for level editor
     */
    public static final int cell_height = 45;
    
    /**
     * Number of y lines on screen
     */
    public static final int linesLevelEditor_Y = 30;
    
    /**
     * Number of x lines on the screen
     */
    public static final int linesLevelEditor_X = 12;
    
    /**
     * Obstacles speed
     */
    public static final int obstaclesSpeed = 50;
    
    /**
     * Player speed X
     */
    public static final int playerSpeed_X = 50;
    
    /**
     * Player speed Y
     */
    public static final int playerSpeed_Y = 50;
   
    /**
     * Player's final speed
     */
    public static final int playerFinalSpeed_Y=400;
    
    /**
     * Time between recognizing mouse input for placing objects on the screen
     */
    public static final double transparentTimeLevel = 0.05;
}
