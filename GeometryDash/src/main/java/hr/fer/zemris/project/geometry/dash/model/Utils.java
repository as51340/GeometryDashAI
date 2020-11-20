package hr.fer.zemris.project.geometry.dash.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * Loads icon and media player from memory
 * @author Andi Škrgat
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
