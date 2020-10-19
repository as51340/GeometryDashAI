package hr.fer.zemris.project.geometry.dash.view;

import java.io.File;

import hr.fer.zemris.project.geometry.dash.view.components.GeometryDashTitle;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Driver extends Application{
	
	private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
	
	private Pane root = new Pane();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		Scene scene = new Scene(createContext());
		
		primaryStage.setTitle("Geometry dash");
		primaryStage.setScene(scene);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		
		
		
	    primaryStage.show();
	     
		
	}
	
	private Parent createContext() {
		addTitle();
		
		return root;
	}
	
	private void addTitle() {
		GeometryDashTitle title = new GeometryDashTitle("GEOMETRY DASH");
		
		title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
	    title.setTranslateY(HEIGHT / 3);
	    
		root.getChildren().add(title);
		
	}

}
