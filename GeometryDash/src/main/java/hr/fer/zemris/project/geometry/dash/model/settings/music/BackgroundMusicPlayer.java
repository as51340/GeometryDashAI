package hr.fer.zemris.project.geometry.dash.model.settings.music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Background music player, this music will only be player on the game menu
 * @author Andi Škrgat
 *
 */
public class BackgroundMusicPlayer{

	/**
	 * Playing property
	 */
	private boolean isPlaying = false;

	/**
	 * List of all media players
	 */
	private List<MediaPlayer> mediaPlayers;

	/**
	 * Reference to the music settings
	 */
	private MusicSettings musicSettings;

	/**
	 * Song index in the list of media players
	 */
	private int songIndex = 0;

	/**
	 * Initializes all media players
	 */
	public BackgroundMusicPlayer() {
		System.out.println("Stvoren novi background music player");
		mediaPlayers = new ArrayList<MediaPlayer>();
		musicSettings = MusicSettings.getInstance();
		loadBackgroundMusicPlayers();
		int playersSize = mediaPlayers.size();
		for(int i = 0; i < playersSize; i++) {
			MediaPlayer player = mediaPlayers.get(i);			
			MediaPlayer nextPlayer = mediaPlayers.get((i + 1) % playersSize);
			player.setOnEndOfMedia(() -> {
				player.stop();
				musicSettings.setCurrMediaPlayer(nextPlayer);
				nextPlayer.play();
				songIndex++;
			});
		}
		MediaPlayer firstMediaPlayer = mediaPlayers.get(songIndex);
		musicSettings.setCurrMediaPlayer(firstMediaPlayer);
		isPlaying = true;
		firstMediaPlayer.play();
	}

	/**
	 * Loads songs
	 */
	private void loadBackgroundMusicPlayers() {
		mediaPlayers.add(Utils.createMediaPlayer("InMyMind.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("BlahBlahBlah.mp3"));
		mediaPlayers.add(Utils.createMediaPlayer("PrayerInC.mp3"));
		mediaPlayers.add(Utils.createMediaPlayer("Melody.mp3"));
		mediaPlayers.add(Utils.createMediaPlayer("Firestone.mp3"));
		mediaPlayers.add(Utils.createMediaPlayer("OceanDrive.mp3"));
		mediaPlayers.add(Utils.createMediaPlayer("MoreThanYouKnow.mp3"));
		mediaPlayers.add(Utils.createMediaPlayer("HappyNow.mp3"));
		mediaPlayers.add(Utils.createMediaPlayer("AreYouWithMe.mp3"));
	}

	/**
	 * Starts background music, or continues where it was paused
	 * 
	 * @return
	 */
	public void start() {
		if (isPlaying == false) {
			isPlaying = true;
			musicSettings.getCurrMediaPlayer().play();
		}
	}

	/**
	 * Stops background music
	 */
	public void stop() {
		if (isPlaying == true && musicSettings.getCurrMediaPlayer() != null) {
			isPlaying = false;
			musicSettings.getCurrMediaPlayer().pause();
		}
	}

	/**
	 * Jumps to the next song on the list
	 */
	public void next() {
		if(musicSettings.getCurrMediaPlayer() == null) {
			return;
		}
		songIndex = (songIndex + 1) % mediaPlayers.size();
		musicSettings.getCurrMediaPlayer().stop();
		musicSettings.setCurrMediaPlayer(mediaPlayers.get(songIndex));
		if(isPlaying == true) {
			musicSettings.getCurrMediaPlayer().play();	
		}
	}
}
