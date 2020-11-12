package hr.fer.zemris.project.geometry.dash.model.listeners;

/**
 * Interested in changes of the player
 * @author Andi Škrgat
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
	void playerIsDead();
	
}
