package hr.fer.zemris.project.geometry.dash.visualization;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import hr.fer.zemris.project.geometry.dash.GeometryDash;
import hr.fer.zemris.project.geometry.dash.ai.AIGameSceneListenerImpl;
import hr.fer.zemris.project.geometry.dash.ai.AiPair;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok.AIAlgorithm;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Tree;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.io.FileIO;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.model.listeners.AIGameSceneListener;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.serialization.GsonFactory;
import hr.fer.zemris.project.geometry.dash.model.serialization.SerializationOfObjects;
import hr.fer.zemris.project.geometry.dash.model.serialization.TreeDeserializer;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.Options;
import hr.fer.zemris.project.geometry.dash.threads.DaemonicThreadFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import javax.swing.*;


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
        gameEngine.getGameWorld().createScene(levels.get(levelIndex).getLevelName());
        
        if (playingMode == PlayingMode.HUMAN) {
            scene.setOnKeyPressed((e) -> {
                if (e.getCode() == KeyCode.W) {
                    for (Player p : GameEngine.getInstance().getGameWorld().getPlayers()) {
                        p.jump();
                        GameEngine.getInstance().getLevelManager().getCurrentLevel().setTotalJumps();
                    }
                    gameEngine.getUserListener().playerJumped();
                }
            });
        }
        
        Player player = new Player(
                new Vector2D(0, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
                new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), playingMode);

        if (playingMode == PlayingMode.HUMAN) {
            gameEngine.getGameStateListener().normalModePlayingStarted();
            gameEngine.getGameWorld().addPlayer(player);
        } else {
            Options options = gameEngine.getSettings().getOptions();
            
            //AIAlgorithm algorithm = new AIAlgorithm(3, 3, options.getAIMode());
            NeuralNetwork nn = null;
            Tree tree;
            switch (options.getAIMode()){
                case NEURAL_NETWORK ->{
                    SerializationOfObjects ser = new SerializationOfObjects(GsonFactory.createNND());
                    nn = ser.deserializeNN(FileIO.readFromJsonFile(GameConstants.pathToGenFolder + "/" + askUserForFileName(PlayingMode.NEURAL_NETWORK) + ".json"));
                    AiPair<NeuralNetwork> pair = new AiPair<NeuralNetwork>(player, nn);
                    gameEngine.getGameWorld().setAiPlayer(pair);
                }
                case GENETIC_PROGRAMMING -> {
                    tree = TreeDeserializer.deserialize(FileIO.readFromJsonFile(GameConstants.pathToGPFolder + "/" + askUserForFileName(PlayingMode.GENETIC_PROGRAMMING) + ".json"));
                    AiPair<Tree> pair = new AiPair<Tree>(player, tree);
                    gameEngine.getGameWorld().setGpPlayer(pair);
                }
                case ELMAN_NEURAL_NETWORK -> {
                    SerializationOfObjects ser = new SerializationOfObjects(GsonFactory.createENND());
                    nn = ser.deserializeENN(FileIO.readFromJsonFile(GameConstants.pathToElmanFolder + "/" + askUserForFileName(PlayingMode.ELMAN_NEURAL_NETWORK) + ".json"));
                    AiPair<NeuralNetwork> pair = new AiPair<NeuralNetwork>(player, nn);
                    gameEngine.getGameWorld().setAiPlayer(pair);
                }
			default -> throw new IllegalArgumentException("Unexpected value: " + options.getAIMode());
            }
            gameEngine.getGameWorld().addPlayer(player);
            gameEngine.getGameStateListener().AIPlayingModeStarted();
            //gameEngine.getGameWorld().setAlgorithm(algorithm);
        }
        GameSceneController controller = loader.getController();
        controller.init(playingMode);
        stage.setScene(scene);


    }

    /**
     * Input AI file
     * @param mode some ai mode
     * @return file name
     */
    private String askUserForFileName(PlayingMode mode) {
        TextInputDialog dialog = new TextInputDialog("Enter AI name");
        dialog.setTitle("AI loader");
        dialog.setHeaderText("Please enter name for your trained AI player(" + mode + ")");

        Optional<String> result;
        while (true) {
            result = dialog.showAndWait();
            if (result.isPresent()) {
                switch (mode) {
                    case NEURAL_NETWORK:
                        if (!(new File(GameConstants.pathToGenFolder + "/" + result.get() + ".json").exists())) {
                            new Alert(Alert.AlertType.ERROR, "File doesnt exists!").showAndWait();
                            continue;
                        }
                        break;
                    case ELMAN_NEURAL_NETWORK:
                        if (!new File(GameConstants.pathToElmanFolder + "/" + result.get() + ".json").exists()) {
                            new Alert(Alert.AlertType.ERROR, "File doesnt exists!").showAndWait();
                            continue;
                        }
                        break;
                    case GENETIC_PROGRAMMING:
                        if (!new File(GameConstants.pathToGPFolder + "/" + result.get() + ".json").exists()) {
                            new Alert(Alert.AlertType.ERROR, "File doesnt exists!").showAndWait();
                            continue;
                        }
                        break;
                }

                break;
            } else {
                switch (mode) {
                    case NEURAL_NETWORK :
                        return "GEN01";
                    case ELMAN_NEURAL_NETWORK:
                        return "ELM01";
                    case GENETIC_PROGRAMMING :
                        return "GP1";
                }
            }
        }

        return result.get();

    }

}
