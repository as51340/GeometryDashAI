package hr.fer.zemris.project.geometry.dash.model.serialization;

import java.lang.reflect.Type;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Action;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Tree;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.TreeNode;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.TreeUtil;

public class TreeDeserializer implements JsonDeserializer<Tree> {

	@Override
	public Tree deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		JsonObject actionObject = jsonObject.getAsJsonObject("action");
		Action action = Action.parse(actionObject.get("actionType").getAsString());
		TreeNode root = new TreeNode(action);
		JsonArray children = jsonObject.get("children").getAsJsonArray();
		initialize(root, children);
		
		root.setValue(TreeUtil.dfsOnTree(root, 1));
		return new Tree(root);
	}
	
	public static Tree deserialize(String json) {
		SerializationOfObjects ser = new SerializationOfObjects(GsonFactory.createTree());
		JsonElement jsonElement = ser.getGson().fromJson(json, JsonElement.class);
		JsonObject object = jsonElement.getAsJsonObject();
		
		JsonObject jsonObject = object.getAsJsonObject("root");
		JsonObject actionObject = jsonObject.getAsJsonObject("action");
		Action action = Action.parse(actionObject.get("actionType").getAsString());
		TreeNode root = new TreeNode(action);
		JsonArray children = jsonObject.get("children").getAsJsonArray();
		initialize(root, children);
		
		root.setValue(TreeUtil.dfsOnTree(root, 1));
		return new Tree(root);
	}

	/**
	 * Recursive method which initializes tree
	 * @param root current node
	 * @param children children of current node
	 */
	private static void initialize(TreeNode node, JsonArray children) {
		Iterator<JsonElement> it = children.iterator();
		while(it.hasNext()) {
			JsonElement jsonElement = it.next();
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			TreeNode child;
			if(jsonObject.has("action")) {
				JsonObject action = jsonObject.getAsJsonObject("action");
				child = new TreeNode(Action.parse(action.get("actionType").getAsString()));
				JsonArray newChildren = jsonObject.get("children").getAsJsonArray();
				initialize(child, newChildren);
			} else {
				child = new TreeNode(jsonObject.get("value").getAsDouble());
			}
			
			node.addChild(child);
		}
	}
	
}
