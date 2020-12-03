package hr.fer.zemris.project.geometry.dash.model.stats;

import com.google.gson.annotations.Expose;

/**
 * Handles all statistic from game
 * @author Andi �krgat
 *
 */
public class Stats {
	
	/**
	 * Total jumps in game
	 */
	@Expose
	private long totalJumps;
	
	/**
	 * Total attempts
	 */
	@Expose
	private long totalAttempts;
	
	/**
	 * Collected stars?
	 */
	@Expose
	private short collectedStars;
	
	/**
	 * Completed levels
	 */
	@Expose
	private short completedLevels;
	
	/**
	 * User coins
	 */
	@Expose
	private short userCoins;
	
	/**
	 * Liked levels
	 */
	@Expose
	private short likedLevels;
	
	/**
	 * Disliked levels
	 */
	@Expose
	private short dislikedLevels;
	
	/**
	 * Rated levels
	 */
	@Expose
	private short ratedLevels;

	/**
	 * @return the totalJumps
	 */
	public long getTotalJumps() {
		return totalJumps;
	}
	
	public Stats() {
		
	}
	
	

	public Stats(long totalJumps, long totalAttempts, short completedLevels, short likedLevels, short dislikedLevels,
			short ratedLevels) {
		super();
		this.totalJumps = totalJumps;
		this.totalAttempts = totalAttempts;
		this.completedLevels = completedLevels;
		this.likedLevels = likedLevels;
		this.dislikedLevels = dislikedLevels;
		this.ratedLevels = ratedLevels;
	}

	/**
	 * @param increments totalJumps
	 */
	public boolean setTotalJumps() {
		this.totalJumps++;
		if(totalJumps == 100) {
			//do something, maybe return true so method that calls can show something
			return true;
		} else if(totalJumps == 500) {
			// mozes i drugacije
			return true;
		} else if(totalJumps == 1000) {
			return true;
		}
		return false;
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
	public boolean setTotalAttempts() {
		this.totalAttempts++;
		if(totalAttempts == 100) {
			return true;
		} else if(totalAttempts == 500) {
			return true;
		}
		return false;
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
	 * @return the completedLevels
	 */
	public short getCompletedLevels() {
		return completedLevels;
	}

	/**
	 * @param increments completedLevels
	 */
	//for example show him something, when he finished his first level, when finished five of them etc.. and then return true or false
	//same for others, think how you can rate levels, or we can just implement Like od Dislike
	public boolean setCompletedLevels() {
		this.completedLevels++;
		if(completedLevels == 1) {
			return true; // or you can return some data structure with bool and number of levels finished so method that calls this know 
			// what to show
		}
		return false;
		
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
