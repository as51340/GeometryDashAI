package hr.fer.zemris.project.geometry.dash.visualization;

import hr.fer.zemris.project.geometry.dash.model.stats.Stats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AchievementsSceneController extends MenuController {
	
	@FXML
	private ListView<Text> achievementsList;
	
	@FXML
	private Label notLoggedIn;
	
	private ObservableList<Text> obsList;
	
	public void init() {
		if(gameEngine.getSession() != null) {
			fillList();
			notLoggedIn.setVisible(false);
			achievementsList.setVisible(true);
		} else {
			notLoggedIn.setVisible(true);
			achievementsList.setVisible(false);
		}
	}

	private void fillList() {
		obsList = FXCollections.observableArrayList();
		Stats stats = gameEngine.getSession().getStats();
		
		obsList.add(new Text("Total jumps:                  " + stats.getTotalJumps()));
		obsList.add(new Text("Total attempts:               " + stats.getTotalAttempts()));
		obsList.add(new Text("Collected stars:              " + stats.getCollectedStars()));
		obsList.add(new Text("Completed levels:             " + stats.getCompletedLevels()));
		obsList.add(new Text("Collected user coins:         " + stats.getUserCoins()));
		obsList.add(new Text("Liked levels:                 " + stats.getLikedLevels()));
		obsList.add(new Text("Disliked levels:              " + stats.getDislikedLevels()));
		obsList.add(new Text("Rated levels:                 " + stats.getRatedLevels()));
		
		achievementsList.setItems(obsList);
		
		achievementsList.setCellFactory(cell -> new ListCell<Text>() {
                @Override
                protected void updateItem(Text item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (item != null) {
                    	setText(item.getText());
                    	setTextFill(Color.WHITE);
                    	//item.setStroke(Color.BLACK); // kako ovo promijeniti??
                    }
                    
                    setFont(Font.font("Pusab", 45));
                    
                    if(getIndex() % 2 == 1)
                        setStyle("-fx-background-color: #bf723f;");
                    else
                    	setStyle("-fx-background-color: #985534;");
                }
            
        });
	}
}
