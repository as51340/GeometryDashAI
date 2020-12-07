package hr.fer.zemris.project.geometry.dash.visualization;

import java.util.Set;

import hr.fer.zemris.project.geometry.dash.model.level.Level;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

public class ChooseLevelController extends MainOptionsController {
	
//	private Set<Level> levels = gameEngine.getLevelManager().getAllLevels();

	@FXML
	private Line line;
	
	@FXML
	private ImageView backgroundImage;
	
	@FXML
	private ImageView previousLevel;
	
	@FXML
	private ImageView nextLevel;

	@FXML
	private void nextButtonClicked() {
		
	}
	
	@FXML
	private void previousButtonClicked() {
		
	}
	
}
