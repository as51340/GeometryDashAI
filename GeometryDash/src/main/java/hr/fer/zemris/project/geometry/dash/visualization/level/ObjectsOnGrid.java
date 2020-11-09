package hr.fer.zemris.project.geometry.dash.visualization.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.annotations.Expose;

import hr.fer.zemris.project.geometry.dash.model.Camera;
import hr.fer.zemris.project.geometry.dash.model.Drawable;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;

/**
 * Encapsulates objects on the level editor scene.
 * @author Andi Škrgat
 *
 */
public class ObjectsOnGrid implements Drawable{

	/**
	 * Game objects are stored in collection - {@linkplain HashSet} so two objects could not be on the same position on the grid
	 */
	@Expose
	private Set<GameObject> listGameObjects;
	
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
		this.camera = camera;
	}

	/**
	 * Adds game object on the scene
	 * @param gameObject
	 */
	public void addGameObject(GameObject gameObject ) {
		listGameObjects.add(gameObject);
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
	}
	
	/**
	 * Removes game object from the scene
	 * @param gameObject game object that user wants to remove from the scene
	 */
	public void remove(GameObject gameObject) {
		listGameObjects.remove(gameObject);
	}
	
	/**
	 * Removes game object that is on specified position. If none object is on that position, nothing happens
	 * @param position position of game object
	 */
	public void remove(Vector2D position) {
		Iterator<GameObject> it = listGameObjects.iterator();
		while(it.hasNext()) {
			GameObject gameObject = it.next();
			if(gameObject.getCurrentPosition().equals(position)) {
				it.remove();
				return;
			}
		}
	}
	
	/**
	 * Retrieves object that is on the specified positon
	 * @param position position on the grid
	 */
	public void getObjectFromPosition(Vector2D position) {
		//TODO - this is for removing objects from the screen 
//		implement some useful method in GameObject if it can be done or keep HashMap with position and game object - memory unefficient
		//TODO - multithreading where appropriate
	}
	
	/**
	 * Adds loaded objects on the scene
	 * @param loadedObjects
	 */
	public void loadObjectsOnScreen(Set<GameObject> loadedObjects) {
		listGameObjects = loadedObjects;
	}
}
