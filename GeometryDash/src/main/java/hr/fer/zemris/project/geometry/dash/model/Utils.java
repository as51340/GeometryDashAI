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
		InputStream is = CharacterObject.class.getResourceAsStream(uri);
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
	
	public static GameObject createObjectFromName(String objName, Image image) {
		if(objName.equals("Block") == true) {
			return createBlock(image);
		} else if(objName.equals("Platform") == true) {
			return createPlatform(image);
		} else if(objName.equals("Spike") == true) {
			return createSpike(image);
		} else if(objName.equals("GrassSpike") == true) {
			return createGrassSpike(image);
		}
		return null;
	}
	
	private static GrassSpike createGrassSpike(Image image) {
		return new GrassSpike(image);
	}
	
	private static Spike createSpike(Image image) {
		return new Spike(image);
	}
	
	private static Platform createPlatform(Image image) {
		return new Platform(image);
	}
	
	private static Block createBlock(Image image) {
		return new Block(image);
	}
}
