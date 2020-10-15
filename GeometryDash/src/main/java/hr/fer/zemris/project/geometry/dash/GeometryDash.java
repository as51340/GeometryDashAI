package hr.fer.zemris.project.geometry.dash;
import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class GeometryDash extends Application {
	
	private GameEngine gameEngine = new GameEngine("Geometry Dash", 1280, 720);
	//private GameWorld u kojem ce biti pravila definirana
	

    @Override
    public void start(Stage stage) {
        
//    	Parent root = null;
//		try {
//			root = FXMLLoader.<Parent>load(getClass().getResource(("Background.fxml")));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        gameEngine.createStageFromData(stage);
        gameEngine.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}