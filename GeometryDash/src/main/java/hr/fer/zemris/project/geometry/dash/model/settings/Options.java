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

	private int numberOfHiddenLayers;

	private int numberPerHiddenLayer;

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

	/**
	 * @return number of hidden layers
	 */
	public int getNumberOfHiddenLayers() {
		return numberOfHiddenLayers;
	}

	public int getNumberPerHiddenLayer() {
		return numberPerHiddenLayer;
	}

	public void setNumberOfHiddenLayers(int numberOfHiddenLayers) {
		this.numberOfHiddenLayers = numberOfHiddenLayers;
	}

	public void setNumberPerHiddenLayer(int numberPerHiddenLayer) {
		this.numberPerHiddenLayer = numberPerHiddenLayer;
	}
}
