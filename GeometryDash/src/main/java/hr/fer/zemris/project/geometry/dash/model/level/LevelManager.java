package hr.fer.zemris.project.geometry.dash.model.level;

import java.util.List;

/**
 * Somehow will read data from xml or some other type and create objects on that positions
 * @author Andi Škrgat
 *
 */
public class LevelManager {

	/**
	 * List of all available levels in game
	 */
	private List<Level> allLevels;
	
	/**
	 * Currently selected level
	 */
	private Level currentLevel;
}
