package hr.fer.zemris.project.geometry.dash.visualization.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.annotations.Expose;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.Drawable;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.threads.DaemonicThreadFactory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;

/**
 * Encapsulates objects on the level editor scene.
 * @author Andi �krgat
 *
 */
public class ObjectsOnGrid implements Drawable{

	/**
	 * Game objects are stored in collection - {@linkplain HashSet} so two objects could not be on the same position on the grid
	 */
	@Expose
	private Set<GameObject> listGameObjects;
	
	/**
	 * For efficiently checking if some position is occupied
	 */
	private Map<Vector2D, GameObject> positionToObject;
	
	/**
	 * Reference to the camera
	 */
	private Camera camera;
	
	/**
	 * Basic constructor that sets camera to the objects on the grid
	 * @param camera
	 */
	public ObjectsOnGrid(Camera camera) {
		listGameObjects = new HashSet<GameObject>();
		positionToObject = new HashMap<Vector2D, GameObject>();
		this.camera = camera;
	}

	/**
	 * Adds game object on the scene
	 * @param gameObject
	 */
	public void addGameObject(GameObject gameObject ) {
		if(listGameObjects.contains(gameObject)) {
			return;
		}
		listGameObjects.add(gameObject);
		positionToObject.put(gameObject.getCurrentPosition(), gameObject);
	}
	
	
	@Override
	public void draw(GraphicsContext graphicsContext) {
		for(GameObject gameObject: listGameObjects) {
			graphicsContext.drawImage(gameObject.getIcon(),  gameObject.getCurrentPosition().getX() - camera.getPosition().getX(),
					gameObject.getCurrentPosition().getY() - camera.getPosition().getY());
		}
	}

	/**
	 * @return the listGameObjects
	 */
	public Set<GameObject> getListGameObjects() {
		return listGameObjects;
	}

	/**
	 * Clears all objects from the scene
	 */
	public void clear() {
		listGameObjects.clear();
		positionToObject.clear();
	}
	
	/**
	 * Removes game object from the scene
	 * @param gameObject game object that user wants to remove from the scene
	 */
	public void remove(GameObject gameObject) {
		listGameObjects.remove(gameObject);
		positionToObject.remove(gameObject.getCurrentPosition(), gameObject);
	}
	
	/**
	 * Removes game object that is on specified position. If none object is on that position, nothing happens
	 * @param position position of game object
	 */
	public void remove(Vector2D position) {
		if(positionToObject.get(position) != null) {
			listGameObjects.remove(positionToObject.get(position));
			positionToObject.remove(position);
		}
	}
	
	/**
	 * Retrieves object that is on the specified positon
	 * @param position position on the grid
	 */
	public GameObject getObjectFromPosition(Vector2D position) {
		return positionToObject.get(position);
	}
	
	/**
	 * Adds loaded objects on the scene
	 * @param loadedObjects
	 */
	public void loadObjectsOnScreen(Set<GameObject> loadedObjects) {
		listGameObjects = loadedObjects;
		for(GameObject go: loadedObjects) {
			positionToObject.put(go.getCurrentPosition(), go);
		}
	}
}
