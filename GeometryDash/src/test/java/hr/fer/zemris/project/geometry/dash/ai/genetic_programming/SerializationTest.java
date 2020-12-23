package hr.fer.zemris.project.geometry.dash.ai.genetic_programming;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import hr.fer.zemris.project.geometry.dash.model.serialization.GsonFactory;
import hr.fer.zemris.project.geometry.dash.model.serialization.SerializationOfObjects;

public class SerializationTest {

/*	@Test
	void simpleTestPlus() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(3, TreeUtil.dfsOnTree(root, 1));
		SerializationOfObjects ser = new SerializationOfObjects(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create());
		System.out.print(ser.serialize(root));
	}
	
	@Test
	void twoLevelSimplePlusMultiply() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(new Action(ActionType.MULTIPLY));
		TreeNode node2 = new TreeNode(1);
		TreeNode node3 = new TreeNode(2);
		TreeNode node4 = new TreeNode(3);
		node1.addChild(node3);
		node1.addChild(node4);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(7, TreeUtil.dfsOnTree(root, 1));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		SerializationOfObjects ser = new SerializationOfObjects(gson);
		String json = ser.serialize(root);
		System.out.println(json);
		//System.out.println(gson.toJson(ser.serialize(root)));
		//JsonObject jsonObject = json.getAsJsonObject();
	}
	
/*	@Test
	void simpleTestMinus() {
		TreeNode root = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(-1, TreeUtil.dfsOnTree(root, 1));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		SerializationOfObjects ser = new SerializationOfObjects(gson);
		String strJson = ser.serialize(root);
		System.out.println(strJson);
//		Json json = ser.
//		JsonObject jsonObject = json
	}
	
	@Test
	void simpleTestPlus() {
		TreeNode root = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		root.addChild(node1);
		root.addChild(node2);
		assertEquals(3, TreeUtil.dfsOnTree(root, 1));
		SerializationOfObjects ser = new SerializationOfObjects(GsonFactory.createTree());
		String json = ser.serialize(root);
//		System.out.print(json);
		JsonElement jsonElement = ser.getGson().fromJson(json, JsonElement.class);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonObject action = jsonObject.getAsJsonObject("action");
		String actionType = action.get("actionType").getAsString();
//		System.out.println(jsonElement);
//		System.out.println(jsonObject);
//		System.out.println(action);
//		System.out.println(actionType);
//		Action a = Action.parse(actionType);
//		System.out.println(a);
		TreeNode root1 = new TreeNode(Action.parse(actionType));
		JsonArray children = jsonObject.get("children").getAsJsonArray();
		System.out.println(children);
		Iterator<JsonElement> iterator = children.iterator();
		while(iterator.hasNext()) {
			JsonElement elem = iterator.next();
			JsonObject obj = elem.getAsJsonObject();
			TreeNode child;
			if(obj.has("action")) {
				JsonObject action1 = obj.getAsJsonObject("action"); 
				child = new TreeNode(Action.parse(action1.get("actionType").getAsString()));
			} else {
				child = new TreeNode(obj.get("value").getAsDouble());
			}
			root1.addChild(child);
		}
		
		root1.setValue(TreeUtil.dfsOnTree(root1, 1));
		System.out.println(ser.serialize(root1));
	}
*/
	
	@Test
	void complicatedBinary() {
		TreeNode root = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node1 = new TreeNode(new Action(ActionType.PLUS));
		TreeNode node2 = new TreeNode(new Action(ActionType.DIVIDE));
		root.addChild(node1);
		root.addChild(node2);
		TreeNode node3 = new TreeNode(new Action(ActionType.MULTIPLY));
		TreeNode node4 = new TreeNode(new Action(ActionType.POWER));
		node1.addChild(node3);
		node1.addChild(node4);
		TreeNode node5 = new TreeNode(new Action(ActionType.MINUS));
		TreeNode node6 = new TreeNode(new Action(ActionType.DIVIDE));
		node2.addChild(node5);
		node2.addChild(node6);
		TreeNode node7 = new TreeNode(3);
		TreeNode node8 = new TreeNode(2);
		node5.addChild(node7);
		node5.addChild(node8);
		TreeNode node9 = new TreeNode(8);
		TreeNode node10 = new TreeNode(4);
		node6.addChild(node9);
		node6.addChild(node10);
		TreeNode node11 = new TreeNode(4);
		TreeNode node12 = new TreeNode(3);
		node3.addChild(node11);
		node3.addChild(node12);
		TreeNode node13 = new TreeNode(4);
		TreeNode node14 = new TreeNode(2);
		node4.addChild(node13);
		node4.addChild(node14);
		assertEquals(27.5, TreeUtil.dfsOnTree(root, 1));
		SerializationOfObjects ser = new SerializationOfObjects(GsonFactory.createTree());
		String json = ser.serialize(root);
		System.out.println(json);
		JsonElement jsonElement = ser.getGson().fromJson(json, JsonElement.class);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonObject action = jsonObject.getAsJsonObject("action");
		TreeNode nroot = new TreeNode(Action.parse(action.get("actionType").getAsString()));
		JsonArray children = jsonObject.get("children").getAsJsonArray();
		initialize(nroot, children);
		System.out.println("Deserijalizacija: ");
		nroot.setValue(TreeUtil.dfsOnTree(nroot, 1));
		System.out.println(ser.serialize(nroot));
		if(root.equals(nroot)) {
			System.out.println("OK!");
		} else {
			System.out.println("Piši ispočetka!");
		}
	}
	
	private void initialize(TreeNode node, JsonArray children) {
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
