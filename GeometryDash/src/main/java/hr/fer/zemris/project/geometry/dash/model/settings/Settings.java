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
	 * Account options
	 */
	private Account account;
	
	/**
	 * Game options
	 */
	private Options options;
	
	/**
	 * Reference to the background music player
	 */
	private BackgroundMusicPlayer backgroundMusicPlayer;
	
	
	/**
	 * Reference to the level music player
	 */
	private LevelMusicPlayer levelMusicPlayer;
		
	/**
	 * Initializes account and options
	 */
	public Settings() {
		account = new Account();
		options = new Options();
		backgroundMusicPlayer = new BackgroundMusicPlayer();
		backgroundMusicPlayer.startPlayingSongs();
		levelMusicPlayer = new LevelMusicPlayer();
	}

	/**
	 * @return the backgroundMusicPlayer
	 */
	public BackgroundMusicPlayer getBackgroundMusicPlayer() {
		return backgroundMusicPlayer;
	}

	/**
	 * @return the levelMusicPlayer
	 */
	public LevelMusicPlayer getLevelMusicPlayer() {
		return levelMusicPlayer;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @return the options
	 */
	public Options getOptions() {
		return options;
	}
	

}
