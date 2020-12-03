package hr.fer.zemris.project.geometry.dash.model.serialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;


/**
 * Custom json deserializer for deserializing {@linkplain GameObject}
 * @author Andi Škrgat
 *
 */
public class GameObjectDeserializer implements JsonDeserializer<GameObject> {

	/**
	 * For converting context from level editor to game world
	 */
	private int y_offset;
	
	/**
	 * Constructor
	 * @param y_offset
	 */
	public GameObjectDeserializer(int y_offset) {
		this.y_offset = y_offset;
	}
	
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
		y += y_offset;
		return Utils.createObjectFromName(objectName, new Vector2D(x,y), height, width, iconPath);
	}
}

