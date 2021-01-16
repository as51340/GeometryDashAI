package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.GameState;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.listeners.AIGameSceneListener;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.ai.AIOptionsController;
import hr.fer.zemris.project.geometry.dash.visualization.ai.TrainingSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class GameSceneController extends MainOptionsController {

	@FXML
	private StackPane rootPane;

	@FXML
	private ImageView background1;

	@FXML
	private ImageView background2;

	@FXML
	private ImageView background3;

	@FXML
	private Button pause;

	@FXML
	private ImageView floor1;

	@FXML
	private ImageView floor2;

	@FXML
	private ImageView floor3;

	@FXML
	public Label generationLabel;

	@FXML
	private Rectangle overlay;

	@FXML
	private Rectangle overlayBlack;

	@FXML
	private Canvas canvas;

	/**
	 * Generation counter
	 */
	private int cnt = 1;

	/**
	 * Playing mode
	 */
	private PlayingMode playingMode;

	/**
	 * Locks training until we click on continue
	 */
	private Object continueLockObject;

	/**
	 * @return the continueLockObject
	 */
	public Object getContinueLockObject() {
		return continueLockObject;
	}

	/**
	 * @param continueLockObject the continueLockObject to set
	 */
	public void setContinueLockObject(Object continueLockObject) {
		this.continueLockObject = continueLockObject;
	}

	@FXML
	public void initialize() {
		Utils.animateBackground(overlay, background1, background2, background3);
		Utils.animateFloor(overlay, floor1, floor2, floor3);
	}

	@FXML
	void pauseTraining(ActionEvent event) {
		if (GameEngine.getInstance().getGameWorld().getGpAlgorithm() != null) {
			GameEngine.getInstance().getGameWorld().getGpAlgorithm().setPausePressed(true);
		} else if(GameEngine.getInstance().getGameWorld().getAlgorithm() != null) {
			GameEngine.getInstance().getGameWorld().getAlgorithm().setPausePressed(true);
		}
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(GameConstants.pathToVisualization + "AI/TrainingSceneExit.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		TrainingSceneController controller = loader.getController();
		controller.init(this.playingMode, false, continueLockObject, cnt);
		controller.setPreviousSceneRoot(rootPane);
	}

	public void init(PlayingMode playingMode) {
		GameEngine.getInstance().getGameWorld().getRenderer().setGraphicsContext(canvas.getGraphicsContext2D());
		if (GameEngine.getInstance().getGameState() == GameState.AI_TRAINING_MODE) {
			generationLabel.setVisible(true);
			pause.setVisible(true);
		}
		this.playingMode = playingMode;
		continueLockObject = new Object();
		if (GameEngine.getInstance().getGameWorld().getGpAlgorithm() != null) {
			GameEngine.getInstance().getGameWorld().getGpAlgorithm().setContinueLockingObject(continueLockObject);
		} else if(GameEngine.getInstance().getGameWorld().getAlgorithm() != null) {
			GameEngine.getInstance().getGameWorld().getAlgorithm().setContinueLockingObject(continueLockObject);
		}
	}

	/**
	 * When ai fininished level, training stopped or training paused
	 */
	public void interruptTraining(PlayingMode playingMode, Object obj, boolean levelPassed) {
		GeometryDash.getStage().setUserData(obj);
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(GameConstants.pathToVisualization + "AI/TrainingSceneExit.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		TrainingSceneController controller = loader.getController();
		controller.init(playingMode, levelPassed, continueLockObject, cnt);
		controller.setPreviousSceneRoot(rootPane);
	}

	/**
	 * Updates generation label if we are in training mode
	 */
	public void updateLabel() {
		generationLabel.setText("Generation " + cnt++);
	}

}
