package hr.fer.zemris.project.geometry.dash.model.settings.character;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps all characters loaded from resources
 * @author Andi Škrgat
 *
 */
public class CharactersSelector {
	
	/**
	 * Currently selected character for playing, is this the smartest way
	 */
	public static CharacterObject selectedCharacter;
	
	/**
	 * All characters 
	 */
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
		CharacterObject char5 = new CharacterObject("geom-dash-angry-icon.png", true);
		CharacterObject char6 = new CharacterObject("geom-dash-blue-green-icon.jpg", true);
		CharacterObject char7 = new CharacterObject("geom-dash-catch-them-all-icon.jpg", true);
		CharacterObject char8 = new CharacterObject("geom-dash-cod3breaker-icon.jpg", true);
		CharacterObject char9 = new CharacterObject("geom-dash-crying-icon.jpg", true);
		CharacterObject char10 = new CharacterObject("geom-dash-cube-icon.jpg", true);
		CharacterObject char11 = new CharacterObject("geom-dash-demons-icon.jpg", true);
		CharacterObject char12 = new CharacterObject("geom-dash-graphic-icon.jpg", true);
		CharacterObject char13 = new CharacterObject("geom-dash-orange-icon.png", true);
		CharacterObject char14 = new CharacterObject("geom-dash-sad-icon.jpg", true);
		CharacterObject char15 = new CharacterObject("geom-dash-shuriken-icon.jpg", true);
		CharacterObject char16 = new CharacterObject("geom-dash-shy-guy-icon.jpg", true);
		CharacterObject char17 = new CharacterObject("geom-dash-special-icon.jpg", true);
		CharacterObject char18 = new CharacterObject("geom-dash-very-angry-icon.jpg", true);
		CharacterObject char19 = new CharacterObject("geom-dash-yellow-icon.png", true);
		CharacterObject char20 = new CharacterObject("geom-dash-gatekeeper-query-icon.jpg", true);
		CharacterObject char21 = new CharacterObject("one.png", true);
		CharacterObject char22 = new CharacterObject("two.png", true);
		CharacterObject char23 = new CharacterObject("three.png", true);
		CharacterObject char24 = new CharacterObject("player.png", true);
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
	

}
