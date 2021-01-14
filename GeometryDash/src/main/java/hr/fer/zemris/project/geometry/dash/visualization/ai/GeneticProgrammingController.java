package hr.fer.zemris.project.geometry.dash.visualization.ai;

import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Collections;

public class GeneticProgrammingController extends AIControllers {


    
    public TextField populationSizeField;
    public TextField maxDepthField;
    public TextField MaxInitDepthField;

    @FXML
    private ComboBox<String> typeBox;

    private final String[] selectionItems = new String[] {"Roullete", "Classic"};
    public void init() {
        super.init();

        typeBox.setItems(FXCollections.observableArrayList(selectionItems));
    }

    @FXML
    public void trainNetwork(ActionEvent event) throws IOException, InterruptedException  {
        int populationSize = -1;
        int maxDepth = -1;
        int maxInitDepth = -1;
        try {
            populationSize = Integer.parseInt(populationSizeField.getText());
            maxDepth = Integer.parseInt(maxDepthField.getText());
            maxInitDepth = Integer.parseInt(MaxInitDepthField.getText());
        } catch (NumberFormatException e) {
            populationSizeField.setText("int required");
        }

        Level chosenLevel = getLevel(levelBox.getValue());
        String selection = typeBox.getValue();
        
    }
    @FXML
    public void stopTrainNetwork(ActionEvent event) {

    }
}
