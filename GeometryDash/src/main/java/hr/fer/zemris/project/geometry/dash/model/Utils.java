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
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * Loads icon and media player from memory
 * 
 * @author Andi äkrgat
 * 
 */
public class Utils {
	
	private static final int BACKGROUND_WIDTH = 800;
	private static final int BACKGROUND_TRANSITION_DURATION = 30000;
	private static final int COLOR_TRANSITION_DURATION = 7000;

	/**
	 * Loads character icon from the resources 
	 * @param uri path to the characters
	 * @return loaded icon
	 */
	public static Image loadIcon(String uri, int width, int height){
		return loadPhoto(uri, width, height);
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
	 * Copies image
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

//	/**
//	 * Creates object from given object name
//	 * @param objName object name
//	 * @param uriIcon path to icon
//	 * @return created {@linkplain GameObject}
//	 */
//	public static GameObject createObjectFromName(String objName, Vector2D objPosition, int height, int width, String uriIcon) {
//		if(objName.equals("Block") == true) {
//			return createBlock(objPosition, height, width, uriIcon);
//		} else if(objName.equals("Platform") == true) {
//			return createPlatform(objPosition, height, width, uriIcon);
//		} else if(objName.equals("Spike") == true) {
//			return createSpike(objPosition, height, width, uriIcon);
//		} else if(objName.equals("GrassSpike") == true) {
//			return createGrassSpike(objPosition, height, width, uriIcon);
//		}
//		return null;
//	}

	/**
	 * Creates object from given object name
	 * @param objName object name
	 * @param uriIcon path to icon
	 * @return created {@linkplain GameObject}
	 */
	public static GameObject createObjectFromName(String objName, Vector2D objPosition, int height, int width, String uriIcon) {
		GameObject retObject;
		switch (objName) {
			case "Block" -> retObject = createBlock(objPosition, height, width, uriIcon);
			case "Platform" -> retObject = createPlatform(objPosition, height, width, uriIcon);
			case "Spike" -> retObject = createSpike(objPosition, height, width, uriIcon);
			case "GrassSpike" -> retObject = createGrassSpike(objPosition, height, width, uriIcon);
			// TODO case "Floor"
			default -> throw new IllegalArgumentException("Unsupported object type!");
			// prije je bilo return null ali mislim da ne bi trebalo biti prihvatljivo
			// da se kreacija objekta s nepoznatim imenom samo pre≈°uti
		}

		return retObject;
	}
	
	/**
	 * Creates grassspike
	 * @param uriIcon path to icon
	 * @return created {@linkplain GrassSpike}
	 */
	private static GrassSpike createGrassSpike(Vector2D objPosition, int height, int width, String uriIcon) {
		return new GrassSpike("GrassSpike", objPosition, height, width, uriIcon);
	}
	
	/**
	 * Creates spike
	 * @param uriIcon path to icon
	 * @return created {@linkplain Spike}
	 */
	private static Spike createSpike(Vector2D objPosition, int height, int width, String uriIcon) {
		return new Spike("Spike", objPosition, height, width, uriIcon);
	}
	
	/**
	 * Creates platform
	 * @param uriIcon path to icon
	 * @return created {@linkplain Platform}
	 */
	private static Platform createPlatform(Vector2D objPosition, int height, int width, String uriIcon) {
		return new Platform("Platform", objPosition, height, width,uriIcon);
	}
	
	/**
	 * Creates block 
	 * @param uriIcon path to icon
	 * @return created {@linkplain Block}
	 */
	private static Block createBlock(Vector2D objPosition, int height, int width, String uriIcon) {
		return new Block("Block", objPosition, height, width, uriIcon);
	}
	
	/**
	 * Animates background moving
	 * @param overlay color overlay
	 * @param background1 first background
	 * @param background2 second background
	 * @param background3 third background
	 */
	public static void animateBackground(Shape overlay, ImageView background1, ImageView background2, ImageView background3) {
		ParallelTransition backgroundCyclingAnimation = new ParallelTransition(
			createBackgroundTransition(background1), 
			createBackgroundTransition(background2), 
			createBackgroundTransition(background3)
		);
		backgroundCyclingAnimation.setCycleCount(Animation.INDEFINITE);
		backgroundCyclingAnimation.play();
		
		SequentialTransition colorCyclingAnimation = new SequentialTransition(
			createColorTransition(overlay, Color.DODGERBLUE, Color.BLUEVIOLET),
			createColorTransition(overlay, Color.BLUEVIOLET, Color.PURPLE),
			createColorTransition(overlay, Color.PURPLE, Color.CRIMSON),
			createColorTransition(overlay, Color.CRIMSON, Color.ORANGE), 
			createColorTransition(overlay, Color.ORANGE, Color.YELLOWGREEN), 
			createColorTransition(overlay, Color.YELLOWGREEN, Color.LIGHTGREEN),
			createColorTransition(overlay, Color.LIGHTGREEN, Color.CYAN),
			createColorTransition(overlay, Color.CYAN, Color.DODGERBLUE)
		);
		colorCyclingAnimation.setCycleCount(Animation.INDEFINITE);
		colorCyclingAnimation.play();
	}
	
	/** Creates background transition
	 * @param background background to translate
	 * @return TranslateTransition for sent background
	 */
	private static TranslateTransition createBackgroundTransition(Node background) {
		TranslateTransition transition = new TranslateTransition(
			Duration.millis(BACKGROUND_TRANSITION_DURATION), background
		);
		transition.setFromX(0);
		transition.setToX(-BACKGROUND_WIDTH);
		transition.setInterpolator(Interpolator.LINEAR);
		return transition;
	}
	
	/** Creates color transition
	 * @param shape shape to change color
	 * @param fromColor transition from this color
	 * @param toColor transition to this color
	 * @return
	 */
	private static FillTransition createColorTransition(Shape shape, Color fromColor, Color toColor) {
		return new FillTransition(
			Duration.millis(COLOR_TRANSITION_DURATION), 
			shape, 
			fromColor, 
			toColor
		);
	}
}
