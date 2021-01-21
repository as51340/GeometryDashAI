package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.ai.AIGameSceneListenerImpl;
import hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok.AIAlgorithm;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.model.listeners.AIGameSceneListener;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.serialization.TreeDeserializer;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.Options;
import hr.fer.zemris.project.geometry.dash.threads.DaemonicThreadFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;


public class ChooseLevelController extends MainOptionsController {

    private GameEngine gameEngine = GameEngine.getInstance();

    private List<Level> levels;

    private List<Color> colors = Arrays.asList(Color.DODGERBLUE, Color.BLUEVIOLET, Color.PURPLE, Color.CRIMSON,
            Color.ORANGE, Color.YELLOWGREEN, Color.LIGHTGREEN, Color.CYAN);

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
    private ProgressBar progressBar;
    
    @FXML
    private Label percentage;
    
    private AIGameSceneListener list = new AIGameSceneListenerImpl();

    @FXML
    public void initialize() {
        levels = new ArrayList<>(gameEngine.getLevelManager().getAllLevels());
        levels.sort(Comparator.comparing(Level::getLevelName));

        chooseLevelBackground.setFill(colors.get(0));
        floorOverlay.setFill(colors.get(0));
        levelName.setText(levels.get(0).getLevelName());
        progressBar.setProgress(levels.get(0).getLevelPercentagePassNormalMode());
        percentage.setText(String.format("%d", (int)(progressBar.getProgress()*100)) + "%");

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
        progressBar.setProgress(levels.get(levelIndex).getLevelPercentagePassNormalMode());
        percentage.setText(String.format("%d", (int)(progressBar.getProgress()*100)) + "%");
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
        progressBar.setProgress(levels.get(levelIndex).getLevelPercentagePassNormalMode());
        percentage.setText(String.format("%d", (int)(progressBar.getProgress()*100)) + "%");
    }

    @FXML
    private void infoButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(GameConstants.pathToVisualization + "LevelInfoScene.fxml"));
        loader.load();
        LevelInfoSceneController controller = loader.getController();
        controller.setPreviousSceneRoot(rootPane);
        controller.setLevelName(levels.get(levelIndex).getLevelName(), Long.toString(GameEngine.getInstance()
                .getLevelManager().getLevelByName(levels.get(levelIndex).getLevelName()).getTotalAttempts()));
    }

    @FXML
    private void levelRectangleClicked() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(GameConstants.pathToVisualization + "GameScene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = GeometryDash.createScaledScene(root, stage);
        scene.getRoot().requestFocus();

        gameEngine.setGameWorld();

        PlayingMode playingMode = (PlayingMode) stage.getUserData();

//		Player player = new Player(new Vector2D(0, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
//				new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), playingMode);
//		gameEngine.getGameWorld().addPlayer(player);
//		
        
        gameEngine.getGameWorld().createScene(levels.get(levelIndex).getLevelName());
        
        for (int i = 0; i < 1; i++) {
            Player player = new Player(new Vector2D(i*20, 490),
                    new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), playingMode);
            gameEngine.getGameWorld().addPlayer(player);
        }


        if (playingMode == PlayingMode.HUMAN) {
            scene.setOnKeyPressed((e) -> {
                if (e.getCode() == KeyCode.W) {
//                	System.out.println("Stisnuta tipka W"); shuld wor
                    for (Player p : GameEngine.getInstance().getGameWorld().getPlayers()) {
                        p.jump();
                        GameEngine.getInstance().getLevelManager().getCurrentLevel().setTotalJumps();
                    }
                    gameEngine.getUserListener().playerJumped();
                }
            });
        } else {
            System.out.println("Moram ucitati automatskog igraca iz datoteke");
        }

        GameSceneController controller = loader.getController();
//		controller.setPreviousSceneRoot(rootPane);
        controller.init(PlayingMode.HUMAN);

//		Thread.sleep(500);
        stage.setScene(scene);


        if (playingMode == PlayingMode.HUMAN) {
            gameEngine.getGameStateListener().normalModePlayingStarted();
        } else {
            Options options = gameEngine.getSettings().getOptions();
            Player player = new Player(
                    new Vector2D(0, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
                    new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), options.getAIMode());

            //TODO remove this when deserialization is implemented
            AIAlgorithm algorithm = new AIAlgorithm(3, 3, options.getAIMode());

            switch (options.getAIMode()){
                case NEURAL_NETWORK ->{
                    //TODO deserialize Neural Network
                }
                case GENETIC_PROGRAMMING -> {
                    
                }
                case ELMAN_NEURAL_NETWORK -> {
                    //TODO deserialize Elman Neural Network
                }
            }

            Object lockObject = new Object(); //locking object
            algorithm.setLockObj(lockObject);
            gameEngine.getGameWorld().setLockObject(lockObject);

            gameEngine.getGameWorld().addPlayer(player);
            algorithm.getPlayerNeuralNetworkMap().put(player, null);

            gameEngine.getGameStateListener().AIPlayingModeStarted();
            gameEngine.getGameWorld().setAlgorithm(algorithm);

            Thread t = new Thread(() -> {
                try {
                    algorithm.runAlgorithm();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName("AI algorithm");
            t.setDaemon(true);
            t.start();


        }

    }

}
