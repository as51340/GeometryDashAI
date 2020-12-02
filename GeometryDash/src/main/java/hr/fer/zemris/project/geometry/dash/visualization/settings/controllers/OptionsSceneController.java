package hr.fer.zemris.project.geometry.dash.visualization.settings.controllers;

import hr.fer.zemris.project.geometry.dash.model.settings.Options;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class OptionsSceneController extends MenuController {
	
	@FXML
	private GridPane optionGrid;
	
	
	public void init() {
		ObservableList<Node> childrens = optionGrid.getChildren();

		Options options = gameEngine.getSettings().getOptions();
	    for (Node node : childrens) 
	    	if(node instanceof CheckBox) {
	    		CheckBox check = (CheckBox) node;
	    		switch(check.getText()) {
	    		case "Auto Retry":
	    			check.setSelected(options.isAutoRetry());
	    			check.setOnAction(event -> options.setAutoRetry(check.isSelected()));
	    			break;	    			
	    		//todo ai
	    		}
	    	}
	    
	}

}
