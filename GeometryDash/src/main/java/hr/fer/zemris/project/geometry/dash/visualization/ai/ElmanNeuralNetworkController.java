package hr.fer.zemris.project.geometry.dash.visualization.ai;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok.AIAlgorithm;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ElmanNeuralNetworkController extends AIControllers {

	@FXML
	private TextField numberPerHiddenLayerField;

	private AIAlgorithm algorithm;

	@Override
	public void trainNetwork(ActionEvent event) throws IOException, InterruptedException {
		if (numberPerHiddenLayerField.getText().isEmpty()) {
			numberPerHiddenLayerField.setText("Required");
			return;
		}

		int numberPerHiddenLayer = -1;
		try {
			numberPerHiddenLayer = Integer.parseInt(numberPerHiddenLayerField.getText());
		} catch (NumberFormatException ex) {
			numberPerHiddenLayerField.setText("Requires int");
			return;
		}

		String levelName = levelBox.getValue();

		algorithm = new AIAlgorithm(1, numberPerHiddenLayer, PlayingMode.NEURAL_NETWORK);

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
		Parent root = loader.load();

		Stage stage = (Stage) rootPane.getScene().getWindow();
		Scene scene = GeometryDash.createScaledScene(root, stage);
		scene.getRoot().requestFocus();
		GameEngine.getInstance().setGameWorld(); // jednako napravi novi game world i postavi session character

		Object lockObject = new Object(); // locking object
		Object generationLock = new Object();

		algorithm.setLockObj(lockObject);
		GameEngine.getInstance().getGameWorld().setLockObject(lockObject);
		algorithm.setGenerationLockObject(generationLock);
		GameEngine.getInstance().getGameWorld().setGenerationLockObject(generationLock);

		for (int i = 0; i < AIConstants.POPULATION_SIZE; i++) {
			Player player = new Player(new Vector2D(0, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
					new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), PlayingMode.NEURAL_NETWORK);
//            System.out.println(player.getId());	
			GameEngine.getInstance().getGameWorld().addPlayer(player);
			algorithm.getPlayerNeuralNetworkMap().put(player, null);
		}
//	System.out.println("Size: " + GameEngine.getInstance().getGameWorld().getPlayers().size());
		GameEngine.getInstance().getGameWorld().createScene(levelName); // ucitaj uvijek prvi level
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
//	controller.setPreviousSceneRoot(rootPane);
		controller.init();
//	GameSceneController.generationLabel.setVisible(true);

		// otherwise window will reset its size to default; this will keep current
		// window width and height
		double width = rootPane.getScene().getWidth();
		double height = rootPane.getScene().getHeight();
		stage.setWidth(width);
		stage.setHeight(height);
		stage.setScene(scene);
		// scena je postavljena to bi trebalo bit okej
		// sad algoritam negdje pokrenut
//	
//	System.out.println("Pokrecem algoritam zasad samo scena postoji!");

	};

	@Override
	public void stopTrainNetwork(ActionEvent event) {
	};

}
