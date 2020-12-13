package hr.fer.zemris.project.geometry.dash.visualization.ai;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.CharacterSelectController;
import hr.fer.zemris.project.geometry.dash.visualization.MainOptionsController;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

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


}
