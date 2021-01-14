package hr.fer.zemris.project.geometry.dash.visualization.ai;

import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Action;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AIControllers extends MenuController {

    @FXML
    protected ComboBox<String> levelBox;

    protected List<Level> levels;

    @FXML
    protected Label noLabel;
    @FXML
    protected Label yesLabel;


    public void init() {
        levels = new ArrayList<>(GameEngine.getInstance().getLevelManager().getAllLevels());
        List<String> levelNames = levels.stream().map(Level::getLevelName).collect(Collectors.toList());
        levelBox.setItems(FXCollections.observableList(levelNames));

        yesLabel.setVisible(false);
    }

    @FXML
    abstract void trainNetwork(ActionEvent event) throws IOException, InterruptedException ;
    @FXML
    abstract void stopTrainNetwork(ActionEvent event);

    protected Level getLevel(String levelName) {
        for(Level l : levels) if(l.getLevelName().equals(levelName)) return l;

        return null;
    }


}
