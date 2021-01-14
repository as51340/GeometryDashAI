package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChoosePlayerController extends MenuController {

    @FXML
    private Button playerButton;

    @FXML
    private Button AIButton;
    
    /**
     * Remembers which playing mode was selected
     */
    private PlayingMode playingModeSelected;
    
    @FXML
    void onAIClicked(MouseEvent event) throws IOException {
    	this.playingModeSelected = GameEngine.getInstance().getSettings().getOptions().getAIMode();

    	actionForChoosing();

    }

    @FXML
    void onPlayerClicked(MouseEvent event) throws IOException {
    	this.playingModeSelected = PlayingMode.HUMAN;
    	actionForChoosing();
    }
    
    /**
     * Common action for both buttons
     * @throws IOException 
     */
    private void actionForChoosing() throws IOException {
    	FXMLLoader loader = new FXMLLoader(
        		ChoosePlayerController.class.getResource(GameConstants.pathToVisualization + "level/ChooseLevelScene.fxml")
        	);
    	loader.load();
    	Stage stage = GeometryDash.getStage();
    	stage.setUserData(playingModeSelected);
    	ChooseLevelController controller = loader.getController();
    	controller.setPreviousSceneRoot(rootPane);
    }
    

}
