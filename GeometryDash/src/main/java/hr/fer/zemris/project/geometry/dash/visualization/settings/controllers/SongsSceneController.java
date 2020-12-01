package hr.fer.zemris.project.geometry.dash.visualization.settings.controllers;

import java.util.Set;

import hr.fer.zemris.project.geometry.dash.model.settings.music.LevelMusicPlayer;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SongsSceneController extends MenuController {
	
	@FXML
	private ListView<Text> songList;
	
	private ObservableList<Text> obsList;
	
	public SongsSceneController() {
		obsList = FXCollections.observableArrayList();
		LevelMusicPlayer musicPlayer = new LevelMusicPlayer();
		Set<String> songs = musicPlayer.getSongNames();
		
		for(String song : songs) {
			obsList.add(new Text(song));
		}
		
	}
	
	@FXML
	public void initialize() {
		songList.setItems(obsList);
		
		songList.setCellFactory(cell -> new ListCell<Text>() {
                @Override
                protected void updateItem(Text item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (item != null) {
                    	setText(item.getText());
                    	setTextFill(Color.WHITE);
                    	item.setStroke(Color.BLACK); // kako ovo promijeniti??
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
