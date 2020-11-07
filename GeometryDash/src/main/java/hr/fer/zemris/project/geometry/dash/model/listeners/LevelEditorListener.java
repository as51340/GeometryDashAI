package hr.fer.zemris.project.geometry.dash.model.listeners;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import javafx.scene.image.Image;

/**
 * Remembers what icon is currently selected on level editor
 * @author Andi Škrgat
 *
 */
public interface LevelEditorListener {

	void newObjectSelected(GameObject gameObject);
	
	void newColorSelected(String color);

}
