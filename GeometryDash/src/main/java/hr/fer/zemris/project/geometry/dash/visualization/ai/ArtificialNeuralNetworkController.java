package hr.fer.zemris.project.geometry.dash.visualization.ai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok.AIAlgorithm;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.GameState;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.GameSceneController;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * When user clicks on AI options and then on first
 * @author Andi Škrgat
 *
 */
public class ArtificialNeuralNetworkController extends MenuController{
	
	 @FXML
	 private StackPane rootPane;

	 @FXML
	 private Rectangle overlay;

	 @FXML
	 private ImageView backButton;

	 @FXML
	 private Button Train;
	 
	 /**
	  * Lista levela
	  */
	 private List<Level> levels;
	 
	 private AIAlgorithm algorithm;
	 
	 public void init() {
		 algorithm =  new AIAlgorithm(3, 4, PlayingMode.NEURAL_NETWORK);
		 levels = new ArrayList<>(GameEngine.getInstance().getLevelManager().getAllLevels());
	 }


	 @FXML
	 void trainNetwork(ActionEvent event) throws IOException, InterruptedException {
		//postavi AI training mode
		//GameEngine.getInstance().getGameStateListener().AITrainingModePlayingStarted();
		//otvori mi GameWorld
		//tu ce bit dropdown svih levela
		//recimo zasad otvori uvijek isti
		FXMLLoader loader = new FXMLLoader(
					getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
		Parent root = loader.load();

		Stage stage = (Stage) rootPane.getScene().getWindow();
		Scene scene = GeometryDash.createScaledScene(root, stage);
		scene.getRoot().requestFocus();
		GameEngine.getInstance().setGameWorld(); //jednako napravi novi game world i postavi session character
		
		Object lockObject = new Object(); //locking object
		algorithm.setLockObj(lockObject);
		GameEngine.getInstance().getGameWorld().setLockObject(lockObject);
		
		for (int i = 0; i < AIConstants.POPULATION_SIZE; i++) {
	            Player player = new Player(new Vector2D(0, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
	                    new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), PlayingMode.NEURAL_NETWORK);
//	            System.out.println(player.getId());	
	            GameEngine.getInstance().getGameWorld().addPlayer(player);
	            algorithm.getPlayerNeuralNetworkMap().put(player, null);
		}
//		System.out.println("Size: " + GameEngine.getInstance().getGameWorld().getPlayers().size());
		GameEngine.getInstance().getGameWorld().createScene(levels.get(1).getLevelName()); //ucitaj uvijek prvi level
		GameEngine.getInstance().getGameWorld().setAlgorithm(algorithm);
		Thread t = new Thread(() -> {
			try {
				algorithm.runAlgorithm();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		t.setName("Genetic algorithm thread!");
		t.setDaemon(true);
		t.start();
		
		GameSceneController controller = loader.getController();
//		controller.setPreviousSceneRoot(rootPane);
		controller.init();
//		GameSceneController.generationLabel.setVisible(true);
		
		// otherwise window will reset its size to default; this will keep current
		// window width and height
		double width = rootPane.getScene().getWidth();
		double height = rootPane.getScene().getHeight();
		stage.setWidth(width);
		stage.setHeight(height);
		stage.setScene(scene);
		//scena je postavljena to bi trebalo bit okej
		//sad algoritam negdje pokrenut
//		
//		System.out.println("Pokrecem algoritam zasad samo scena postoji!");
	 }
	
}
