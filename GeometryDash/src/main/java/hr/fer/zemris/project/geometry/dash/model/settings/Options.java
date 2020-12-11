package hr.fer.zemris.project.geometry.dash.model.settings;

import hr.fer.zemris.project.geometry.dash.model.PlayingMode;

/**
 * Specifies all options user can manage
 * @author Andi Škrgat
 *
 */
public class Options {

	/**
	 * Auto retry after game player lost
	 */
	private boolean autoRetry;
	
	/**
	 * Specifiec which mode is active
	 */
	private PlayingMode AIMode = PlayingMode.NEURAL_NETWORK;

	public Options() {
		autoRetry = false;
	}
	/**
	 * @return the autoRetry
	 */
	public boolean isAutoRetry() {
		return autoRetry;
	}

	/**
	 * @param autoRetry the autoRetry to set
	 */
	public void setAutoRetry(boolean autoRetry) {
		this.autoRetry = autoRetry;
	}
	/**
	 * @return the playingMode
	 */
	public PlayingMode getAIMode() {
		return AIMode;
	}
	/**
	 * @param playingMode the playingMode to set
	 */
	public void setAIMode(PlayingMode playingMode) {
		this.AIMode = playingMode;
	}
	
	
	
}
