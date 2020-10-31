package hr.fer.zemris.project.geometry.dash.model.settings.character;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import hr.fer.zemris.project.geometry.dash.model.Utils;
import javafx.scene.image.Image;


/**
 * Represents character in game. Every character in game has icon and can be locked or unlocked from the beginning of the game
 * @author Andi Škrgat
 */
public class CharacterObject {
	
	/**
	 * {@linkplain ImageIcon}
	 */
	private Image icon;
	
	/**
	 * Not all characters are available from the beginning
	 */
	private boolean locked;
	
	/**
	 * 
	 * @param uri Uri for loading icon
	 * @param locked Not all characters are unlocked from the start of the game
	 */
	public CharacterObject(String uri, boolean locked) {
		this.icon = loadIcon(uri);
		this.locked = locked;
	}
	
	/**
	 * Loads icon with the help of {@linkplain Utils} class
	 * @param uri path to the icon
	 * @return loaded icon
	 */
	private Image loadIcon(String uri) {
		return Utils.loadIcon(uri);
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
	 * @return the icon
	 */
	public Image getIcon() {
		return icon;
	}
	
	

}
