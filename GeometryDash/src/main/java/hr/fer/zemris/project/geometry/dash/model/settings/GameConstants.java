package hr.fer.zemris.project.geometry.dash.model.settings;

import javafx.scene.image.Image;

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
    public static final int playerPosition_X = 225;
    
    /**
     * Y position of player
     */
    public static final int playerPosition_Y = 440;
	
    /**
     * Floor position
     */
    public static final int floorPosition_Y = 630;
    
    /**
     * Camera offset to player
     */	
    public static final int cameraPlayerOffset_Y = 500;

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
    public static final int cameraGroundOffset_Y = 150;
    
    /**
     * Gravity constant
     */
    public static final int GRAVITY = 50;
	
    /**
     * Offset player to ground
     */
    public static final int playerGroundOffset_Y = 45;
    
    /**
     * Jumping offset
     */
    public static final int playerJumpingOffset = -75;
    
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
    public static final int linesLevelEditor_X = 15;
    
    /**
     * Obstacles speed
     */
    public static final int obstaclesSpeed = 50;
    
   
}
