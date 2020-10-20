package hr.fer.zemris.project.geometry.dash;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.settings.music.BackgroundMusicPlayer;
import hr.fer.zemris.project.geometry.dash.view.components.GeometryDashTitle;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GeometryDash extends Application {
	
	
	private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
	
	
	private GameEngine gameEngine = new GameEngine(60, "Geometry Dash", 1280, 720);
	
	//private GameWorld u kojem ce biti pravila definirana
	
	private Pane root = new Pane();
	
	
	
	/**
	 * adds title to root scene
	 */
	private void addTitle() {
		GeometryDashTitle title = new GeometryDashTitle("GEOMETRY DASH");
		
		title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
	    title.setTranslateY(HEIGHT / 3);
	    
		root.getChildren().add(title);
		
	}
	
	/**
	 * creates context for root scene
	 * @return
	 */
	private Parent createContext() {
		addTitle();
		
		return root;
	}	

    @Override
    public void start(Stage primaryStage) {
        
//    	Parent root = null;
//		try {
//			root = FXMLLoader.<Parent>load(getClass().getResource(("Background.fxml")));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        gameEngine.createStageFromData(primaryStage);
        gameEngine.start();	
//    	tester
//		Media media = new Media(getClass().getResource(GameConstants.pathToSongs + "BlahBlahBlah.mp3").toExternalForm());
//		MediaPlayer mediaPlayer = new MediaPlayer(media);
//		mediaPlayer.setVolume(1);
//		mediaPlayer.play();
//        Utils.createMediaPlayer("BlahBlahBlah.mp3").play();
//        System.out.println("Printaj odmah poslije");
        
        Scene scene = new Scene(createContext());
		
		primaryStage.setTitle("Geometry dash");
		primaryStage.setScene(scene);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		
		
		
	    primaryStage.show();
        
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}