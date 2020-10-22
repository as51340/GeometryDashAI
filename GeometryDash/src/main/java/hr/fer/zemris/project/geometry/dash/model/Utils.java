package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Loads icon and media player from memory
 * @author Andi Škrgat
 *
 */
public class Utils {

	/**
	 * Loads character icon from the resources
	 * @param uri path to the characters
	 * @return loaded icon
	 */
	public static Image loadIcon(String uri){
		String path = GameConstants.pathToIcons + uri;
		InputStream is = Character.class.getResourceAsStream(path);
		if(is == null) {
			throw new NoSuchElementException("No such icon in the resource directory");
		} 
		Image icon = new Image(is,GameConstants.iconWidth, GameConstants.iconHeight, false, true);
		try {
			is.close();
		} catch(IOException e) {
			System.out.println("Cannot close input stream." );
		}
		return icon;
	}
	


	/**
	 * Creates media player from given song
	 * @param songName song name
	 * @return created media player
	 */
	public static MediaPlayer createMediaPlayer(String songName) {
		Media media = new Media(Utils.class.getResource(GameConstants.pathToSongs + songName).toExternalForm());
//		Media media = new Media(new File("C:/Users/Korisnik/Desktop/Projekt/GeometryDash/src/main/resources/hr/fer/zemris/project/geom/dash/settings/songs/BlahBlahBlah.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		return mediaPlayer;
	}
}
