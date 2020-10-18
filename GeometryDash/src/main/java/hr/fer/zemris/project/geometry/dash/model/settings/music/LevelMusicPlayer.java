package hr.fer.zemris.project.geometry.dash.model.settings.music;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelMusicPlayer {

	/**
	 * Maps song to level
	 */
	private Map<String, MediaPlayer> levelMediaPlayer;
	
	/**
	 * Reference to the music settings
	 */
	private MusicSettings musicSettings;

	/**
	 * @return the levelsSongs
	 */
	public Map<String, MediaPlayer> getLevelMediaPlayer() {
		return levelMediaPlayer;
	}

	/**
	 * Loads all media players that will be used in levels and creates music settings
	 */
	public LevelMusicPlayer() {
		levelMediaPlayer = loadLevelMediaPlayer();
		musicSettings = MusicSettings.getInstance();
	}

	/**
	 * Map level and songs
	 * 
	 * @return map filled with level and songs
	 */
	private Map<String, MediaPlayer> loadLevelMediaPlayer() {
		levelMediaPlayer = new HashMap<String, MediaPlayer>();
		levelMediaPlayer.put("Stereo Madness", Utils.createMediaPlayer("BlahBlahBlah.mp3"));
		levelMediaPlayer.put("Back On Track", Utils.createMediaPlayer("InMyMind.mp3"));
		levelMediaPlayer.put("Polargeist", Utils.createMediaPlayer("PrayerInC.mp3"));
		levelMediaPlayer.put("Dry Out", Utils.createMediaPlayer("Melody.mp3"));
		levelMediaPlayer.put("Base After Base", Utils.createMediaPlayer("Firestone.mp3"));
		levelMediaPlayer.put("Cant Let Go", Utils.createMediaPlayer("SweetDreams.mp3"));
		levelMediaPlayer.put("Jumper", Utils.createMediaPlayer("OceanDrive.mp3"));
		levelMediaPlayer.put("Time Machine", Utils.createMediaPlayer("MoreThanYouKnow.mp3"));
		levelMediaPlayer.put("Cycles", Utils.createMediaPlayer("HappyNow.mp3"));
		levelMediaPlayer.put("XStep", Utils.createMediaPlayer("AreYouWithMe.mp3"));		
		return levelMediaPlayer;
	}

	/**
	 * @return the level songs
	 */
	public Set<String> getSongNames() {
		return levelMediaPlayer.keySet();
	}

	/**
	 * Adds new mapping, level -> song
	 * @param levelName level name
	 * @param songName  song name
	 * 
	 */
	public void addLevelSong(String levelName, String songName) {
		levelMediaPlayer.put(levelName, Utils.createMediaPlayer(songName));
	}
	
	/**
	 * Sets song to specific level
	 * @param levelName level name
	 * @param songName song name
	 */
	public void setLevelSong(String levelName, String songName) {
		addLevelSong(levelName, songName);	
	}
}
