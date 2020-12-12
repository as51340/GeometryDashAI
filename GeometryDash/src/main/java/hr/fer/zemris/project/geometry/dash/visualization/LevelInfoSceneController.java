package hr.fer.zemris.project.geometry.dash.visualization;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class LevelInfoSceneController extends MenuController {
		
    @FXML
    private Text levelName;

    @FXML
    private Label totalJumps;
	
	public void setLevelName(String levelName, String totalJumps) {
		this.levelName.setText(levelName);
		this.totalJumps.setText(totalJumps);
	}

}
