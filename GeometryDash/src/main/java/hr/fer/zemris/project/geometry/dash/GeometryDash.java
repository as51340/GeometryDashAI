package hr.fer.zemris.project.geometry.dash;
import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.BackgroundSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * From where application starts
 */
public class GeometryDash extends Application {

	private GameEngine gameEngine = GameEngine.getInstance();
	
	private void loadGameMenu(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml"));
    	Parent root = fxmlLoader.load();
    	Scene scene = createScaledScene(root, primaryStage);

		BackgroundSceneController controller = fxmlLoader.<BackgroundSceneController>getController();
        controller.init();
    	gameEngine.createStageFromData(primaryStage);
//    	gameEngine.start();

		primaryStage.setTitle("Geometry Dash");
		primaryStage.setScene(scene);
	    primaryStage.show();
	}
	
	private void loadMain(Stage primaryStage) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
    	Parent root = fxmlLoader.load();
    	Scene scene = createScaledScene(root, primaryStage);
    	
    	scene.setOnKeyPressed((e) -> {
    		if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W || e.getCode() == KeyCode.SPACE) {
        		gameEngine.getGameWorld().getPlayerListener().playerJumped();
        		gameEngine.getUserListener().playerJumped();
    		}
    	});

    	scene.setOnMouseClicked((e) -> {
    		if(e.getButton() == MouseButton.PRIMARY) {
        		gameEngine.getGameWorld().getPlayerListener().playerJumped();
        		gameEngine.getUserListener().playerJumped();
    		}
    	});
        
    	gameEngine.createStageFromData(primaryStage);
    	gameEngine.start();
        gameEngine.getGameStateListener().normalModePlayingStarted();

    	primaryStage.setTitle("Geometry Dash");
		primaryStage.setScene(scene);
	    primaryStage.show();
	}
	
	public static Scene createScaledScene(Node root, Stage stage) {
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
        stage.minHeightProperty().bind(stage.widthProperty().multiply(ratio));
        stage.maxHeightProperty().bind(stage.widthProperty().multiply(ratio));
        
        return scene;
	}
	
    @Override
    public void start(Stage primaryStage) throws IOException {
    	
    	loadGameMenu(primaryStage);
//    	loadMain(primaryStage);

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
