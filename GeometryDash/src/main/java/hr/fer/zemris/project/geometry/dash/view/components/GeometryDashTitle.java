package hr.fer.zemris.project.geometry.dash.view.components;

import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Ivan Sabolic
 *
 */
public class GeometryDashTitle extends Pane{
	private Text text;
	
	/**
	 * Creates Title with its settings 
	 * @param name
	 */
	public GeometryDashTitle(String name) {
		text = 	new Text(name);

		InputStream fontStream = getClass().getClassLoader().getResourceAsStream("hr/fer/zemris/project/geom/dash/fonts/OXYGENE1.ttf");
		text.setFont(Font.loadFont(fontStream, 70));
		text.setFill(javafx.scene.paint.Color.GREEN);
		
		DropShadow shadow = new DropShadow();
		shadow.setRadius(4);
		text.setEffect(shadow);
		
		getChildren().addAll(text);
	}
	
	public double getTitleWidth() {
        return text.getLayoutBounds().getWidth();
    }

    public double getTitleHeight() {
        return text.getLayoutBounds().getHeight();
    }
}
