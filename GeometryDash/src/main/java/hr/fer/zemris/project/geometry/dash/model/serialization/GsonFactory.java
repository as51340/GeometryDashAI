package hr.fer.zemris.project.geometry.dash.model.serialization;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import hr.fer.zemris.project.geometry.dash.ai.ElmanNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.GeneticNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.model.GameObject;
import hr.fer.zemris.project.geometry.dash.model.Session;
import hr.fer.zemris.project.geometry.dash.model.Utils;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;


/**
 * Factory for creating {@linkplain Gson} objects for (de)serialization
 * @author Andi Škrgat 
 */
public class GsonFactory {
	
	/**
	 * Creates {@linkplain Gson} for (de)serializing {@linkplain GameObject}
	 * @return created {@linkplain Gson}
	 */
	public static Gson createGameObjectGson(int y_offset) {
		return new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer(y_offset))
				.setPrettyPrinting()
				.create();
	}
	
	/**
	 * Creates {@linkplain Gson} for saving user/session to file
	 * @return created {@linkplain Gson}
	 */
	public static Gson createUserGson() {
		return new GsonBuilder()
//				.registerTypeAdapter(Session.class, new SessionDeserializer())
				.setPrettyPrinting()
				.create();
	}
	
	/**
	 * Creates {@linkplain Gson} for saving tree to file
	 * @return created {@linkplain Gson}
	 */
	public static Gson createTree() {
		return new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.setPrettyPrinting()
				.create();
	}

	/**
	 * Creates {@linkplain Gson} for saving neural network to file
	 * @return created {@linkplain Gson}
	 */
	public static Gson createNN() {
		return new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeHierarchyAdapter(GeneticNeuralNetwork.class, new NNSerializer())
				.setPrettyPrinting()
				.create();
	}

	/**
	 * Creates {@linkplain Gson} for deserializing neural network to file
	 * @return created {@linkplain Gson}
	 */
	public static Gson createNND() {
		return new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeHierarchyAdapter(GeneticNeuralNetwork.class, new NNDeserializer())
				.setPrettyPrinting()
				.create();
	}


	/**
	 * Creates {@linkplain Gson} for saving neural network to file
	 * @return created {@linkplain Gson}
	 */
	public static Gson createENN() {
		return new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeHierarchyAdapter(ElmanNeuralNetwork.class, new ENNSerializer())
				.setPrettyPrinting()
				.create();
	}

	/**
	 * Creates {@linkplain Gson} for deserializing neural network to file
	 * @return created {@linkplain Gson}
	 */
	public static Gson createENND() {
		return new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeHierarchyAdapter(ElmanNeuralNetwork.class, new ENNDeserializer())
				.setPrettyPrinting()
				.create();
	}


}
