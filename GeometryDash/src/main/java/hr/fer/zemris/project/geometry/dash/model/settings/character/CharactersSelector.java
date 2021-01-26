package hr.fer.zemris.project.geometry.dash.model.settings.character;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Keeps all characters loaded from resources
 * @author Andi Škrgat
 *
 */
public class CharactersSelector {
	
	/**
	 * Currently selected character for playing, is this the smartest way
	 */
	@Expose
	private CharacterObject selectedCharacter;
	
	/**
	 * All characters 
	 */
	@Expose
	private List<CharacterObject> allCharacters;
	
	/**
	 * Constructor that loads all characters
	 */
	public CharactersSelector() {
		allCharacters = new ArrayList<CharacterObject>();
		loadAllCharacters(allCharacters);
	}
	
	/**
	 * Loads all characters
	 * @param allCharacters
	 * @return list of loaded characters 
	 */
	private void loadAllCharacters(List<CharacterObject> allCharacters) {
		CharacterObject char1 = new CharacterObject("geom-dash-130-coins-icon.jpg", false);
		CharacterObject char2 = new CharacterObject("geom-dash-3000-stars-icon.jpg", false);
		CharacterObject char4 = new CharacterObject("geom-dash-9000-coins-icon.jpg", false);
		CharacterObject char5 = new CharacterObject("geom-dash-angry-icon.png", false);
		CharacterObject char6 = new CharacterObject("geom-dash-blue-green-icon.jpg", false);
		CharacterObject char7 = new CharacterObject("geom-dash-catch-them-all-icon.jpg", false);
		CharacterObject char8 = new CharacterObject("geom-dash-cod3breaker-icon.jpg", false);
		CharacterObject char9 = new CharacterObject("geom-dash-crying-icon.jpg", false);
		CharacterObject char10 = new CharacterObject("geom-dash-cube-icon.jpg", false);
		CharacterObject char11 = new CharacterObject("geom-dash-demons-icon.jpg", false);
		CharacterObject char12 = new CharacterObject("geom-dash-graphic-icon.jpg", false);
		CharacterObject char13 = new CharacterObject("geom-dash-orange-icon.png", false);
		CharacterObject char14 = new CharacterObject("geom-dash-sad-icon.jpg", false);
		CharacterObject char15 = new CharacterObject("geom-dash-shuriken-icon.jpg", false);
		CharacterObject char16 = new CharacterObject("geom-dash-shy-guy-icon.jpg", false);
		CharacterObject char17 = new CharacterObject("geom-dash-special-icon.jpg", false);
		CharacterObject char18 = new CharacterObject("geom-dash-very-angry-icon.jpg", false);
		CharacterObject char19 = new CharacterObject("geom-dash-yellow-icon.png", false);
		CharacterObject char20 = new CharacterObject("geom-dash-gatekeeper-query-icon.jpg", false);
		CharacterObject char21 = new CharacterObject("one.png", false);
		CharacterObject char22 = new CharacterObject("two.png", false);
		CharacterObject char23 = new CharacterObject("three.png", false);
		CharacterObject char24 = new CharacterObject("player.png", false);
		allCharacters.add(char1);
		allCharacters.add(char2);
		allCharacters.add(char4);
		allCharacters.add(char5);
		allCharacters.add(char6);
		allCharacters.add(char7);
		allCharacters.add(char8);
		allCharacters.add(char9);
		allCharacters.add(char10);
		allCharacters.add(char11);
		allCharacters.add(char12);
		allCharacters.add(char13);
		allCharacters.add(char14);
		allCharacters.add(char15);
		allCharacters.add(char16);
		allCharacters.add(char17);
		allCharacters.add(char18);
		allCharacters.add(char19);
		allCharacters.add(char20);
		allCharacters.add(char21);
		allCharacters.add(char22);
		allCharacters.add(char23);
		allCharacters.add(char24);
		selectedCharacter = char24;
	}
	
	/**
	 * @return all game characters
	 */
	public List<CharacterObject> getAllCharacters() {
		return allCharacters;
	}
	
	
	/**
	 * @return currently selected character
	 */
	public CharacterObject getSelectedCharacter() {
		return selectedCharacter;
	}

	/**
	 * Sets currently selected character
	 * @param selectedCharacter
	 */
	public void setSelectedCharacter(CharacterObject selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
	}
	
	/**
	 * Unlocks character.
	 * @param i
	 */
	public void unlockCharacter(int i) {
		allCharacters.get(i).setLocked(false);
	}
	

}
