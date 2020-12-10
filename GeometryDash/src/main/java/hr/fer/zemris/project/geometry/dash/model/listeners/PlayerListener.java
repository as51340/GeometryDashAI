package hr.fer.zemris.project.geometry.dash.model.listeners;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.settings.Options;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Interested in changes of the player
 * @author Andi �krgat
 *
 */
public interface PlayerListener{

	/**
	 * Player is in the air
	 */
	void playerIsInAir();
	
	/**
	 * Player is on the platform
	 */
	void playerIsOnPlatform();
	
	/**
	 * Player is on the floor
	 */
	void playerIsOnFloor();

	/**
	 * Player is dead
	 * @throws IOException 
	 */
	void playerIsDead(Options options, GameEngine gameEngine, double time) throws IOException;
	
	/**
	 * Performs action when player jumps
	 */
	void playerJumped();
	
}
