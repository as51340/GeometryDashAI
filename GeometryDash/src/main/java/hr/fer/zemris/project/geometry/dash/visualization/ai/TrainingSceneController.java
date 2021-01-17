package hr.fer.zemris.project.geometry.dash.visualization.ai;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.ai.AIGameSceneListenerImpl;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Tree;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.listeners.AIGameSceneListener;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.BackgroundSceneController;
import hr.fer.zemris.project.geometry.dash.visualization.MenuController;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for handling training scene
 * 
 * @author Andi Škrgat
 *
 */
public class TrainingSceneController extends MenuController {

	@FXML
	private StackPane rootPane;

	@FXML
	private Rectangle overlay;

	@FXML
	private Text levelName;

	@FXML
	private AnchorPane centerPane;

	@FXML
	private Button save;

	@FXML
	private Button menuButton;

	@FXML
	private Label attempt;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label AiLabelType;

	/**
	 * Game listener
	 */
	private AIGameSceneListener gameListener;

	/**
	 * Playing mode to save
	 */
	private PlayingMode playingMode;

	/**
	 * Object that needs to be saved
	 */
	private Object objToSave;
	
	/**
	 * Locks training until we click on continue
	 */
	private Object continueLockObject;
	
	@FXML
    private Button contP;

	@FXML
	void continueAction(ActionEvent event) {
		if (GameEngine.getInstance().getGameWorld().getGpAlgorithm() != null) {
			GameEngine.getInstance().getGameWorld().getGpAlgorithm().setPausePressed(false);
		} else if(GameEngine.getInstance().getGameWorld().getAlgorithm() != null) {
			GameEngine.getInstance().getGameWorld().getAlgorithm().setPausePressed(false);
		}

		// close menu
		backButtonClicked(null);

		synchronized(continueLockObject) {
			continueLockObject.notifyAll(); // oslobodi sve dretve
		}
	}

	/**
	 * Init function
	 * 
	 * @param playingMode
	 */
	public void init(PlayingMode playingMode, boolean levelPassed, Object continueLockObject, int generationNum) {
		AiLabelType.setText(playingMode.toString());
		attempt.setText("Generation " + generationNum);
		if(levelPassed) {
			contP.setVisible(false);
		} else {
			contP.setVisible(true);
		}
		gameListener = new AIGameSceneListenerImpl();
		this.playingMode = playingMode;
		objToSave = GeometryDash.getStage().getUserData();
		this.continueLockObject = continueLockObject;
	}

	@FXML
	void AIOptionsMenu(ActionEvent event) throws IOException {
		GameEngine.getInstance().getGameStateListener().AITrainingModePlayingExited();
		openOptionsScene();
	}

	private void openOptionsScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(GameConstants.pathToVisualization + "AI/AIOptionsScene.fxml"));
		loader.load();
		AIOptionsController controller = loader.getController();
		controller.setPreviousSceneRoot(rootPane);
	}

	@FXML
	void saveAction(ActionEvent event) throws IOException {
		GameEngine.getInstance().getGameStateListener().AITrainingModePlayingExited();
		if (this.playingMode == PlayingMode.GENETIC_PROGRAMMING) {
			Tree bestTree = (Tree) objToSave;
			gameListener.saveGP(bestTree);
			openOptionsScene();
		} else if (this.playingMode == PlayingMode.ELMAN_NEURAL_NETWORK) {
			throw new IllegalStateException("Elman not implemented yet");
		} else if (this.playingMode == PlayingMode.NEURAL_NETWORK) {
			throw new IllegalStateException("Genetic not implemented yet");
		} else {
			throw new IllegalStateException("Playing mode is unknown");
		}
	}

}
