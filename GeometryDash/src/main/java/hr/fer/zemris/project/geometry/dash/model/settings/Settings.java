package hr.fer.zemris.project.geometry.dash.model.settings;

import hr.fer.zemris.project.geometry.dash.model.settings.music.BackgroundMusicPlayer;
import hr.fer.zemris.project.geometry.dash.model.settings.music.LevelMusicPlayer;
import hr.fer.zemris.project.geometry.dash.model.settings.music.MusicSettings;

/**
 * Game settings
 * @author Andi Škrgat
 *
 */
public class Settings {
	
	/**
	 * Game options
	 */
	private Options options;
	
	/**
	 * Reference to the background music player
	 */
	private BackgroundMusicPlayer backgroundMusicPlayer;
	
		
	/**
	 * Initializes account and options
	 */
	public Settings() {
		options = new Options();
		backgroundMusicPlayer = new BackgroundMusicPlayer();
	}

	/**
	 * @return the backgroundMusicPlayer
	 */
	public BackgroundMusicPlayer getBackgroundMusicPlayer() {
		return backgroundMusicPlayer;
	}
	
	
	/**
	 * @return the options
	 */
	public Options getOptions() {
		return options;
	}
	

}
