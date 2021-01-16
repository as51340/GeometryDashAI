package hr.fer.zemris.project.geometry.dash.model.settings;

import javafx.scene.image.Image;

/**
 * All constants used in game
 * @author Andi Skrgat
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
	public static final short iconHeight = 45; 
	
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
	 * Path to game's obstacles
	 */
	public static final String pathToObstacles = "/hr/fer/zemris/project/geom/dash/obstacles/";

	 /** 
	 * Path to photos
	 */
	public static final String pathToStatic = "/hr/fer/zemris/project/geom/dash/static/";

	
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
    public static final int playerPosition_X = 300;
    
    /**
     * Y position of player
     */
    public static final int playerPosition_Y = 550;
	
    /**
     * Floor position
     */
    public static final int floorPosition_Y = 540;
    
   
    /**
     * Path to block image
     */
    public static final String blockImage = "/hr/fer/zemris/project/geom/dash/obstacles/block/blue.png";

    /**
     * Path to spike image
     */
    public static final String spikeImage = "/hr/fer/zemris/project/geom/dash/obstacles/spike/blue.png";

    /**
     * Path to left spike image
     */
    public static final String leftSpikeImage = "/hr/fer/zemris/project/geom/dash/obstacles/spike/blueLeft.png";

    /**
     * Path to right spike image
     */
    public static final String rightSpikeImage = "/hr/fer/zemris/project/geom/dash/obstacles/spike/blueRight.png";

    /**
     * 	Path to platform image
     */
    public static final String platformImage = "/hr/fer/zemris/project/geom/dash/obstacles/platform/blue.png";

    /**
     * 	Path to grass spike image
     */
    public static final String grassImage = "/hr/fer/zemris/project/geom/dash/obstacles/grassspike/blue.png";

    /**
     * Offset camera to ground
     */
    public static final int cameraGroundOffset_Y = 450;
    
    /**
     * Gravity constant
     */
    public static final int gravity_Y = 5600;
    
    /**
     * Accelaration in x direction
     */
    public static final int acceleration_X = 400;

    /**
     * Offset player to ground
     */
    public static final int playerGroundOffset_Y = 45;
    
    /**
     * Jumping offset
     */
    public static final int playerJumpingOffset = -1400;
    
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
    public static final int linesLevelEditor_X = 13;
    
    /**
     * Obstacles speed
     */
    public static final int obstaclesSpeed = 50;
    
    /**
     * Player speed X
     */
    public static final int playerSpeed_X = 100;
    
    /**
     * Player speed Y
     */
    public static final int playerSpeed_Y = 100;
   
    /**
     * Player's final speed in y direction
     */
    public static final int playerFinalSpeed_Y=1500;
    
    /**
     * Player's final speed in y direction
     */
    public static final int playerFinalSpeed_X=430;
    
    /**
     * Path to zip file
     */
    public static final String pathToLevelsFolder = "levels";
    
    /**
     * Buffer size when reading from file
     */
    public static final int bytesPerKB = 4096;
 
    /**
     * Player's rotation
     */
    public static final double playerRotationSpeed = 500;
    
    /**
<<<<<<< HEAD
     * Level to world y offset
     */
    public static final int levelToWorldOffset = 50;
    
    /**
     * Path to user folder
     */
    public static final String pathToUsersFolder = "users";
 
    /** Duration of transition between scenes.
     * 
     */
    public static final int TRANSITION_DURATION = 300;
    
    /**
     * Level ending offset
     */
    public static final int LEVEL_END_OFFSET = 200;
}
