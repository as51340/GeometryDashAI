package hr.fer.zemris.project.geometry.dash.model.settings.music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class BackgroundMusicPlayer {

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
		mediaPlayers = new ArrayList<MediaPlayer>();
		musicSettings = MusicSettings.getInstance();
		loadBackgroundMusicPlayers();
	}

	private void loadBackgroundMusicPlayers() {
		mediaPlayers.add(Utils.createMediaPlayer("InMyMind.mp3"));
		mediaPlayers.add(Utils.createMediaPlayer("BlahBlahBlah.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("PrayerInC.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("Melody.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("Firestone.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("SweetDreams.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("OceanDrive.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("MoreThanYouKnow.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("HappyNow.mp3"));
//		mediaPlayers.add(Utils.createMediaPlayer("AreYouWithMe.mp3"));
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
		songIndex = (songIndex + 1) % mediaPlayers.size();
		musicSettings.getCurrMediaPlayer().stop();
		musicSettings.setCurrMediaPlayer(mediaPlayers.get(songIndex));
		musicSettings.getCurrMediaPlayer().play();
	}

	/**
	 * Plays songs in background in its own thread TODO Pitaj cupica
	 */
	public void startPlayingSongs() {
		BackgroundThreadFactory threadFactory = new BackgroundThreadFactory();
		int playersSize = mediaPlayers.size();
		threadFactory.newThread(() -> {
			boolean first = true;
			while(true) {
				MediaPlayer player = mediaPlayers.get(songIndex);
				songIndex = (songIndex + 1) % playersSize;
				MediaPlayer nextPlayer = mediaPlayers.get(songIndex);
				if (first == true) {
					first = false;
					musicSettings.setCurrMediaPlayer(player);
					player.setStartTime(Duration.minutes(2));
					//player.play();
				}
				player.setOnEndOfMedia(() -> {
					musicSettings.setCurrMediaPlayer(nextPlayer);
					nextPlayer.setStartTime(Duration.minutes(2));
					nextPlayer.seek(Duration.ZERO);
					//nextPlayer.play();
				});
			}
		}).start();
		;
	}

	/**
	 * Thread factory for creating daemon threads
	 * 
	 * @author Andi Škrgat
	 *
	 */
	private class BackgroundThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread musicThread = new Thread(r);
			musicThread.setDaemon(true);
			return musicThread;
		}

	}
}
