package hr.fer.zemris.project.geometry.dash.model.listeners;

/**
 * Listener for game scene
 * @author Andi Škrgat
 *
 */
public interface GameSceneListener {

	/**
	 * Stop training
	 */
	void stop();
	
	void changeGenerationText(String text);
	
}
