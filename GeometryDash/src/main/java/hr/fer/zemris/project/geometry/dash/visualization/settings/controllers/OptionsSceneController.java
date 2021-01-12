package hr.fer.zemris.project.geometry.dash.visualization.settings.controllers;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.settings.Options;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

		Options options = GameEngine.getInstance().getSettings().getOptions();
	    for (Node node : childrens) 
	    	if(node instanceof CheckBox) {
	    		CheckBox check = (CheckBox) node;
	    		switch(check.getText()) {
	    		case "Auto Retry":
	    			check.setSelected(options.isAutoRetry());
	    			check.setOnAction(event -> options.setAutoRetry(check.isSelected()));
	    			break;	    			
				case "Neural Network":
					check.setSelected(options.getAIMode() == PlayingMode.NEURAL_NETWORK);
					check.setOnAction(event -> {
						options.setAIMode(PlayingMode.NEURAL_NETWORK);
						init();
					});
					break;
				case "Elman Network":
					check.setSelected(options.getAIMode() == PlayingMode.ELMAN_NEURAL_NETWORK);
					check.setOnAction(event -> {
						options.setAIMode(PlayingMode.ELMAN_NEURAL_NETWORK);
						init();
					});
					break;
				case "Genetic Programming":
					check.setSelected(options.getAIMode() == PlayingMode.GENETIC_PROGRAMMING);
					check.setOnAction(event -> {
						options.setAIMode(PlayingMode.GENETIC_PROGRAMMING);
						init();
					});
					break;

				}
	    	}
	    
	}


}
