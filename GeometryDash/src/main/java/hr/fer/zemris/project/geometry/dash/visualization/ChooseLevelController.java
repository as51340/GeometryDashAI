package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ChooseLevelController extends MainOptionsController {
	
	private GameEngine gameEngine = GameEngine.getInstance();
	
	private List<Level> levels;
	
	private List<Color> colors = Arrays.asList(
		Color.DODGERBLUE,
		Color.BLUEVIOLET,
		Color.PURPLE,
		Color.CRIMSON,
		Color.ORANGE,
		Color.YELLOWGREEN,
		Color.LIGHTGREEN,
		Color.CYAN
	);
	
	private List<Circle> pagination;
	
	private int levelIndex = 0; 
	
	private int colorIndex = 0;
	
    @FXML
    private Rectangle chooseLevelBackground;
	
    @FXML
    private ImageView floor1;

    @FXML
    private ImageView floor2;

    @FXML
    private ImageView floor3;

    @FXML
    private Rectangle floorOverlay;
	
    @FXML
    private Text levelName;
    
    @FXML
    private FlowPane paginationPane;
    
    @FXML
    private StackPane levelNameAndPaginationPane;
    
    @FXML
    private StackPane rootPane;
    
	@FXML
	public void initialize() {
		levels = new ArrayList<>(gameEngine.getGameWorld().getLevelManager().getAllLevels());
		levels.sort(Comparator.comparing(Level::getLevelName));
		
		chooseLevelBackground.setFill(colors.get(0));
		floorOverlay.setFill(colors.get(0));
		levelName.setText(levels.get(0).getLevelName());
		
		pagination = new ArrayList<>();
		for (int i = 0, size = levels.size(); i < size; i++) {
			Circle circle = new Circle(8, Color.GRAY);
			pagination.add(circle);
			paginationPane.getChildren().add(circle);
		}
		
		pagination.get(0).setFill(Color.WHITE);
		levelNameAndPaginationPane.setMouseTransparent(true);
	}

	@FXML
	private void nextButtonClicked() {		
		colorIndex = (colorIndex + 1) % colors.size();
		chooseLevelBackground.setFill(colors.get(colorIndex));
		floorOverlay.setFill(colors.get(colorIndex));
		
		pagination.get(levelIndex).setFill(Color.GRAY);
		levelIndex = (levelIndex + 1) % levels.size();
		levelName.setText(levels.get(levelIndex).getLevelName());
		pagination.get(levelIndex).setFill(Color.WHITE);
	}
	
	@FXML
	private void previousButtonClicked() {
		if (--colorIndex < 0) {
			colorIndex += colors.size();
		}
		chooseLevelBackground.setFill(colors.get(colorIndex));
		floorOverlay.setFill(colors.get(colorIndex));
		
		pagination.get(levelIndex).setFill(Color.GRAY);
		if (--levelIndex < 0) {
			levelIndex += levels.size();
		}
		levelName.setText(levels.get(levelIndex).getLevelName());
		pagination.get(levelIndex).setFill(Color.WHITE);
	}
	
    @FXML
    private void infoButtonClicked() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(GameConstants.pathToVisualization + "LevelInfoScene.fxml"));
		loader.load();
		LevelInfoSceneController controller = loader.getController();
		controller.setPreviousSceneRoot(rootPane);
		controller.setLevelName(levels.get(levelIndex).getLevelName(), Long.toString(
			GameEngine.getInstance().getGameWorld().getLevelManager().getLevelByName(levels.get(levelIndex).getLevelName()).getTotalAttempts()
		));
    }

    @FXML
    private void levelRectangleClicked() throws IOException {		
		Scene scene = rootPane.getScene();
		scene.getRoot().requestFocus();
		
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
    	
		FXMLLoader loader = new FXMLLoader(getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
		loader.load();
		GameSceneController controller = loader.getController();
		
		gameEngine.getGameWorld().createScene(levels.get(levelIndex).getLevelName());
		controller.setPreviousSceneRoot(rootPane);
		controller.init();
		
        gameEngine.getGameStateListener().normalModePlayingStarted();
		gameEngine.start();
    }
	
}
