package hr.fer.zemris.project.geometry.dash.ai;

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
		TreeUtil.printTree(bestTree.getRoot(), 0);
		String fileName = askUserForFileName();
		if(fileName == null) {
			return; //handlay fail u save GP
		}
		Gson gson = GsonFactory.createTree();
		SerializationOfObjects ser = new SerializationOfObjects(gson);
		String json = ser.serialize(bestTree);
		Tree loadedTree = ser.deserializeTree(json);
		if(!loadedTree.equals(bestTree)) {
			TreeUtil.printTree(loadedTree.getRoot(), 0);
			throw new IllegalStateException("Ser doesn't work good"); 
		}
		FileIO.createJsonFile(GameConstants.pathToGPFolder + "/" + fileName, json);
	}

	@Override
	public void saveElman(NeuralNetwork nn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGen(NeuralNetwork nn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGP() {
		// TODO Auto-generated method stub
		
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

}
