package hr.fer.zemris.project.geometry.dash.visualization.level;

import java.io.IOException;
import java.util.Set;

import com.google.gson.Gson;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.io.ZipUtil;
import hr.fer.zemris.project.geometry.dash.model.level.Level;
import hr.fer.zemris.project.geometry.dash.model.listeners.LevelEditorListener;
import hr.fer.zemris.project.geometry.dash.model.listeners.MainThreadResultListener;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.serialization.GsonFactory;
import hr.fer.zemris.project.geometry.dash.model.serialization.SerializationOfObjects;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.threads.DaemonicThreadFactory;
import hr.fer.zemris.project.geometry.dash.threads.ResultListenerImpl;
import hr.fer.zemris.project.geometry.dash.visualization.BackgroundSceneController;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for level editor.
 * 
 * @author Andi Skrgat
 */
public class LevelEditorSceneController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Canvas grid;

	@FXML
	private GridPane gridPane;

	@FXML
	private ImageView addSpike;

	@FXML
	private ImageView addBlock;

	@FXML
	private ImageView addPlatform;

	@FXML
	private ImageView addGrass;

	@FXML
	private ImageView addTunel;

	@FXML
	private ImageView addFire;

	@FXML
	private ImageView add;

	@FXML
	private ImageView spikeLeft;

	@FXML
	private ImageView spikeRight;

	@FXML
	private GridPane colorPicker;

	@FXML
	private Button blackColor;

	@FXML
	private Button blueColor;

	@FXML
	private Button redColor;

	@FXML
	private Button greenColor;

	@FXML
	private Button grayColor;

	@FXML
	private Button purpleColor;

	@FXML
	private Button loadButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button resetButton;

	@FXML
	private Button removeButton;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu fileMenu;

	@FXML
	private MenuItem newItem;

	@FXML
	private MenuItem loadItem;

	@FXML
	private MenuItem saveItem;

	@FXML
	private MenuItem saveAsItem;

	@FXML
	private Menu helpMenu;

	@FXML
	private MenuItem aboutItem;

	@FXML
	private ImageView goBack;

	private Pane previousSceneRootPane;

	/**
	 * Reference to the game engine
	 */
	private GameEngine gameEngine;

	/**
	 * Level editor listener
	 */
	private LevelEditorListener levelEditorListener = new DefaultLevelEditorListener();

	/**
	 * Current color
	 */
	private String currColor = "blue";

	/**
	 * Level on which user is currently working
	 */
	private Level level;

	@FXML
	public void initialize() {

	}

	/**
	 * Sets listeners for objects on the scene
	 */
	public void setListeners() {
		setOnMousePressed();
		setOnMouseReleased();
		setOnMouseMoved();
		setOnMouseDragged();
		setActionsForButtons();
		setKeyCombinations();
		setMenuActions();
	}

	/**
	 * Sets actions for menu
	 */
	private void setMenuActions() {
		newItem.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
		newItem.setText("New");
		newItem.setOnAction((e) -> {
			levelEditorListener.reset();
		});
		loadItem.setText("Load");
		loadItem.setAccelerator(KeyCombination.keyCombination("CTRL+O"));
		loadItem.setOnAction((e) -> {
			levelEditorListener.loadLevel();
		});
		saveItem.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
		saveItem.setText("Save");
		saveItem.setOnAction((e) -> {
			if (level == null) {
				levelEditorListener.saveLevel(null);
			} else {
				levelEditorListener.saveLevel(level.getLevelName());
			}
		});
		saveAsItem.setAccelerator(KeyCombination.keyCombination("CTRL+SHIFT+S"));
		saveAsItem.setText("Save As");
		saveAsItem.setOnAction((e) -> {
			levelEditorListener.saveLevel(null);
		});
		aboutItem.setText("About");
		aboutItem.setAccelerator(KeyCombination.keyCombination("CTRL+H"));
		aboutItem.setOnAction((e) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About level editor");
			alert.setContentText(
					"Create your perfect level with level editor. You can create new spikes, block, platforms and bunch of other"
							+ "objects on scene. When you finish, export file and everything will be set for you. Enjoy your AIDash!"
							+ "" + "Game authors");
			alert.showAndWait();
		});

	}

	/**
	 * Sets key combinations for level editor
	 */
	private void setKeyCombinations() {
		grid.getScene().getAccelerators().put(KeyCombination.keyCombination("CTRL+S"), () -> {
			if (level == null) {
				levelEditorListener.saveLevel(null);
			} else {
				levelEditorListener.saveLevel(level.getLevelName());
			}
		});
		grid.getScene().getAccelerators().put(KeyCombination.keyCombination("CTRL+SHIFT+S"), () -> {
			levelEditorListener.saveLevel(null);
		});
		grid.getScene().getAccelerators().put(KeyCombination.keyCombination("CTRL+O"), () -> {
			levelEditorListener.loadLevel();
		});
	}

	/**
	 * Sets actions for clicking on buttons
	 */
	private void setActionsForButtons() {
		addBlock.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Block", new Vector2D(0, 0),
					GameConstants.iconHeight, GameConstants.iconWidth, createPathToObstacle("block")));
		});
		addGrass.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("GrassSpike", new Vector2D(0, 0),
					GameConstants.iconHeight, GameConstants.iconWidth, createPathToObstacle("grassspike")));

		});
		addPlatform.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Platform", new Vector2D(0, 0),
					GameConstants.iconHeight, GameConstants.iconWidth, createPathToObstacle("platform")));
		});
		addSpike.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Spike", new Vector2D(0, 0),
					GameConstants.iconHeight, GameConstants.iconWidth, createPathToObstacle("spike")));
		});
		spikeLeft.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Spike", new Vector2D(0, 0),
					GameConstants.iconHeight, GameConstants.iconWidth, createPathToObstacleLeft("spike")));
		});
		spikeRight.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Spike", new Vector2D(0, 0),
					GameConstants.iconHeight, GameConstants.iconWidth, createPathToObstacleRight("spike")));
		});
		blackColor.setOnMouseClicked((e) -> {
			this.currColor = "black";
			levelEditorListener.newColorSelected("black");
		});
		blueColor.setOnMouseClicked((e) -> {
			this.currColor = "blue";
			levelEditorListener.newColorSelected("blue");
		});
		redColor.setOnMouseClicked((e) -> {
			this.currColor = "red";
			levelEditorListener.newColorSelected("red");
		});
		greenColor.setOnMouseClicked((e) -> {
			this.currColor = "green";
			levelEditorListener.newColorSelected("green");
		});
		grayColor.setOnMouseClicked((e) -> {
			this.currColor = "gray";
			levelEditorListener.newColorSelected("gray");
		});
		purpleColor.setOnMouseClicked((e) -> {
			this.currColor = "purple";
			levelEditorListener.newColorSelected("purple");
		});
		saveButton.setOnMouseClicked((e) -> {
			if (level == null) {
				levelEditorListener.saveLevel(null);
			} else {
				levelEditorListener.saveLevel(level.getLevelName());
			}
		});
		loadButton.setOnMouseClicked((e) -> {
			levelEditorListener.loadLevel();
		});

		resetButton.setOnMouseClicked((e) -> {
			levelEditorListener.reset();
		});
		removeButton.setOnMouseClicked((e) -> {
			levelEditorListener.remove();
		});
	}

	@FXML
	void backButtonClicked(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml"));
		Parent parent = loader.load();
//		Parent parent = FXMLLoader.load(getClass().getResource(GameConstants.pathToVisualization + "BackgroundScene.fxml"));
		Scene backgroundScene = new Scene(parent);
		BackgroundSceneController controller = loader.getController();
		controller.setGameEngine(gameEngine);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(backgroundScene);
		window.show();
	}

	/**
	 * Creates path
	 * 
	 * @param obstacle
	 * @return
	 */
	private String createPathToObstacle(String obstacle) {
		return GameConstants.pathToObstacles + obstacle + "/" + this.currColor + ".png";
	}

	/**
	 * Creates path
	 * 
	 * @param obstacle
	 * @return
	 */
	private String createPathToObstacleLeft(String obstacle) {
		return GameConstants.pathToObstacles + obstacle + "/" + this.currColor + "Left.png";
	}

	/**
	 * Creates path
	 * 
	 * @param obstacle
	 * @return
	 */
	private String createPathToObstacleRight(String obstacle) {
		return GameConstants.pathToObstacles + obstacle + "/" + this.currColor + "Right.png";
	}

	/**
	 * Sets on mouse pressed listener
	 */
	private void setOnMousePressed() {
		grid.getScene().setOnMousePressed((e) -> {
			gameEngine.getLevelEditor().getMouseHandler().setMousePressedButton(e.getButton());
		});
	}

	/**
	 * Sets on mouse moved listener
	 */
	private void setOnMouseMoved() {
		grid.getScene().setOnMouseMoved((e) -> {
			gameEngine.getLevelEditor().getMouseHandler().setMouse_x(e.getX());
			gameEngine.getLevelEditor().getMouseHandler().setMouse_y(e.getY());
		});
	}

	/**
	 * Sets {@linkplain MouseHandler} properties
	 */
	private void setOnMouseDragged() {
		grid.getScene().setOnMouseDragged((e) -> {
			gameEngine.getLevelEditor().getMouseHandler().setMouseDragged(true);
			gameEngine.getLevelEditor().getMouseHandler()
					.setDeltaDrag_x(e.getX() - gameEngine.getLevelEditor().getMouseHandler().getMouse_x());
			gameEngine.getLevelEditor().getMouseHandler()
					.setDeltaDrag_y(e.getY() - gameEngine.getLevelEditor().getMouseHandler().getMouse_y());
		});
	}

	/**
	 * Sets released action
	 */
	private void setOnMouseReleased() {
		grid.getScene().setOnMouseReleased((e) -> {
			gameEngine.getLevelEditor().getMouseHandler().setMousePressedButton(null);
			gameEngine.getLevelEditor().getMouseHandler().setDeltaDrag_x(0);
			gameEngine.getLevelEditor().getMouseHandler().setDeltaDrag_y(0);
		});
	}

	/**
	 * @return the graphicsContext
	 */
	public GraphicsContext getGraphicsContext() {
		return grid.getGraphicsContext2D();
	}

	/**
	 * Sets game engine to the controller
	 * 
	 * @param gameEngine
	 */
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	/**
	 * @return the addSpike
	 */
	public ImageView getAddSpike() {
		return addSpike;
	}

	/**
	 * @param addSpike the addSpike to set
	 */
	public void setAddSpike(ImageView addSpike) {
		this.addSpike = addSpike;
	}

	/**
	 * @return the addBlock
	 */
	public ImageView getAddBlock() {
		return addBlock;
	}

	/**
	 * @param addBlock the addBlock to set
	 */
	public void setAddBlock(ImageView addBlock) {
		this.addBlock = addBlock;
	}

	/**
	 * @return the addPlatform
	 */
	public ImageView getAddPlatform() {
		return addPlatform;
	}

	/**
	 * @param addPlatform the addPlatform to set
	 */
	public void setAddPlatform(ImageView addPlatform) {
		this.addPlatform = addPlatform;
	}

	/**
	 * @return the addGrass
	 */
	public ImageView getAddGrass() {
		return addGrass;
	}

	/**
	 * @param addGrass the addGrass to set
	 */
	public void setAddGrass(ImageView addGrass) {
		this.addGrass = addGrass;
	}

	/**
	 * @return the addTunel
	 */
	public ImageView getAddTunel() {
		return addTunel;
	}

	/**
	 * @param addTunel the addTunel to set
	 */
	public void setAddTunel(ImageView addTunel) {
		this.addTunel = addTunel;
	}

	/**
	 * @return the addFire
	 */
	public ImageView getAddFire() {
		return addFire;
	}

	/**
	 * @param addFire the addFire to set
	 */
	public void setAddFire(ImageView addFire) {
		this.addFire = addFire;
	}

	/**
	 * @return the add
	 */
	public ImageView getAdd() {
		return add;
	}

	/**
	 * @param add the add to set
	 */
	public void setAdd(ImageView add) {
		this.add = add;
	}

	/**
	 * @return the blackColor
	 */
	public Button getBlackColor() {
		return blackColor;
	}

	/**
	 * @param blackColor the blackColor to set
	 */
	public void setBlackColor(Button blackColor) {
		this.blackColor = blackColor;
	}

	/**
	 * @return the blueColor
	 */
	public Button getBlueColor() {
		return blueColor;
	}

	/**
	 * @param blueColor the blueColor to set
	 */
	public void setBlueColor(Button blueColor) {
		this.blueColor = blueColor;
	}

	/**
	 * @return the redColor
	 */
	public Button getRedColor() {
		return redColor;
	}

	/**
	 * @param redColor the redColor to set
	 */
	public void setRedColor(Button redColor) {
		this.redColor = redColor;
	}

	/**
	 * @return the greenColor
	 */
	public Button getGreenColor() {
		return greenColor;
	}

	/**
	 * @param greenColor the greenColor to set
	 */
	public void setGreenColor(Button greenColor) {
		this.greenColor = greenColor;
	}

	/**
	 * @return the grayColor
	 */
	public Button getGrayColor() {
		return grayColor;
	}

	/**
	 * @param grayColor the grayColor to set
	 */
	public void setGrayColor(Button grayColor) {
		this.grayColor = grayColor;
	}

	/**
	 * @return the purpleColor
	 */
	public Button getPurpleColor() {
		return purpleColor;
	}

	/**
	 * @param purpleColor the purpleColor to set
	 */
	public void setPurpleColor(Button purpleColor) {
		this.purpleColor = purpleColor;
	}

	/**
	 * @return the gameEngine
	 */
	public GameEngine getGameEngine() {
		return gameEngine;
	}

	/**
	 * Listens for changes on level editor and updates corresponding class
	 * {@linkplain GridAttaching}
	 * 
	 * @author Andi Skrgat
	 */
	class DefaultLevelEditorListener implements LevelEditorListener {

		/**
		 * Object that serializes and deserializes given object
		 */
		private SerializationOfObjects serializeUtil;

		/**
		 * Constructor for level editor listener
		 */
		public DefaultLevelEditorListener() {
			serializeUtil = new SerializationOfObjects(GsonFactory.createGameObjectGson(0));
		}

		@Override
		public void newObjectSelected(GameObject gameObject) {
			getGameEngine().getLevelEditor().getGridAttaching().setCurrObj(gameObject);
			gameEngine.getLevelEditor().getGridAttaching().setRemoveIntent(false);
		}

		@Override
		public void newColorSelected(String color) {
			getGameEngine().getLevelEditor().getGridAttaching().setCurrObj(null);
			gameEngine.getLevelEditor().getGridAttaching().setRemoveIntent(false);
			addBlock.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "block/" + color + ".png",
					GameConstants.iconWidth, GameConstants.iconHeight));
			addGrass.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "grassspike/" + color + ".png",
					GameConstants.iconWidth, GameConstants.iconHeight));
			addPlatform.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "platform/" + color + ".png",
					GameConstants.iconWidth, GameConstants.iconHeight));
			addSpike.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "spike/" + color + ".png",
					GameConstants.iconWidth, GameConstants.iconHeight));
			spikeLeft.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "spike/" + color + "Left.png",
					GameConstants.iconWidth, GameConstants.iconHeight));
			spikeRight.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "spike/" + color + "Right.png",
					GameConstants.iconWidth, GameConstants.iconHeight));
		}

		@Override
		public void saveLevel(String fileToSave) {
			gameEngine.getLevelEditor().getGridAttaching().setRemoveIntent(false);
			String json = serializeUtil
					.serialize(gameEngine.getLevelEditor().getGridAttaching().getObjectsOnGrid().getListGameObjects());
			String savedTo = ZipUtil.saveToZipFile(GameConstants.pathToLevelsFolder, json, fileToSave);
			if (savedTo != null) {
				gameEngine.getLevelManager().addLevel(savedTo,
						gameEngine.getLevelEditor().getGridAttaching().getObjectsOnGrid().getListGameObjects());
				level = gameEngine.getLevelManager().getLevelByName(savedTo);
			}
		}

		@Override
		public void loadLevel() {
			gameEngine.getLevelEditor().getGridAttaching().setRemoveIntent(false);
			String jsonFromFile = ZipUtil.openZipFile(GameConstants.pathToLevelsFolder, null);
			Set<GameObject> loadedObjects = (Set<GameObject>) serializeUtil.deserializeGameObjects(jsonFromFile);
			if(loadedObjects != null) {
				reset();
				gameEngine.getLevelEditor().getGridAttaching().getObjectsOnGrid().loadObjectsOnScreen(loadedObjects);	
			}
		}

		@Override
		public void reset() {
			gameEngine.getLevelEditor().getGridAttaching().setRemoveIntent(false);
			gameEngine.getLevelEditor().getGridAttaching().getObjectsOnGrid().clear();
			level = null;
		}

		@Override
		public void remove() {
			gameEngine.getLevelEditor().getGridAttaching().setCurrObj(null);
			gameEngine.getLevelEditor().getGridAttaching().setRemoveIntent(true);
		}
	}
}
