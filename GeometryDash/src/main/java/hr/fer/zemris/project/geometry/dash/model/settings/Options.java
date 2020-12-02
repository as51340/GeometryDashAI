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
	
	//bunch of AI options
	
}
