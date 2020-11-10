package hr.fer.zemris.project.geometry.dash.model;

import java.util.Set;

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
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;


/**
 * Class for serializing and deserializing different types of objects
 * @author Andi Škrgat
 */
public class SerializeUtil {

	/**
	 * Reference to the object for serialization and deserialization
	 */
	private Gson gson;
	
	public SerializeUtil() {
		this.gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
				.setPrettyPrinting()
				.create();
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
	 * Deserializes json and converts it to the set of game objects
	 * @param json json string
	 * @return set od game objects
	 */
	public Set<GameObject> deserialize(String json) {
	    Type listOfMyClassObject = new TypeToken<Set<GameObject>>(){}.getType();
	    return gson.fromJson(json, listOfMyClassObject);
	}
	
	/**
	 * Custom json deserializer for deserializing {@linkplain GameObject}
	 * @author Andi Škrgat
	 *
	 */
	static class GameObjectDeserializer implements JsonDeserializer<GameObject> {

		@Override
		public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			String objectName = jsonObject.get("name").getAsString();
			String iconPath = jsonObject.get("iconPath").getAsString();
			int width = jsonObject.get("width").getAsInt();
			int height = jsonObject.get("height").getAsInt();
			JsonObject vectorPosition = jsonObject.get("currentPosition").getAsJsonObject();
			double x = vectorPosition.get("x").getAsDouble();
			double y = vectorPosition.get("y").getAsDouble();
			y += 50;
			return Utils.createObjectFromName(objectName, new Vector2D(x,y), height, width, iconPath);
		}
		
	}
}
