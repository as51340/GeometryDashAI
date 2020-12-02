package hr.fer.zemris.project.geometry.dash.model.settings;

import hr.fer.zemris.project.geometry.dash.model.stats.Stats;

/**
 * Account in game. This will enable us to remembers user data
 * @author Andi Škrgat
 *
 */
public class Account {

	/**
	 * Username
	 */
	private String username;
	
	/**
	 * Password
	 */
	private String password;
	
	/**
	 * User's first name
	 */
	private String firstName;
	
	/**
	 * User's last name
	 */
	private String lastName;

	/**
	 * Account information
	 * @param firstName first name 
	 * @param lastName last name
	 * @param username username
	 * @param password password
	 */
	public Account(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
		
}
