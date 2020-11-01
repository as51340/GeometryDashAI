package hr.fer.zemris.project.geometry.dash.model.listeners;

import javafx.scene.image.Image;

/**
 * Remembers what icon is currently selected on level editor
 * @author Andi Škrgat
 *
 */
public interface LevelEditorListener {

	void newActionSelected(Image newImage, String objectName);
	
}
