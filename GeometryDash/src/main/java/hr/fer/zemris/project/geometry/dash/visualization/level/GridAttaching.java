package hr.fer.zemris.project.geometry.dash.visualization.level;

import java.io.InputStream;
import java.util.NoSuchElementException;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.Changeable;
import hr.fer.zemris.project.geometry.dash.model.Drawable;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.model.settings.character.CharacterObject;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

/**
 * Class that is responsible for attaching objects to grid
 * @author Andi Škrgat
 *
 */
public class GridAttaching implements Drawable, Changeable{

	/**
	 * Current position on the screen
	 */
	private Vector2D position;
	
	/**
	 * Reference to the mouse handler - acts as listener
	 */
	private MouseHandler mouseHandler;
	
	/**
	 * Camera
	 */
	private Camera camera;
		
	/**
	 * Currently selected object for adding it to the grid
	 */
	private GameObject currObj;
	
	/**
	 * Stores objects on grid
	 */
	private ObjectsOnGrid objectsOnGrid;
	
	/**
	 * Stores information if user wants to remove object from the scene
	 */
	private boolean removeIntent;
	
	/**
	 * Constructor
	 * @param mouseHandler mouse listener
	 * @param camera camera
	 */
	public GridAttaching(MouseHandler mouseHandler, Camera camera) {
		this.position = new Vector2D(0, 0);
		this.mouseHandler = mouseHandler; 
		this.camera = camera;
		objectsOnGrid = new ObjectsOnGrid(camera);
	}
	
	@Override
	public void update() {
		double x = Math.floor((mouseHandler.getMouse_x() + mouseHandler.getDeltaDrag_x() + camera.getPosition().getX()) / GameConstants.iconWidth);
		double y = Math.floor((mouseHandler.getMouse_y() + mouseHandler.getDeltaDrag_y() + camera.getPosition().getY()) / GameConstants.iconHeight);
		position.setX(x*GameConstants.iconWidth - camera.getPosition().getX());
		position.setY(y*GameConstants.iconHeight - camera.getPosition().getY());
		if(removeIntent == true && mouseHandler.getMousePressedButton() == MouseButton.PRIMARY  && this.position.getY() < GameConstants.floorPosition_Y) {
			Vector2D positionToRemove = new Vector2D(x*GameConstants.iconWidth, y * GameConstants.iconHeight);
			objectsOnGrid.remove(positionToRemove);
		}
		else if(currObj != null && mouseHandler.getMousePressedButton() == MouseButton.PRIMARY  && this.position.getY() < GameConstants.floorPosition_Y) {
			Vector2D newPosition = new Vector2D(x*GameConstants.iconWidth, y * GameConstants.iconHeight);
			GameObject newGameObject = currObj.copy();
			newGameObject.setCurrentPosition(newPosition);
			objectsOnGrid.addGameObject(newGameObject);
		}
	}
	
	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.setGlobalAlpha(0.5);
		if(currObj != null && this.position.getY() < GameConstants.floorPosition_Y) { //draw only if it is above ground
			graphicsContext.drawImage(currObj.getIcon(), this.position.getX(), this.position.getY());	
		}
		graphicsContext.setGlobalAlpha(1);	
		objectsOnGrid.draw(graphicsContext);
	}
	
	/**
	 * @return the position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * @return the mouseHandler
	 */
	public MouseHandler getMouseHandler() {
		return mouseHandler;
	}

	/**
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}

	
	/**
	 * @return the objectsOnGrid
	 */
	public ObjectsOnGrid getObjectsOnGrid() {
		return objectsOnGrid;
	}
	
	/**
	 * @return the currObj
	 */
	public GameObject getCurrObj() {
		return currObj;
	}

	/**
	 * @param currObj the currObj to set
	 */
	public void setCurrObj(GameObject currObj) {
		this.currObj = currObj;
	}

	/**
	 * @return the removeIntent
	 */
	public boolean isRemoveIntent() {
		return removeIntent;
	}

	/**
	 * @param removeIntent the removeIntent to set
	 */
	public void setRemoveIntent(boolean removeIntent) {
		this.removeIntent = removeIntent;
	}
	
}
