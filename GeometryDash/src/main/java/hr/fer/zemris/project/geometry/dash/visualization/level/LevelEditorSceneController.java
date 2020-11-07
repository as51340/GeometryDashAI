package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.listeners.LevelEditorListener;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Controller for level editor.
 * 
 * @author Andi Skrgat Rijesiti da kamera ne moze ic di su kontrole Da se ne
 *         mogu staviti dva objekta na istu poziciju
 */
public class LevelEditorSceneController {

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

	/**
	 * Reference to the game engine
	 */
	private GameEngine gameEngine;

	private LevelEditorListener levelEditorListener = new DefaultLevelEditorListener();

	@FXML
	public void initialize() {

	}

	public void setListeners() {
		setOnMousePressed();
		setOnMouseReleased();
		setOnMouseMoved();
		setOnMouseDragged();
		setActionsForButtons();
	}

	private void setActionsForButtons() {
		addBlock.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Block", addBlock.getImage()));
		});
		addGrass.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("GrassSpike", addGrass.getImage()));

		});
		addPlatform.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Platform", addPlatform.getImage()));
		});
		addSpike.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Spike", addSpike.getImage()));
		});
		spikeLeft.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Spike", spikeLeft.getImage()));
		});
		spikeRight.setOnMouseClicked((e) -> {
			levelEditorListener.newObjectSelected(Utils.createObjectFromName("Spike", spikeRight.getImage()));
		});
		blackColor.setOnMouseClicked((e) -> {
			levelEditorListener.newColorSelected("black");
		});
		blueColor.setOnMouseClicked((e) -> {
			levelEditorListener.newColorSelected("blue");
		});
		redColor.setOnMouseClicked((e) -> {
			levelEditorListener.newColorSelected("red");
		});
		greenColor.setOnMouseClicked((e) -> {
			levelEditorListener.newColorSelected("green");
		});
		grayColor.setOnMouseClicked((e) -> {
			levelEditorListener.newColorSelected("gray");
		});
		purpleColor.setOnMouseClicked((e) -> {
			levelEditorListener.newColorSelected("purple");
		});

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

		@Override
		public void newObjectSelected(GameObject gameObject) {
			getGameEngine().getLevelEditor().getGridAttaching().setCurrObj(gameObject);
		}

		@Override
		public void newColorSelected(String color) {
			addBlock.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "block/" + color + ".png"));
			addGrass.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "grassspike/" + color + ".png"));
			addPlatform.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "platform/" + color + ".png"));
			addSpike.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "spike/" + color + ".png"));
			spikeLeft.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "spike/" + color + "Left.png"));
			spikeRight.setImage(Utils.loadIcon(GameConstants.pathToObstacles + "spike/" + color + "Right.png"));
		}

	}
}
