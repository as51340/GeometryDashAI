package hr.fer.zemris.project.geometry.dash.model.settings;

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
	 * Specifies if restart button will be shown on the pause screen
	 */
	private boolean showRestartButton;

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
	 * @return the showRestartButton
	 */
	public boolean isShowRestartButton() {
		return showRestartButton;
	}

	/**
	 * @param showRestartButton the showRestartButton to set
	 */
	public void setShowRestartButton(boolean showRestartButton) {
		this.showRestartButton = showRestartButton;
	}
	
	//bunch of AI options
	
}
