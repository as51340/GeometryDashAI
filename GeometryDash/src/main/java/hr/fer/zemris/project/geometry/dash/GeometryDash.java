package hr.fer.zemris.project.geometry.dash;
import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.GameWorld;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.music.BackgroundMusicPlayer;
import hr.fer.zemris.project.geometry.dash.visualization.GameSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GeometryDash extends Application {

	private GameEngine gameEngine = new GameEngine(100, "Geometry Dash", GameConstants.WIDTH, GameConstants.HEIGHT);
	
	
	private void loadGameMenu(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(
	    		getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml")
	    	);
	    	
	        // Set a default "standard" or "100%" resolution
	        double origW = GameConstants.WIDTH;
	        double origH = GameConstants.HEIGHT;

	        // Place the Parent in a StackPane, which will keep it centered
	        StackPane rootPane = new StackPane();
	        rootPane.getChildren().add(root);

	        // Create the scene initially at the "100%" size
	        Scene scene = new Scene(rootPane, origW, origH);
	        
	        // Bind the scene's width and height to the scaling parameters on the group
	        root.scaleXProperty().bind(scene.widthProperty().divide(origW));
	        root.scaleYProperty().bind(scene.heightProperty().divide(origH));

	        // Preserve aspect ratio when resizing
	        double ratio = (double) GameConstants.HEIGHT / GameConstants.WIDTH;
	        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(ratio));
	        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(ratio));
	        
			primaryStage.setTitle("Geometry Dash");
			primaryStage.setScene(scene);
		    primaryStage.show();
	}
	
	private void loadMain(Stage primaryStage) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
    	Parent root1 = fxmlLoader.load();
    	GameSceneController controller = fxmlLoader.<GameSceneController>getController();
    	controller.setGameEngine(gameEngine);
    	Scene scene = new Scene(root1);
    	gameEngine.createStageFromData(primaryStage);
    	gameEngine.start();
    	primaryStage.setTitle("Geometry Dash");
		primaryStage.setScene(scene);
	    primaryStage.show();
	}
	
    @Override
    public void start(Stage primaryStage) throws IOException {

//        loadGameMenu(primaryStage);
    	loadMain(primaryStage);
//    	tester
//		Media media = new Media(getClass().getResource(GameConstants.pathToSongs + "BlahBlahBlah.mp3").toExternalForm());
//		MediaPlayer mediaPlayer = new MediaPlayer(media);
//		mediaPlayer.setVolume(1);
//		mediaPlayer.play();
//		Utils.createMediaPlayer("BlahBlahBlah.mp3").play();
//		System.out.println("Printaj odmah poslije");
    	
    }

    public static void main(String[] args) {
        launch(args);
    }

}
