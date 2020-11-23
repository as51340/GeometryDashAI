package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class BackgroundSceneController {

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


	/**
	 * Reference to the game engine
	 */
	private GameEngine gameEngine;

	
    @FXML
    private void settingsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "SettingsScene.fxml")
    	);
    	loader.load();
    	SettingsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    	controller.setGameEngine(gameEngine);
    }
    
    @FXML
    private void achievementsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "AchievementsScene.fxml")
    	);
    	loader.load();
    	AchievementsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    	controller.setGameEngine(gameEngine);
    }
    
    @FXML
    private void statsButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    		getClass().getResource(GameConstants.pathToVisualization + "StatsScene.fxml")
    	);
    	loader.load();
    	StatsSceneController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    	controller.setGameEngine(gameEngine);
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
    private void playButtonClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(
    			getClass().getResource(GameConstants.pathToVisualization + "ChooseLevelScene.fxml")
    	);
    	loader.load();
    	ChooseLevelController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    	controller.setGameEngine(gameEngine);
    }

	@FXML
	public void initialize() {
		Utils.animateBackground(overlay, background1, background2, background3);
	}
	
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
}
