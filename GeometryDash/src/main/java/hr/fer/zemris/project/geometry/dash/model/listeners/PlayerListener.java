package hr.fer.zemris.project.geometry.dash.model.listeners;

import hr.fer.zemris.project.geometry.dash.model.settings.Options;

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
	 */
	void playerIsDead(Options options);
	
	/**
	 * Performs action when player jumps
	 */
	void playerJumped();
	
}
