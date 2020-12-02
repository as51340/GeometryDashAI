package hr.fer.zemris.project.geometry.dash.model.listeners;

/**
 * Interesting actions that user performs 
 * @author Andi �krgat
 *
 */
public interface UserListener {

	/**
	 * Action when user started another game
	 */
	void userStartedPlaying();
	
	/**
	 * User can rate level
	 * @param levelName level name
	 * @param rating rating level with name levelName
	 */
	void userRatedLevel(String levelName, int rating);
	
	/**
	 * User finished level
	 * @param levelName level name
	 */
	void userFinishedLevel(String levelName);
	
	/**
	 * Method that is called when someone registers
	 * @param firstName first name
	 * @param lastName last name
	 * @param username username
	 * @param password password
	 */
	void register(String firstName, String lastName, String username, String password);
	
	/**
	 * Create new session, user already exists
	 * @param username username
	 * @param password password
	 */
	void login(String username, String password);
	
	/**
	 * logout
	 */
	void logout();
		
	/**
	 * Player jumped
	 */
	void playerJumped();
}
