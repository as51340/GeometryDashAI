package hr.fer.zemris.project.geometry.dash.visualization;

import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CharacterSelectController {

	private static final int TRANSITION_DURATION = 300;
	private static final int GRID_ICON_SIZE = 60;
	private static final int SELECTED_ICON_SIZE = 150;
	private static final int GRID_COLUMNS = 6;
	
	private Pane previousSceneRootPane;
	
    @FXML
    private StackPane rootPane;
    
    @FXML
    private AnchorPane anchorPane;
	
	@FXML
	private Rectangle background;
	
    @FXML
    private Line line;

    @FXML
    private ImageView backButton;

    @FXML
    private ImageView selectedCharacter;

    @FXML
	private Rectangle gridBackground;
    
    @FXML
	private GridPane characterGrid;
    
    @FXML
	private Rectangle overlay;
    
    
    public void setPreviousSceneRoot(Pane previousSceneRootPane) {
		previousSceneRootPane.getChildren().add(rootPane);
		this.previousSceneRootPane = previousSceneRootPane;
		
		overlay.setVisible(true);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(TRANSITION_DURATION), rootPane);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        
        fadeTransition1.setOnFinished(e1 -> {
        	FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(TRANSITION_DURATION), overlay);
            fadeTransition2.setFromValue(1);
            fadeTransition2.setToValue(0);
            fadeTransition2.play();
        });
        
        fadeTransition1.play();
	}
    
    @FXML
    protected void backButtonClicked(MouseEvent event) {
        FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(TRANSITION_DURATION), overlay);
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
    
    @FXML
    public void initialize() {
    	CharactersSelector selector = new CharactersSelector();
    	int x = 0, y = 0;
    	
    	for(CharacterObject character : selector.getAllCharacters()) {
    		ImageView characterImage;
    		
    		if(character.isLocked()) {
    			characterImage = new ImageView(Utils.loadStatic("lock.jpg", GRID_ICON_SIZE, GRID_ICON_SIZE));
    		} else {
    			characterImage = new ImageView(Utils.loadIcon(character.getUri(), GRID_ICON_SIZE, GRID_ICON_SIZE));
    			
    			characterImage.getStyleClass().add("menu-button");
        		characterImage.addEventHandler(MOUSE_CLICKED, e -> {
        			selectedCharacter.setImage(Utils.loadIcon(character.getUri(), SELECTED_ICON_SIZE, SELECTED_ICON_SIZE));
        			CharactersSelector.selectedCharacter = character;
        		});
    		}
    		
    		x %= GRID_COLUMNS;
    		characterGrid.add(characterImage, x, y, 1, 1);
    		x++;
    		
    		if(x == GRID_COLUMNS) y++;
    	}
    }

}
