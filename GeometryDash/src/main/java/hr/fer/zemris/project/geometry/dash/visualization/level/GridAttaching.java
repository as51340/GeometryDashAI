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

public class GridAttaching implements Drawable, Changeable{

	private Vector2D position;
	
	private MouseHandler mouseHandler;
	
	private Camera camera;
		
	private GameObject currObj;
	
	private ObjectsOnGrid objectsOnGrid;
	
	private double transparentTime = GameConstants.transparentTimeLevel;
	
	private double transparentTimeLeft = 0;
	
	public GridAttaching(MouseHandler mouseHandler, Camera camera) {
		this.position = new Vector2D(0, 0);
		this.mouseHandler = mouseHandler; 
		this.camera = camera;
		objectsOnGrid = new ObjectsOnGrid(camera);
	}
	
	@Override
	public void update() {
		transparentTimeLeft -= transparentTime;
		double x = Math.floor((mouseHandler.getMouse_x() + mouseHandler.getDeltaDrag_x() + camera.getPosition().getX()) / GameConstants.iconWidth);
		double y = Math.floor((mouseHandler.getMouse_y() + mouseHandler.getDeltaDrag_y() + camera.getPosition().getY()) / GameConstants.iconHeight);
		position.setX(x*GameConstants.iconWidth - camera.getPosition().getX());
		position.setY(y*GameConstants.iconHeight - camera.getPosition().getY());
		if(currObj != null && mouseHandler.getMousePressedButton() == MouseButton.PRIMARY && 
				transparentTimeLeft < 0 && this.position.getY() < GameConstants.floorPosition_Y) {
			transparentTimeLeft = transparentTime;
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
	 * @return the transparentTime
	 */
	public double getTransparentTime() {
		return transparentTime;
	}

	/**
	 * @return the transparentTimeLeft
	 */
	public double getTransparentTimeLeft() {
		return transparentTimeLeft;
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
	
}
