package hr.fer.zemris.project.geometry.dash.model.listeners;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import javafx.scene.canvas.GraphicsContext;

/**
 * Specifies what has to be changed in game model when player enters or exits from some mode of game
 * @author Andi Škrgat
 *
 */
public interface GameStateListener {
	
	/**
	 * Entered in level editor
	 * @param graphicsContext
	 */
	void levelEditorModeEntered(GraphicsContext graphicsContext);
	
	/**
	 * Exited from level editor
	 */
	void levelEditorModeExited();
	
	/**
	 * Entered in characters selector
	 */
	void characterSelectorModeEntered();
	
	/**
	 * Exited from characters selector
	 */
	void characterSelectorModeExited();
	
	/**
	 * Started normal mode
	 */
	void normalModePlayingStarted();
	
	/**
	 * Exited from normal mode
	 */
	void normalModePlayingExited();
}
