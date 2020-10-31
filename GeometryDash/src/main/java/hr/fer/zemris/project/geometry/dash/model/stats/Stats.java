package hr.fer.zemris.project.geometry.dash.model.stats;

/**
 * Handles all statistic from game
 * @author Andi Škrgat
 *
 */
public class Stats {
	
	/**
	 * Total jumps in game
	 */
	private long totalJumps;
	
	/**
	 * Total attempts
	 */
	private long totalAttempts;
	
	/**
	 * Collected stars?
	 */
	private short collectedStars;
	
	/**
	 * Collected diamonds?
	 */
	private short collectedDiamonds;
	
	/**
	 * Total orbs collected
	 */
	private short totalOrbsCollected;
	
	/**
	 * Completed levels
	 */
	private short completedLevels;
	
	/**
	 * Secret coins
	 */
	private short secretCoins;
	
	/**
	 * User coins
	 */
	private short userCoins;
	
	/**
	 * Liked levels
	 */
	private short likedLevels;
	
	/**
	 * Disliked levels
	 */
	private short dislikedLevels;
	
	/**
	 * Rated levels
	 */
	private short ratedLevels;

	/**
	 * @return the totalJumps
	 */
	public long getTotalJumps() {
		return totalJumps;
	}

	/**
	 * @param increments totalJumps
	 */
	public void setTotalJumps() {
		this.totalJumps++;
	}

	/**
	 * @return the totalAttempts
	 */
	public long getTotalAttempts() {
		return totalAttempts;
	}

	/**
	 * @param increments totalAttempts
	 */
	public void setTotalAttempts() {
		this.totalAttempts++;
	}

	/**
	 * @return the collectedStars
	 */
	public short getCollectedStars() {
		return collectedStars;
	}

	/**
	 * @param increments collectedStars
	 */
	public void setCollectedStars() {
		this.collectedStars++;
	}

	/**
	 * @return the collectedDiamonds
	 */
	public short getCollectedDiamonds() {
		return collectedDiamonds;
	}

	/**
	 * @param increments collectedDiamonds
	 */
	public void setCollectedDiamonds() {
		this.collectedDiamonds++;
	}

	/**
	 * @return the totalOrbsCollected
	 */
	public short getTotalOrbsCollected() {
		return totalOrbsCollected;
	}

	/**
	 * @param increments totalOrbsCollected
	 */
	public void setTotalOrbsCollected() {
		this.totalOrbsCollected++;
	}

	/**
	 * @return the completedLevels
	 */
	public short getCompletedLevels() {
		return completedLevels;
	}

	/**
	 * @param increments completedLevels
	 */
	public void setCompletedLevels() {
		this.completedLevels++;
	}

	/**
	 * @return the secretCoins
	 */
	public short getSecretCoins() {
		return secretCoins;
	}

	/**
	 * @param increments secretCoins
	 */
	public void setSecretCoins() {
		this.secretCoins++;
	}

	/**
	 * @return the userCoins
	 */
	public short getUserCoins() {
		return userCoins;
	}

	/**
	 * @param increments userCoins 
	 */
	public void setUserCoins() {
		this.userCoins++;
	}

	/**
	 * @return the likedLevels
	 */
	public short getLikedLevels() {
		return likedLevels;
	}

	/**
	 * @param increments liked levels
	 */
	public void setLikedLevels() {
		this.likedLevels++;
	}

	/**
	 * @return the dislikedLevels
	 */
	public short getDislikedLevels() {
		return dislikedLevels;
	}

	/**
	 * @param increments disliked levels
	 */
	public void setDislikedLevels() {
		this.dislikedLevels++;
	}

	/**
	 * @return the ratedLevels
	 */
	public short getRatedLevels() {
		return ratedLevels;
	}

	/**
	 * @param increments ratedLevels
	 */
	public void setRatedLevels() {
		this.ratedLevels++;
	}
	
	
	
	

}
