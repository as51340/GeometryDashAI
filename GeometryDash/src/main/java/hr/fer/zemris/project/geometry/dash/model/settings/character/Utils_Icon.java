package hr.fer.zemris.project.geometry.dash.model.settings.character;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import javafx.scene.image.Image;

public class Utils_Icon {

	/**
	 * Sets icon's width
	 */
	private static final short iconWidht = 30;
	
	/**
	 * Sets icon's height
	 */
	private static final short iconHeight = 30;
	
	/**
	 * Loads character icon from the resources
	 * @param uri path to the characters
	 * @return loaded icon
	 */
	public static Image loadIcon(String uri){
		String pathPrefix = "../../../player/icons/";
		InputStream is = Character.class.getResourceAsStream(pathPrefix + uri);
		if(is == null) {
			throw new NoSuchElementException("No such icon in the resource directory");
		} 
		Image icon = new Image(is,iconWidht, iconHeight, false, true);
		try {
			is.close();
		} catch(IOException e) {
			System.out.println("Cannot close input stream." );
		}
		return icon;
		
	}
}
