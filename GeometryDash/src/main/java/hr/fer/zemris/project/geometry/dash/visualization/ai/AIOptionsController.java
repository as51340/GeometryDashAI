package hr.fer.zemris.project.geometry.dash.visualization.ai;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.visualization.TreeVisualizationController;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.CharacterSelectController;
import hr.fer.zemris.project.geometry.dash.visualization.ChooseLevelController;
import hr.fer.zemris.project.geometry.dash.visualization.MainOptionsController;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Controller for scene when user selects for which model he wants to change options
 * @author Andi �krgat
 *
 */
public class AIOptionsController extends MenuController{

    @FXML
    private AnchorPane centerPane;

    @FXML
    private Button neuralNetworkGeneticButton;

    @FXML
    private Button elmanNeuralNetworkGeneticButton;

    @FXML
    private Button geneticProgrammingButton;
    
    @FXML
    void artificalNeuralNetworkGeneticAlgClicked(MouseEvent event) throws IOException {
    	 FXMLLoader loader = new FXMLLoader(
                 getClass().getResource(GameConstants.pathToVisualization + "AI/ArtificialNeuralNetwork.fxml")
         );
         loader.load();
         Stage stage = GeometryDash.getStage();
         //stage.setUserData(playingModeSelected);
         ArtificialNeuralNetworkController controller = loader.getController();
         controller.init();
         controller.setPreviousSceneRoot(rootPane);
    }

    @FXML
    void elmanArtificalNeuralNetworkGeneticAlg(MouseEvent event) {
    
    }

    @FXML
    void geneticProgrammingClicked(MouseEvent event) {
    	TreeVisualizationController controller = new TreeVisualizationController();
    	controller.createScene();
    	Scene scene = new Scene(controller.getRoot(), 1280, 720, true);
    	Stage primaryStage = GeometryDash.getStage();
    	primaryStage.setTitle("Genetic programming tree");
    	primaryStage.setScene(scene);
    	primaryStage.show();
    	scene.setCamera(controller.getCamera());
    	
    }


}
