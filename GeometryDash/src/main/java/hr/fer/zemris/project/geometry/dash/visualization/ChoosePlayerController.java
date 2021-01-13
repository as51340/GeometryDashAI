package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.ai.AIParametersController;
import hr.fer.zemris.project.geometry.dash.visualization.settings.controllers.SongsSceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
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

    	if(playingModeSelected == PlayingMode.GENETIC_PROGRAMMING)
    	    actionForChoosing(this.playingModeSelected, this.rootPane);
    	else {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(GameConstants.pathToVisualization + "AI/AIParameters.fxml")
            );
            loader.load();
            Stage stage = GeometryDash.getStage();
            stage.setUserData(playingModeSelected);
            AIParametersController controller = loader.getController();
            controller.init();
            controller.setPreviousSceneRoot(rootPane);
        }
    }

    @FXML
    void onPlayerClicked(MouseEvent event) throws IOException {
    	this.playingModeSelected = PlayingMode.HUMAN;
    	actionForChoosing(this.playingModeSelected, this.rootPane);
    }
    
    /**
     * Common action for both buttons
     * @throws IOException 
     */
    public static void actionForChoosing(PlayingMode playingModeSelected, StackPane rootPane) throws IOException {
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
