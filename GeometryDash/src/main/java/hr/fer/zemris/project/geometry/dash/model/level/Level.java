package hr.fer.zemris.project.geometry.dash.model.level;

/**
 * Game level with its stats etc.
 * @author Andi Škrgat
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
	 * Initially normal mode is selected for playing
	 */
	private LEVEL_MODE levelMode = LEVEL_MODE.NORMAL_MODE;

	/**
	 * @param levelMode the levelMode to set
	 */
	public void setLevelMode(LEVEL_MODE levelMode) {
		this.levelMode = levelMode;
	}

	/**
	 * @return the levelMode
	 */
	public LEVEL_MODE getLevelMode() {
		return levelMode;
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

}
