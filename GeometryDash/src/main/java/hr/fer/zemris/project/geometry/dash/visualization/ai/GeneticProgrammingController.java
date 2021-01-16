package hr.fer.zemris.project.geometry.dash.visualization.ai;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.GeneticFunctionality;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Tree;
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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GeneticProgrammingController extends AIControllers {

    @FXML
    private TextField populationSizeField;

	private GeneticFunctionality algorithm;

	@FXML
	private ComboBox<String> typeBox;

	@FXML
	private ComboBox<String> genAlgBox;

	private final String[] selectionItems = new String[] { "ROULETTE_WHEEL_SELECTION", "THREE_TOURNAMENT_SELECTION" };

	private final String[] genAlgItems = new String[] { "ELIMINATIVE_GENETIC_ALGORITHM",
			"GENERATIONAL_GENETIC_ALGORITHM" };

	public void init() {
		super.init();

		typeBox.setItems(FXCollections.observableArrayList(selectionItems));
		typeBox.setValue(selectionItems[0]);
		genAlgBox.setItems(FXCollections.observableArrayList(genAlgItems));
		genAlgBox.setValue(genAlgItems[0]);
		populationSizeField.setText("50");
	}

	@FXML
	public void trainNetwork(ActionEvent event) throws IOException, InterruptedException {
		int populationSize = -1;
		
		if(populationSizeField.getText().isEmpty()) {
			populationSizeField.setText("required");
			return;
		}
		
		
		try {
			populationSize = Integer.parseInt(populationSizeField.getText());
		} catch (NumberFormatException e) {
			populationSizeField.setText("int required");
			return;
		}

		String levelName = levelBox.getValue();
		String selection = typeBox.getValue();
		String genAlgItems = genAlgBox.getValue();

		algorithm = new GeneticFunctionality(populationSize, selection, genAlgItems);

		
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
		Parent root = loader.load();

		Stage stage = (Stage) rootPane.getScene().getWindow();
		Scene scene = GeometryDash.createScaledScene(root, stage);
		scene.getRoot().requestFocus();
		GameEngine.getInstance().setGameWorld(); // jednako napravi novi game world i postavi session character

		Object lockObject = new Object(); // locking object
		algorithm.setLockingObject(lockObject);
		GameEngine.getInstance().getGameWorld().setLockObject(lockObject);

		algorithm.initializePopulation();
		int pop_size = algorithm.getPopulation().size();
		if (pop_size != algorithm.getTreePopulationSize()) {
			throw new IllegalStateException("Kriva inicijalizacija u gp algorithmu!");
		}
		
		Set<Player> players = algorithm.getPopulation().keySet();
		GameEngine.getInstance().getGameWorld().createScene(levelName);
		GameEngine.getInstance().getGameWorld().setGpAlgorithm(algorithm);
		
		for(Player p : players) {
			GameEngine.getInstance().getGameWorld().addPlayer(p);
		}
		
		Thread t = new Thread(() -> {
			algorithm.performAlgorithm();
		});
		t.setName("Gp algorithm thread!");
		t.setDaemon(true);
		t.start();

		GameSceneController controller = loader.getController();
		controller.init();
		//	GameSceneController.generationLabel.setVisible(true);

		double width = rootPane.getScene().getWidth();
		double height = rootPane.getScene().getHeight();
		stage.setWidth(width);
		stage.setHeight(height);
		stage.setScene(scene);
	}

	@FXML
	public void stopTrainNetwork(ActionEvent event) {

	}
}
