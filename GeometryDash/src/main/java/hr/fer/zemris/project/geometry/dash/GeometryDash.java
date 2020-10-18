package hr.fer.zemris.project.geometry.dash;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.settings.music.BackgroundMusicPlayer;
import javafx.application.Application;
import javafx.stage.Stage;

public class GeometryDash extends Application {
	
	private GameEngine gameEngine = new GameEngine(60, "Geometry Dash", 1280, 720);
	
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
//    	tester
//		Media media = new Media(getClass().getResource(GameConstants.pathToSongs + "BlahBlahBlah.mp3").toExternalForm());
//		MediaPlayer mediaPlayer = new MediaPlayer(media);
//		mediaPlayer.setVolume(1);
//		mediaPlayer.play();
//        Utils.createMediaPlayer("BlahBlahBlah.mp3").play();
//        System.out.println("Printaj odmah poslije");
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}