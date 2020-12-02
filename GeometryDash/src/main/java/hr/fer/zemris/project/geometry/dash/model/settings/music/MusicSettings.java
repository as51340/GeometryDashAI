package hr.fer.zemris.project.geometry.dash.model.settings.music;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.media.*;

/**
 * Stores information about current media player, volume... Implemented Singleton design pattern
 * @author Andi Škrgat
 *
 */
public class MusicSettings {
	
	/**
	 * Reference to the instance
	 */
	private static final MusicSettings SINGLE_INSTANCE = new MusicSettings();
	
	/**
	 * Music's volume, in {@linkplain Media} volume is in range from [0.0, 1.0], default is on 0.5
	 */
	private double volume = 0.5;
		
	/**
	 * Currently active media player
	 */
	private MediaPlayer currMediaPlayer;
	
	public static MusicSettings getInstance() {
		return SINGLE_INSTANCE;
	}
	
	/**
	 * Constructor is private because we use singleton design pattern
	 */
	private MusicSettings() {
	}
	
	/**
	 * @return currently active media player
	 */
	public MediaPlayer getCurrMediaPlayer() {
		return currMediaPlayer;
	}

	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		this.volume = volume;
		if(currMediaPlayer != null) {
			currMediaPlayer.setVolume(volume);
		}
	}

	/**
	 * @param currMediaPlayer the currMediaPlayer to set
	 */
	public void setCurrMediaPlayer(MediaPlayer currMediaPlayer) {
		currMediaPlayer.stop();
		this.currMediaPlayer = currMediaPlayer;
		currMediaPlayer.play();
	}
	
}
