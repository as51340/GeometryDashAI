package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class BackgroundSceneController {

	private static final int BACKGROUND_WIDTH = 800;
	private static final int BACKGROUND_TRANSITION_DURATION = 30000;
	private static final int COLOR_TRANSITION_DURATION = 7000;

	@FXML
	private ImageView background1;

	@FXML
	private ImageView background2;

	@FXML
	private ImageView background3;
	
	@FXML
	private Rectangle overlay;
	
    @FXML
    private ImageView playButton;

    @FXML
    private ImageView charactersButton;

    @FXML
    private ImageView achievementsButton;

    @FXML
    private ImageView settingsButton;

    @FXML
    private ImageView statsButton;
    
    @FXML
    private StackPane rootPane;
    
    @FXML
    private void settingsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "SettingsScene.fxml")
    	);
    	loader.load();
    	SettingsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
    
    @FXML
    private void achievementsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "AchievementsScene.fxml")
    	);
    	loader.load();
    	AchievementsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
    
    @FXML
    private void statsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "StatsScene.fxml")
    	);
    	loader.load();
    	StatsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
    
    @FXML
    private void characterSelectButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "CharacterSelectScene.fxml")
    	);
    	loader.load();
    	CharacterSelectController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }

	@FXML
	public void initialize() {
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
	
	private static TranslateTransition createBackgroundTransition(Node background) {
		TranslateTransition transition = new TranslateTransition(
			Duration.millis(BACKGROUND_TRANSITION_DURATION), background
		);
		transition.setFromX(0);
		transition.setToX(-BACKGROUND_WIDTH);
		transition.setInterpolator(Interpolator.LINEAR);
		return transition;
	}
	
	private static FillTransition createColorTransition(Shape shape, Color fromColor, Color toColor) {
		return new FillTransition(
			Duration.millis(COLOR_TRANSITION_DURATION), 
			shape, 
			fromColor, 
			toColor
		);
	}

}
