package hr.fer.zemris.project.geometry.dash.visualization.level;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.drawables.Vector2D;
import javafx.scene.input.MouseEvent;

public class DragCameraControls {

	/**
	 * It must have referene on the camera, so it can update positons. Camera for grid - level editor
	 */
	private Camera camera;
	
	public DragCameraControls() {
		camera = new Camera();
	}
	
	/**
	 * @return camera
	 */
	public Camera getCamera() {
		return this.camera;
	}
	
	public void updateCamera(Vector2D cameraPosition) {
		camera.getPosition().setPos(cameraPosition.getX(), cameraPosition.getY());
	}
	
}
