package hr.fer.zemris.project.geometry.dash.view.gamemenu;

import hr.fer.zemris.project.geometry.dash.view.gamemenu.components.GeometryDashTitle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GameMenuScene extends Scene {
	
	private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
	
	
    
    public GameMenuScene(Parent root) {
    	super(root);
    	
    	addTitle();
		 	
	}
    
//    public GameMenuScene() {
//    	this(createContext());
//    }

	
	
	
	
	/**
	 * adds title to root scene
	 */
	private void addTitle() {
		GeometryDashTitle title = new GeometryDashTitle("GEOMETRY DASH");
		
		title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
	    title.setTranslateY(HEIGHT / 3);
	    
	    System.out.println("tu sam");
//	    super.root.getChildren().add(new Label("jaa"));
		rootProperty().w.getChildren.add(title);
		
		
		
	}
	
	private Parent createContext() {
		addTitle();
		
		return root;
	}



}
