package hr.fer.zemris.project.geometry.dash.model.settings.music;

/**
 * Sound system interface. If object implements this interface it can play sound on required actions.
 * @author Korisnik
 *
 */
public interface SoundSystem {
	
	/**
	 * When player dies, this sound will be played
	 */
	void playKillSound();
	
	

}
