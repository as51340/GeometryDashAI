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
		loadAllCharacters(allCharacters);
	}
	
	/**
	 * Loads all characters
	 * @param allCharacters
	 * @return list of loaded characters 
	 */
	private void loadAllCharacters(List<Character> allCharacters) {
		Character char1 = new Character("geom-dash-130-coins-icon.jpg", false);
		Character char2 = new Character("geom-dash-3000-stars-icon.jpg", false);
		Character char4 = new Character("geom-dash-9000-coins-icon.jpg", false);
		Character char5 = new Character("geom-dash-angry-icon.png", true);
		Character char6 = new Character("geom-dash-blue-green-icon.jpg", true);
		Character char7 = new Character("geom-dash-catch-them-all-icon.jpg", true);
		Character char8 = new Character("geom-dash-cod3breaker-icon.jpg", true);
		Character char9 = new Character("geom-dash-crying-icon.jpg", true);
		Character char10 = new Character("geom-dash-cube-icon.jpg", true);
		Character char11 = new Character("geom-dash-demons-icon.jpg", true);
		Character char12 = new Character("geom-dash-graphic-icon.jpg", true);
		Character char13 = new Character("geom-dash-orange-icon.png", true);
		Character char14 = new Character("geom-dash-sad-icon.jpg", true);
		Character char15 = new Character("geom-dash-shuriken-icon.jpg", true);
		Character char16 = new Character("geom-dash-shy-guy-icon.jpg", true);
		Character char17 = new Character("geom-dash-special-icon.jpg", true);
		Character char18 = new Character("geom-dash-very-angry-icon.jpg", true);
		Character char19 = new Character("geom-dash-yellow-icon.png", true);
		Character char20 = new Character("geom-dash-gatekeeper-query-icon.jpg", true);
		Character char21 = new Character("one.png", true);
		Character char22 = new Character("two.png", true);
		Character char23 = new Character("three.png", true);
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
