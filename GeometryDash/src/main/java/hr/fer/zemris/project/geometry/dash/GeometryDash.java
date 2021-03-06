package hr.fer.zemris.project.geometry.dash;
import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.BackgroundSceneController;
import hr.fer.zemris.project.geometry.dash.visualization.GameSceneController;
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
	
	private static Stage primaryStage;
	
	/**
	 * @return {@linkplain Stage}
	 */
	public static Stage getStage() {
		return primaryStage;
	}
	
	private void loadGameMenu(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml"));
    	Parent root = fxmlLoader.load();
    	Scene scene = createScaledScene(root, primaryStage);

		BackgroundSceneController controller = fxmlLoader.<BackgroundSceneController>getController();
        controller.init();
        
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

        // Disable resizing to prevent bugs
		stage.setResizable(false);

        // Preserve aspect ratio when resizing
		// Should be commented out if resizing is disabled
//		double ratio = (double) GameConstants.HEIGHT / GameConstants.WIDTH;
//		stage.minHeightProperty().bind(stage.widthProperty().multiply(ratio));
//		stage.maxHeightProperty().bind(stage.widthProperty().multiply(ratio));
        
        return scene;
	}
	
	private void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
    @Override
    public void start(Stage primaryStage) throws IOException {
    	setPrimaryStage(primaryStage);
    	loadGameMenu(primaryStage);

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
