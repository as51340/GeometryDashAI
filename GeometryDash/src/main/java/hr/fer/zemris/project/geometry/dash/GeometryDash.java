package hr.fer.zemris.project.geometry.dash;
import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.GameWorld;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
<<<<<<< HEAD
import hr.fer.zemris.project.geometry.dash.model.settings.music.BackgroundMusicPlayer;
import hr.fer.zemris.project.geometry.dash.view.components.GeometryDashTitle;
import hr.fer.zemris.project.geometry.dash.visualization.GameSceneController;
=======
>>>>>>> 9073c1cd8dc7c96e74e7ead64ed70f4e79c39202
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
=======
import javafx.scene.layout.StackPane;
>>>>>>> 9073c1cd8dc7c96e74e7ead64ed70f4e79c39202
import javafx.stage.Stage;

public class GeometryDash extends Application {
	
<<<<<<< HEAD
	
	private GameEngine gameEngine = new GameEngine(100, "Geometry Dash", GameConstants.WIDTH, GameConstants.HEIGHT);

	
	//ovo ti netreba
	private Pane root = new Pane();
	
	
    @Override
    public void start(Stage primaryStage) throws IOException {
    	//OVO OSTAVI
//    	Parent root1 = FXMLLoader.load(getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml"));
//    	Scene scene = new Scene(root1);
		//
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
    	Parent root1 = fxmlLoader.load();
    	GameSceneController controller = fxmlLoader.<GameSceneController>getController();
    	controller.setGameEngine(gameEngine);
    	Scene scene = new Scene(root1);
=======
	private GameEngine gameEngine = new GameEngine(60, "Geometry Dash", GameConstants.WIDTH, GameConstants.HEIGHT);

    @Override
    public void start(Stage primaryStage) throws IOException {
    	Parent root = FXMLLoader.load(
    		getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml")
    	);
>>>>>>> 9073c1cd8dc7c96e74e7ead64ed70f4e79c39202
    	
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
