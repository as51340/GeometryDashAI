package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;
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
	public static Image loadIcon(String uri, int width, int height){
		String path = GameConstants.pathToIcons + uri;
		return loadPhoto(path, width, height);
	}
	

	/**
	 * Loads photo from the resources
	 * @param uri path to the photo
	 * @return loaded photo
	 */
	public static Image loadStatic(String uri, int width, int height){
		String path = GameConstants.pathToStatic + uri;
		return loadPhoto(path, width, height);
	}
	
	/**
	 * Loads photo from resources
	 * @param path path to photo
	 * @param width wanted width of the photo
	 * @param height wanted height of the photo
	 * @return loaded photo
	 */
	private static Image loadPhoto(String path, int width, int height) {
		InputStream is = CharacterObject.class.getResourceAsStream(path);
		if(is == null)
			throw new NoSuchElementException("No such photo in the resource directory");
		
		Image icon = new Image(is, width, height, false, true);
		try {
			is.close();
		} catch(IOException e) {
			System.out.println("Cannot close input stream.");
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
