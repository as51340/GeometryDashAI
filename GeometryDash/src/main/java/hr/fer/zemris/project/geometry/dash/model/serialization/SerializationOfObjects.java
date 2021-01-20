package hr.fer.zemris.project.geometry.dash.model.serialization;

import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.lang.reflect.Type;

import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Tree;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Session;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;


/**
 * Class for serializing and deserializing different types of objects
 * @author Andi Škrgat
 */
public class SerializationOfObjects {

	/**
	 * Reference to the object for serialization and deserialization
	 */
	private Gson gson;
		
	public SerializationOfObjects(Gson gson) {
		this.gson = gson;
	}

	
	/**
	 * Serializes given object
	 * @param obj object to be serializes
	 * @return json string
	 */
	public String serialize(Object obj) {
		return gson.toJson(obj);
	}
	
	/**
	 * Deserializes json and converts it to the set of requested objects
	 * @param json json string
	 * @return set of objects
	 */
	public Set<GameObject> deserializeGameObjects(String json) {
	    Type listOfMyClassObject = new TypeToken<Set<GameObject>>(){}.getType();
	    return gson.fromJson(json, listOfMyClassObject);
	}
	
	/**
	 * Deserializes user with his context
	 * @param json json to be deserialized
	 * @return deserialized content
	 */
	public Session deserializeUser(String json) {
	    return gson.fromJson(json, Session.class);
	}
	
	/**
	 * Deserializes tree
	 * @param json json to be deserialized
	 * @return deserialized content
	 */
	public Tree deserializeTree(String json) {
		return TreeDeserializer.deserialize(json);
//		return gson.fromJson(json, Tree.class);
	}


	
	/**
	 * @return gson
	 */
	public Gson getGson() {
		return gson;
	}
	
}
