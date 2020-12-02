package hr.fer.zemris.project.geometry.dash.model.level;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.settings.music.LevelMusicPlayer;
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
	 * Reference to the level music player
	 */
	private LevelMusicPlayer levelMusicPlayer;

	/**
	 * Currently selected level
	 */
	private Level currentLevel;

	/**
	 * Constructor that loads all initial levels created from the start
	 */
	public LevelManager() {
		allLevels = new HashSet<Level>();
	}

	/**
	 * Adds level to the list of all levels
	 * 
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
		for (Level level : allLevels) {
			if (level.getLevelName().equals(levelName)) {
				return level;
			}
		}
		return null;
	}

	/**
	 * Remove level with given name
	 * 
	 * @param levelName name of the level
	 */
	public void removeLevelByName(String levelName) {
		Iterator<Level> iterator = allLevels.iterator();
		while (iterator.hasNext()) {
			Level lev = iterator.next();
			if (lev.getLevelName().equals(levelName)) {
				iterator.remove();
				return;
			}
		}
	}

	/**
	 * Level name
	 * 
	 * @param levelName level name
	 * @return level with level name
	 */
	public Set<GameObject> startLevelWithName(String levelName) {
		Iterator<Level> iterator = allLevels.iterator();
		while (iterator.hasNext()) {
			Level lev = iterator.next();
			if (lev.getLevelName().equals(levelName)) {
				levelMusicPlayer.startPlayingSongForLevel(levelName);
				return lev.getLevelData();
			}
		}
		// we should throw because it's not normal situation when user can choose to load level with some level name
		// but it actually doesn't exist
		throw new RuntimeException("No such level");
	}

	/**
	 * @return all levels
	 */
	public Set<Level> getAllLevels() {
		return this.allLevels;
	}

	/**
	 * @return the currentLevel
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * @param currentLevel the currentLevel to set
	 */
	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

	/**
	 * @return the levelMusicPlayer
	 */
	public LevelMusicPlayer getLevelMusicPlayer() {
		return levelMusicPlayer;
	}

}
