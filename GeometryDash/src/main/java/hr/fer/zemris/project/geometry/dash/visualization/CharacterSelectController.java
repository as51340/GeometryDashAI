package hr.fer.zemris.project.geometry.dash.visualization;

import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharactersSelector;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class CharacterSelectController extends MainOptionsController {

	private static final int GRID_ICON_SIZE = 60;
	private static final int SELECTED_ICON_SIZE = 150;
	private static final int GRID_COLUMNS = 6;
    
    @FXML
	private ImageView background1;

	@FXML
	private ImageView background2;

	@FXML
	private ImageView background3;
	
	@FXML
	private Rectangle overlay;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView selectedCharacter;

    @FXML
	private Rectangle gridBackground;
    
    @FXML
	private GridPane characterGrid;
    
    @FXML
    public void initialize() {
    	Utils.animateBackground(overlay, background1, background2, background3);
    	
    	//CharactersSelector selector = gameEngine.getGameWorld().getCharactersSelector();
    	CharactersSelector selector = new CharactersSelector();
    	int x = 0, y = 0;
    	
    	for(CharacterObject character : selector.getAllCharacters()) {
    		ImageView characterImage;
    		
    		if(character.isLocked()) {
    			characterImage = new ImageView(Utils.loadStatic("lock.jpg", GRID_ICON_SIZE, GRID_ICON_SIZE));
    		} else {
    			characterImage = new ImageView(Utils.loadIcon(GameConstants.pathToIcons + character.getUri(), GRID_ICON_SIZE, GRID_ICON_SIZE));
    			
    			characterImage.getStyleClass().add("menu-button");
        		characterImage.addEventHandler(MOUSE_CLICKED, e -> {
        			selectedCharacter.setImage(Utils.loadIcon(GameConstants.pathToIcons + character.getUri(), SELECTED_ICON_SIZE, SELECTED_ICON_SIZE));
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
