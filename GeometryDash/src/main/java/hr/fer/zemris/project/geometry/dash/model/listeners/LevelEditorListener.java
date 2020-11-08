package hr.fer.zemris.project.geometry.dash.model.listeners;

import java.util.Set;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import javafx.scene.image.Image;

/**
 * Remembers what icon is currently selected on level editor
 * @author Andi Škrgat
 *
 */
public interface LevelEditorListener {

	/**
	 * Action that is done when user selects new obstacle to place it on the grid
	 * @param gameObject
	 */
	void newObjectSelected(GameObject gameObject);
	
	/**
	 * Specifies action that will be done when user selects new color
	 * @param color
	 */
	void newColorSelected(String color);
	
	/**
	 * Saves level to the zip file
	 */
	void saveLevel(String fileToSave);
	
	/**
	 * Loads level
	 */
	void loadLevel();
	
	/**
	 * Resets current level
	 */
	void reset();

}
