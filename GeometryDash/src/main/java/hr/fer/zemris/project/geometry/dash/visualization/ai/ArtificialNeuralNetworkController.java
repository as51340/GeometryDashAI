package hr.fer.zemris.project.geometry.dash.visualization.ai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * When user clicks on AI options and then on first
 * 
 * @author Andi Škrgat
 *
 */
public class ArtificialNeuralNetworkController extends AIControllers {

	@FXML
	private TextField numberPerHiddenLayerField;
	@FXML
	private TextField hiddenLayersField;

	@FXML
	private Button Train;

	private AIAlgorithm algorithm;

	public void init() {
		super.init();
		numberPerHiddenLayerField.setText("5");
		hiddenLayersField.setText("3");
	}

	@Override
	void trainNetwork(ActionEvent event) throws IOException, InterruptedException {
		if (hiddenLayersField.getText().isEmpty()) {
			hiddenLayersField.setText("Required");
			return;
		}

		if (numberPerHiddenLayerField.getText().isEmpty()) {
			numberPerHiddenLayerField.setText("Required");
			return;
		}

		int numberPerHiddenLayer = -1;
		int hiddenLayers = -1;
		try {
			hiddenLayers = Integer.parseInt(hiddenLayersField.getText());
			numberPerHiddenLayer = Integer.parseInt(numberPerHiddenLayerField.getText());
		} catch (NumberFormatException ex) {
			numberPerHiddenLayerField.setText("Requires int");
			return;
		}

		algorithm = new AIAlgorithm(hiddenLayers, numberPerHiddenLayer, PlayingMode.NEURAL_NETWORK);

		// postavi AI training mode
		// GameEngine.getInstance().getGameStateListener().AITrainingModePlayingStarted();
		// otvori mi GameWorld
		// tu ce bit dropdown svih levela
		// recimo zasad otvori uvijek isti
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
		Parent root = loader.load();

		Stage stage = (Stage) rootPane.getScene().getWindow();
		Scene scene = GeometryDash.createScaledScene(root, stage);
		scene.getRoot().requestFocus();
		GameEngine.getInstance().setGameWorld(); // jednako napravi novi game world i postavi session character

		Object lockObject = new Object(); // locking object

		algorithm.setLockObj(lockObject);
		GameEngine.getInstance().getGameWorld().setLockObject(lockObject);

		GameEngine.getInstance().getGameWorld().createScene(levelBox.getValue());
		GameEngine.getInstance().getGameWorld().setAlgorithm(algorithm);

		algorithm.initialize();

		Set<Player> players = algorithm.getPlayerNeuralNetworkMap().keySet();
		for (Player p : players) {
			GameEngine.getInstance().getGameWorld().addPlayer(p);
		}

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
		algorithm.setController(controller);
		GameEngine.getInstance().setGameState(GameState.AI_TRAINING_MODE);
//		controller.setPreviousSceneRoot(rootPane);
		controller.init(PlayingMode.NEURAL_NETWORK);
//		GameSceneController.generationLabel.setVisible(true);

		// otherwise window will reset its size to default; this will keep current
		// window width and height
		stage.setScene(scene);
		// scena je postavljena to bi trebalo bit okej
		// sad algoritam negdje pokrenut
//		
//		System.out.println("Pokrecem algoritam zasad samo scena postoji!");
	}

	@Override
	void stopTrainNetwork(ActionEvent event) {

	}; // zasada ovako

}
/*

 */