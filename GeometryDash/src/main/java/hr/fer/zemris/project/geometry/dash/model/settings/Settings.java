package hr.fer.zemris.project.geometry.dash.model.settings;

/**
 * Game settings
 * @author Andi Škrgat
 *
 */
public class Settings {
	
	/**
	 * Account options
	 */
	private Account account;
	
	/**
	 * Game options
	 */
	private Options options;
	
	/**
	 * Initializes account and options
	 */
	public Settings() {
		account = new Account();
		options = new Options();
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @return the options
	 */
	public Options getOptions() {
		return options;
	}
	

}
