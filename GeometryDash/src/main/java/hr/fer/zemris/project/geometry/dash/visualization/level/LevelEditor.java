package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.Changeable;
import hr.fer.zemris.project.geometry.dash.model.Drawable;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.threads.DaemonicThreadFactory;
import hr.fer.zemris.project.geometry.dash.visualization.level.mouse.MouseHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Full featured level editor
 * 
 * @author Andi Škrgat
 *
 */
public class LevelEditor implements Drawable, Changeable {

	/**
	 * Object for drawing
	 */
	private GraphicsContext graphicsContext;

	/**
	 * Camera
	 */
	private Camera camera;

	/**
	 * Mouse listener
	 */
	private MouseHandler mouseHandler;

	/**
	 * Class for attaching objects
	 */
	private GridAttaching gridAttaching;

	/**
	 * Dragging functionality
	 */
	private DragCameraControls dragCameraControls;

	/**
	 * Constructor
	 */
	public LevelEditor() {
		camera = new Camera();
		mouseHandler = new MouseHandler();
		dragCameraControls = new DragCameraControls(camera, mouseHandler);
		gridAttaching = new GridAttaching(mouseHandler, camera);
	}

	/**
	 * When game is level editor mode then graphics is set and it can be used for
	 * drawing objects on grid
	 * 
	 * @param graphicsContext
	 */
	public void setGraphicsContext(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
	}

	@Override
	public void update() {
		dragCameraControls.update();
		gridAttaching.update();
	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.clearRect(0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);
		dragCameraControls.draw(graphicsContext);
		gridAttaching.draw(graphicsContext);
	}

	/**
	 * This method is called from game engine so graphics context can be initialized
	 */
	public void draw() {
		draw(this.graphicsContext);
	}

	/**
	 * @return the graphicsContext
	 */
	public GraphicsContext getGraphicsContext() {
		return graphicsContext;
	}

	/**
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * @return the mouseHandler
	 */
	public MouseHandler getMouseHandler() {
		return mouseHandler;
	}

	/**
	 * @return the gridAttaching
	 */
	public GridAttaching getGridAttaching() {
		return gridAttaching;
	}

	/**
	 * @return the dragCameraControls
	 */
	public DragCameraControls getDragCameraControls() {
		return dragCameraControls;
	}

}
