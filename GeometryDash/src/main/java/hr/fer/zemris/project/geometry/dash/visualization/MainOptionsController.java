package hr.fer.zemris.project.geometry.dash.visualization;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainOptionsController {
	
	private Pane previousSceneRootPane;
	
    @FXML
    private StackPane rootPane;
    
	@FXML
	private Rectangle overlayBlack;
	
    @FXML
    private ImageView backButton;

	public void setPreviousSceneRoot(Pane previousSceneRootPane) {
		previousSceneRootPane.getChildren().add(rootPane);
		this.previousSceneRootPane = previousSceneRootPane;
		
		overlayBlack.setVisible(true);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(GameConstants.TRANSITION_DURATION), rootPane);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        
        fadeTransition1.setOnFinished(e1 -> {
        	FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(GameConstants.TRANSITION_DURATION), overlayBlack);
            fadeTransition2.setFromValue(1);
            fadeTransition2.setToValue(0);
            fadeTransition2.play();
        });
        
        fadeTransition1.play();
	}
    
    @FXML
    protected void backButtonClicked(MouseEvent event) {
        FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(GameConstants.TRANSITION_DURATION), overlayBlack);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        
        fadeTransition1.setOnFinished(e1 -> {
        	FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(300), rootPane);
            fadeTransition2.setFromValue(1);
            fadeTransition2.setToValue(0);
            fadeTransition2.setOnFinished(e2 -> previousSceneRootPane.getChildren().remove(rootPane));
            fadeTransition2.play();
        });
        
        fadeTransition1.play();
    }

}
