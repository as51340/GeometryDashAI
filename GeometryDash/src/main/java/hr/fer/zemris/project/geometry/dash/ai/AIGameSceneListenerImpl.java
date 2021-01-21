package hr.fer.zemris.project.geometry.dash.ai;

import java.io.File;
import java.util.Optional;

import com.google.gson.Gson;

import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Tree;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.TreeUtil;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.io.FileIO;
import hr.fer.zemris.project.geometry.dash.model.listeners.AIGameSceneListener;
import hr.fer.zemris.project.geometry.dash.model.serialization.GsonFactory;
import hr.fer.zemris.project.geometry.dash.model.serialization.SerializationOfObjects;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.control.TextInputDialog;

/**
 * Game scene listener
 * @author Andi Škrgat
 *
 */
public class AIGameSceneListenerImpl implements AIGameSceneListener{

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGP(Tree bestTree) {
		save(bestTree, GsonFactory.createTree(), GameConstants.pathToGPFolder);
	}


	@Override
	public void saveElman(NeuralNetwork nn) {
		save(nn, GsonFactory.createNN(), GameConstants.pathToElmanFolder);
	}

	@Override
	public void saveGen(NeuralNetwork nn) {
		save(nn, GsonFactory.createNN(), GameConstants.pathToGenFolder);
	}

	@Override
	public Tree loadGP() {
		String fileName = askUserForFileName();
		String json = FileIO.readFromJsonFile(fileName);
		return new SerializationOfObjects(GsonFactory.createTree()).deserializeTree(json);
	}

	@Override
	public void loadElman() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGen() {
		// TODO Auto-generated method stub
		
	}
	
	private String askUserForFileName() {
		TextInputDialog dialog = new TextInputDialog("Enter AI name");
		dialog.setTitle("AI loader");
		dialog.setHeaderText("Please enter name for your trained AI player(without extension)");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
		    return result.get();
		} else {
			return null;
		}
	}

	/**
	 * Common method to save files
	 * @param objectToSave
	 * @param gson
	 * @param path
	 */
	private void save(Object objectToSave, Gson gson, String path) {
		String filename;
		do {
			filename = askUserForFileName();
		}while(filename == null);

		SerializationOfObjects ser = new SerializationOfObjects(gson);
		String json = null;
		if(objectToSave instanceof Tree) {
			Tree bestTree = (Tree) objectToSave;
			json = ser.serialize(bestTree);
			Tree loadedTree = ser.deserializeTree(json);
			if(!loadedTree.equals(bestTree)) {
				TreeUtil.printTree(loadedTree.getRoot(), 0);
				throw new IllegalStateException("Ser doesn't work good");
			}

		} else if(objectToSave instanceof NeuralNetwork) {
			NeuralNetwork nn = (NeuralNetwork) objectToSave;
			json = ser.serialize(nn);

			NeuralNetwork loadedNN = new SerializationOfObjects(GsonFactory.createNND()).deserializeNN(json);

		}

		FileIO.createJsonFile(path + "/" + filename + ".json", json);

	}
}
