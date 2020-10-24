package hr.fer.zemris.project.geometry.dash;
import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GeometryDash extends Application {
	
	private GameEngine gameEngine = new GameEngine(60, "Geometry Dash", GameConstants.WIDTH, GameConstants.HEIGHT);

    @Override
    public void start(Stage primaryStage) throws IOException {
    	Parent root = FXMLLoader.load(
    		getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml")
    	);
    	
        // Set a default "standard" or "100%" resolution
        double origW = GameConstants.WIDTH;
        double origH = GameConstants.HEIGHT;
        
        // Place the Group in a StackPane, which will keep it centered
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(root);
        
        // Create the scene initially at the "100%" size
        Scene scene = new Scene(rootPane, origW, origH);
        
        // Bind the scene's width and height to the scaling parameters on the group
        root.scaleXProperty().bind(scene.widthProperty().divide(origW));
        root.scaleYProperty().bind(scene.heightProperty().divide(origH));
        
        // Preserve aspect ratio when resizing
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(0.5));
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(0.5));

        gameEngine.createStageFromData(primaryStage);
        gameEngine.start();
        
//    	tester
//		Media media = new Media(getClass().getResource(GameConstants.pathToSongs + "BlahBlahBlah.mp3").toExternalForm());
//		MediaPlayer mediaPlayer = new MediaPlayer(media);
//		mediaPlayer.setVolume(1);
//		mediaPlayer.play();
//        Utils.createMediaPlayer("BlahBlahBlah.mp3").play();
//        System.out.println("Printaj odmah poslije");
        
		primaryStage.setTitle("Geometry Dash");
		primaryStage.setScene(scene);
	    primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
