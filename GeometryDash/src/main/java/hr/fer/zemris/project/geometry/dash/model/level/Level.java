package hr.fer.zemris.project.geometry.dash.model.level;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.listeners.SerializationListener;
import hr.fer.zemris.project.geometry.dash.visualization.level.ObjectsOnGrid;

/**
 * Game level with all its data, stats and level name.
 * @author Andi �krgat
 *
 */
public class Level {
	
	/**
	 * Level name
	 */
	private String levelName;
	
	/**
	 * Total attempts on level
	 */
	private long totalAttempts;
	
	/**
	 * Total jumps on level
	 */
	private long totalJumps;
	
	/**
	 * Current percentage pass when playing normal mode
	 */
	private double currentLevelPercentagePassNormalMode;
	
	/**
	 * Current percentage pass when playing practice mode
	 */
	private double currentLevelPercentagePassPracticeMode;
	
	/**
	 * Percentage pass when playing normal mode
	 */
	private double levelPercentagePassNormalMode;
	
	/**
	 * Percentage pass when playing practice mode
	 */
	private double levelPercentagePassPracticeMode;
	
	/**
	 * Level's data
	 */
	private Set<GameObject> gameObjects;
	
	
	/**
	 * Level is created with its data
	 * @param levelName name of the level
	 * @param gameObjects objects belonging to the level
	 */
	public Level(String levelName, Set<GameObject> gameObjects) {
		this.levelName = levelName;
		this.gameObjects = gameObjects;
	}

	/**
	 * @return the totalAttempts
	 */
	public long getTotalAttempts() {
		return totalAttempts;
	}

	/**
	 * increments totalAttempts
	 */
	public void setTotalAttempts() {
		this.totalAttempts++;
	}

	/**
	 * @return the totalJumps
	 */
	public long getTotalJumps() {
		return totalJumps;
	}

	/**
	 * increments totalJumps
	 */
	public void setTotalJumps() {
		this.totalJumps++;
	}

	/**
	 * resets totalJumps
	 */
	public void resetTotalJumps() {
		this.totalJumps = 0;
	}

	/**
	 * @return the levelPercentagePassNormalMode
	 */
	public double getLevelPercentagePassNormalMode() {
		return levelPercentagePassNormalMode;
	}

	/**
	 *  increments levelPercentagePassNormalMode
	 */
	public void setLevelPercentagePassNormalMode() {
		this.levelPercentagePassNormalMode++;
	}

	/**
	 * @return the levelPercentagePassPracticeMode
	 */
	public double getLevelPercentagePassPracticeMode() {
		return levelPercentagePassPracticeMode;
	}

	/**
	 * increments levelPercentagePassPracticeMode
	 */
	public void setLevelPercentagePassPracticeMode() {
		this.levelPercentagePassPracticeMode++;
	}
	
	/**
	 * @return the levelPercentagePassNormalMode
	 */
	public double getCurrentLevelPercentagePassNormalMode() {
		return currentLevelPercentagePassNormalMode;
	}

	/**
	 * @return the levelPercentagePassPracticeMode
	 */
	public double getCurrentLevelPercentagePassPracticeMode() {
		return currentLevelPercentagePassPracticeMode;
	}
	
	/**
	 * @param currentLevelPercentagePassNormalMode the currentLevelPercentagePassNormalMode to set
	 */
	public void setCurrentLevelPercentagePassNormalMode(double currentLevelPercentagePassNormalMode) {
		this.currentLevelPercentagePassNormalMode = currentLevelPercentagePassNormalMode;
	}

	/**
	 * @param currentLevelPercentagePassPracticeMode the currentLevelPercentagePassPracticeMode to set
	 */
	public void setCurrentLevelPercentagePassPracticeMode(double currentLevelPercentagePassPracticeMode) {
		this.currentLevelPercentagePassPracticeMode = currentLevelPercentagePassPracticeMode;
	}

	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * @return level data
	 */
	public Set<GameObject> getLevelData() {
		return gameObjects;
	}

	/**
	 * @param totalAttempts the totalAttempts to set
	 */
	public void setTotalAttempts(long totalAttempts) {
		this.totalAttempts = totalAttempts;
	}

	/**
	 * @param totalJumps the totalJumps to set
	 */
	public void setTotalJumps(long totalJumps) {
		this.totalJumps = totalJumps;
	}

	/**
	 * @param levelPercentagePassNormalMode the levelPercentagePassNormalMode to set
	 */
	public void setLevelPercentagePassNormalMode(double levelPercentagePassNormalMode) {
		this.levelPercentagePassNormalMode = levelPercentagePassNormalMode;
	}

	/**
	 * @param levelPercentagePassPracticeMode the levelPercentagePassPracticeMode to set
	 */
	public void setLevelPercentagePassPracticeMode(double levelPercentagePassPracticeMode) {
		this.levelPercentagePassPracticeMode = levelPercentagePassPracticeMode;
	}
	
	/**
	 * @return the gameObjects
	 */
	public Set<GameObject> getGameObjects() {
		return gameObjects;
	}

}
