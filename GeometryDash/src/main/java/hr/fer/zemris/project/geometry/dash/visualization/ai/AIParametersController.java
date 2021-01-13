package hr.fer.zemris.project.geometry.dash.visualization.ai;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.settings.Options;
import hr.fer.zemris.project.geometry.dash.visualization.ChoosePlayerController;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class AIParametersController extends MenuController {

    @FXML
    Label network;
    @FXML
    Label hiddenLayersLabel;
    @FXML
    TextField hiddenLayers;
    @FXML
    TextField numberPerHiddenLayer;
    @FXML
    Button continueButton;

    private final int NETWORK_ADJUST = 100;


    private PlayingMode playingMode;

    public void init() {
        playingMode = GameEngine.getInstance().getSettings().getOptions().getAIMode();
        network.setText(String.join(" ", String.valueOf(playingMode).split("_")));

        if(playingMode == PlayingMode.ELMAN_NEURAL_NETWORK) {
            hiddenLayersLabel.setVisible(false);
            hiddenLayers.setVisible(false);
            network.setTranslateX(network.getTranslateX() - NETWORK_ADJUST);
        }
    }

    /**
     * Sprema opcije mreže u options
     * @throws IOException
     */
    @FXML
    public void onContinueButton() throws IOException {
        if(hiddenLayers.getText().isEmpty()) {
            hiddenLayers.setText("Required");
            return;
        }

        if(numberPerHiddenLayer.getText().isEmpty()) {
            numberPerHiddenLayer.setText("Required");
            return;
        }

        Options options = GameEngine.getInstance().getSettings().getOptions();
        try{
            if(playingMode == PlayingMode.NEURAL_NETWORK)
                options.setNumberOfHiddenLayers(Integer.parseInt(hiddenLayers.getText()));
            else
                options.setNumberOfHiddenLayers(0);
            options.setNumberPerHiddenLayer(Integer.parseInt(numberPerHiddenLayer.getText()));
        } catch (NumberFormatException e) {
            hiddenLayers.setText("Please provide an integer");
            numberPerHiddenLayer.setText("Please provide an integer");
            return;
        }

        ChoosePlayerController.actionForChoosing(playingMode, this.rootPane);
    }
}
