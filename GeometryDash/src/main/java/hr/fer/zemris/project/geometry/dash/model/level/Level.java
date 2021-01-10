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
	 * Percentage pass when playing normal mode
	 */
	private short levelPercentagePassNormalMode;
	
	/**
	 * Percentage pass when playing practice mode
	 */
	private short levelPercentagePassPracticeMode;
	
	/**
	 * Level's data
	 */
	private Set<GameObject> gameObjects;
	
	/**
	 * Stores information about end of the level
	 */
	private double last_x = Double.MIN_VALUE;
		
	
	/**
	 * Level is created with its data
	 * @param levelName name of the level
	 * @param gameObjects objects belonging to the level
	 */
	public Level(String levelName, Set<GameObject> gameObjects) {
		this.levelName = levelName;
		TreeSet<GameObject> treeset = new TreeSet<GameObject>(AIConstants.obstaclesLevelComparator);
		treeset.addAll(gameObjects);
		this.gameObjects = treeset;
		setLast_x( ( (TreeSet<GameObject>) this.gameObjects).last().getCenterPosition().getX());
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
	public short getLevelPercentagePassNormalMode() {
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
	public short getLevelPercentagePassPracticeMode() {
		return levelPercentagePassPracticeMode;
	}

	/**
	 * increments levelPercentagePassPracticeMode
	 */
	public void setLevelPercentagePassPracticeMode() {
		this.levelPercentagePassPracticeMode++;
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
	public void setLevelPercentagePassNormalMode(short levelPercentagePassNormalMode) {
		this.levelPercentagePassNormalMode = levelPercentagePassNormalMode;
	}

	/**
	 * @param levelPercentagePassPracticeMode the levelPercentagePassPracticeMode to set
	 */
	public void setLevelPercentagePassPracticeMode(short levelPercentagePassPracticeMode) {
		this.levelPercentagePassPracticeMode = levelPercentagePassPracticeMode;
	}
	
	/**
	 * @return the gameObjects
	 */
	public Set<GameObject> getGameObjects() {
		return gameObjects;
	}

	/**
	 * @return the last_x
	 */
	public double getLast_x() {
		return last_x;
	}

	/**
	 * Sets last x
	 * @param last
	 */
	public void setLast_x(double last) {
		this.last_x = last;
	}

}
