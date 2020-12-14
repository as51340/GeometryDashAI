package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.settings.controllers.SongsSceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class ChoosePlayerController extends MenuController {

    @FXML
    private Button playerButton;

    @FXML
    private Button AIButton;
    
    @FXML
    void onAIClicked(MouseEvent event) throws IOException {
    	GameEngine.getInstance().setGameWorld();
    	GameEngine.getInstance().getGameWorld().getPlayerListener().playerCreated(
    			GameEngine.getInstance().getSettings()
    			.getOptions().getAIMode());
    	actionForChoosing();
    }

    @FXML
    void onPlayerClicked(MouseEvent event) throws IOException {
    	GameEngine.getInstance().setGameWorld();
    	GameEngine.getInstance().getGameWorld().getPlayerListener().playerCreated(PlayingMode.HUMAN);
    	actionForChoosing();
    }
    
    /**
     * Common action for both buttons
     * @throws IOException 
     */
    private void actionForChoosing() throws IOException {
    	FXMLLoader loader = new FXMLLoader(
        		getClass().getResource(GameConstants.pathToVisualization + "level/ChooseLevelScene.fxml")
        	);
    	loader.load();
    	ChooseLevelController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
    

}
