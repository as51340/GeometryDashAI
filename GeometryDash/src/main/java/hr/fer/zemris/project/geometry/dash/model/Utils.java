package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Block;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.GrassSpike;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Platform;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Spike;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

/**
 * Loads icon and media player from memory
 * 
 * @author Andi Škrgat
 * 
 */
public class Utils {

	/**
	 * Loads character icon from the resources
	 * 
	 * @param uri path to the characters
	 * @return loaded icon
	 */
	public static Image loadIcon(String uri) {
		String path = GameConstants.pathToIcons + uri;
		InputStream is = CharacterObject.class.getResourceAsStream(path);
		if (is == null) {
			throw new NoSuchElementException("No such icon in the resource directory");
		}
		Image icon = new Image(is, GameConstants.iconWidth, GameConstants.iconHeight, false, true);
		try {
			is.close();
		} catch (IOException e) {
			System.out.println("Cannot close input stream.");
		}
		return icon;
	}

	/**
	 * Copies image
	 * 
	 * @param image old image
	 * @return new copied image
	 */
	public static Image copyImage(Image image) {
		PixelReader pixelReader = image.getPixelReader();

		int width = (int) image.getWidth();
		int height = (int) image.getHeight();

		// Copy from source to destination pixel by pixel
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color color = pixelReader.getColor(x, y);
				pixelWriter.setColor(x, y, color);
			}
		}
		return writableImage;
	}

	/**
	 * Creates media player from given song
	 * 
	 * @param songName song name
	 * @return created media player
	 */
	public static MediaPlayer createMediaPlayer(String songName) {
		Media media = new Media(Utils.class.getResource(GameConstants.pathToSongs + songName).toExternalForm());
//		Media media = new Media(new File("C:/Users/Korisnik/Desktop/Projekt/GeometryDash/src/main/resources/hr/fer/zemris/project/geom/dash/settings/songs/BlahBlahBlah.mp3").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		return mediaPlayer;
	}
	
	public static GameObject createObjectFromName(String objName, Vector2D position) {
		if(objName.equals("Block") == true) {
			return createBlock(position);
		} else if(objName.equals("Platform") == true) {
			return createPlatform(position);
		} else if(objName.equals("Spike") == true) {
			return createSpike(position);
		} else if(objName.equals("GrassSpike") == true) {
			return createGrassSpike(position);
		} 
		return null;
	}
	
	private static GrassSpike createGrassSpike(Vector2D position) {
//		return new 
		return null;
	}
	
	private static Spike createSpike(Vector2D position) {
//		return new Spike(position, GameConstants)
		return null;
	}
	
	private static Platform createPlatform(Vector2D position) {
		return new Platform(position, 45, GameConstants.platformImage);
	}
	
	private static Block createBlock(Vector2D position) {
		return new Block(position, GameConstants.blockImage);
	}
}
