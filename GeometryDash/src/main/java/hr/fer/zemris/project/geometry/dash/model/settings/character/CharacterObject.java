package hr.fer.zemris.project.geometry.dash.model.settings.character;

import com.google.gson.annotations.Expose;

/**
 * Represents character in game. Every character in game has icon and can be locked or unlocked from the beginning of the game
 * @author Andi Skrgat
 */
public class CharacterObject {
	
	/**
	 * Uri to icon photo
	 */
	@Expose
	private String uri;
	
	/**
	 * Not all characters are available from the beginning
	 */
	@Expose
	private boolean locked;
	
	/**
	 * 
	 * @param uri Uri for loading icon
	 * @param locked Not all characters are unlocked from the start of the game
	 */
	public CharacterObject(String uri, boolean locked) {
		this.locked = locked;
		this.uri = uri;
	}
	
	/**
	 * @return  locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	/**
	 * @return the icon uri
	 */
	public String getUri() {
		return uri;
	}
	
}
