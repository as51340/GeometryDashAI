package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.Changeable;
import hr.fer.zemris.project.geometry.dash.model.Drawable;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class GridAttaching implements Drawable, Changeable{

	private Vector2D position;
	
	private MouseHandler mouseHandler;
	
	private Camera camera;
		
	private Image currImgObj = Utils.loadIcon("geom-dash-blue-green-icon.jpg");
	
	private String currNameObj = null;
	
	private ObjectsOnGrid objectsOnGrid;
	
	private double transparentTime = GameConstants.transparentTimeLevel;
	
	private double transparentTimeLeft = 0;
	
	public GridAttaching(MouseHandler mouseHandler, Camera camera) {
		this.position = new Vector2D(0, 0);
		this.mouseHandler = mouseHandler; 
		this.camera = camera;
		objectsOnGrid = new ObjectsOnGrid(camera);
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
	 * @return the currImgObj
	 */
	public Image getCurrImgObj() {
		return currImgObj;
	}

	/**
	 * @return the currNameObj
	 */
	public String getCurrNameObj() {
		return currNameObj;
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
	 * @param currImgObj the currImgObj to set
	 */
	public void setCurrImgObj(Image currImgObj) {
		this.currImgObj = currImgObj;
	}

	/**
	 * @param currNameObj the currNameObj to set
	 */
	public void setCurrNameObj(String currNameObj) {
		this.currNameObj = currNameObj;
	}

	@Override
	public void update() {
		transparentTimeLeft -= transparentTime;
		double x = Math.floor((mouseHandler.getMouse_x() + mouseHandler.getDeltaDrag_x() + camera.getPosition().getX()) / GameConstants.iconWidth);
		double y = Math.floor((mouseHandler.getMouse_y() + mouseHandler.getDeltaDrag_y() + camera.getPosition().getY()) / GameConstants.iconHeight);
		position.setX(x*GameConstants.iconWidth - camera.getPosition().getX());
		position.setY(y*GameConstants.iconHeight - camera.getPosition().getY());
		if(mouseHandler.getMousePressedButton() == MouseButton.PRIMARY && transparentTimeLeft < 0 && this.position.getY() < GameConstants.floorPosition_Y) {
			transparentTimeLeft = transparentTime;
			Vector2D newPosition = new Vector2D(x*GameConstants.iconWidth, y * GameConstants.iconHeight);
			objectsOnGrid.addMapping(Utils.copyImage(currImgObj), newPosition);
		}
	}
	
	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.setGlobalAlpha(0.5);
		if(this.position.getY() < GameConstants.floorPosition_Y) { //draw only if it is above ground
			graphicsContext.drawImage(currImgObj, this.position.getX(), this.position.getY());	
		}
		graphicsContext.setGlobalAlpha(1);
		objectsOnGrid.draw(graphicsContext);
	}
}
