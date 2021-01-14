package hr.fer.zemris.project.geometry.dash.visualization.ai;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ElmanNeuralNetworkController extends AIControllers {

    @FXML
    private TextField numberPerHiddenLayerField;



    @Override
    public void trainNetwork(ActionEvent event) throws IOException, InterruptedException  {
        if(numberPerHiddenLayerField.getText().isEmpty()) {
            numberPerHiddenLayerField.setText("Required");
            return;
        }

        int numberPerHiddenLayer = -1;
        try {
            numberPerHiddenLayer = Integer.parseInt(numberPerHiddenLayerField.getText());
        } catch (NumberFormatException ex) {
            numberPerHiddenLayerField.setText("Requires int");
            return;
        }

        Level chosenLevel = getLevel(levelBox.getValue());

    };

    @Override
    public void stopTrainNetwork(ActionEvent event) {};


}
