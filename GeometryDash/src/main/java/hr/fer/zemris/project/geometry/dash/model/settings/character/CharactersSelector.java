package hr.fer.zemris.project.geometry.dash.model.settings.character;

import java.util.List;

import javax.swing.ImageIcon;

/**
 * Keeps all characters loaded from resources
 * @author Korisnik
 *
 */
public class CharactersSelector {
	
	/**
	 * Currently selected character for playing
	 */
	private Character selectedCharacter;
	
	/**
	 * All characters 
	 */
	private List<Character> allCharacters;
	
	/**
	 * Constructor that loads all characters
	 */
	public CharactersSelector() {
		this.allCharacters = loadAllCharacters(allCharacters);
	}
	
	/**
	 * Loads all characters
	 * @param allCharacters
	 * @return list of loaded characters 
	 */
	private List<Character> loadAllCharacters(List<Character> allCharacters) {
		//TODO
		return null;
	}
	
	/**
	 * @return all game characters
	 */
	public List<Character> getAllCharacters() {
		return allCharacters;
	}
	
	
	/**
	 * @return currently selected character
	 */
	public Character getSelectedCharacter() {
		return this.selectedCharacter;
		
	}
	

}
