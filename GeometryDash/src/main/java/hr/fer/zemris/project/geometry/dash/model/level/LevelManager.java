package hr.fer.zemris.project.geometry.dash.model.level;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.visualization.level.ObjectsOnGrid;

/**
 * @author Andi Škrgat
 *
 */
public class LevelManager {

	/**
	 * List of all available levels in game
	 */
	private Set<Level> allLevels;
	
	
	/**
	 * Constructor that loads all initial levels created from the start
	 */
	public LevelManager() {
		allLevels = new HashSet<Level>();
	}
	
	/**
	 * Adds level to the list of all levels
	 * @param levelName name of the level
	 * @param levelData objects belonging to the level
	 */
	public Level addLevel(String levelName, Set<GameObject> levelData) {
		Level level = new Level(levelName, levelData);		
		allLevels.add(level);
		return level;
	}
	
	/**
	 * Returns appropriate level by its name
	 * @param levelName name of the level
	 * @return {@linkplain Level} with level name
	 */
	public Level getLevelByName(String levelName) {
		for(Level level: allLevels) {
			if(level.getLevelName().equals(levelName)) {
				return level;
			}
		}
		return null;
	}
	
	/**
	 * Remove level with given name
	 * @param levelName name of the level
	 */
	public void removeLevelByName(String levelName) {
		Iterator<Level> iterator = allLevels.iterator();
		while(iterator.hasNext()) {
			Level lev = iterator.next();
			if(lev.getLevelName().equals(levelName)) {
				iterator.remove();
				return;
			}
		}
	}
}
