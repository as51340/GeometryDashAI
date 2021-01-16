package hr.fer.zemris.project.geometry.dash.model.listeners;

import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.genetic_programming.Tree;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;

/**
 * Listener for game scene
 * @author Andi Škrgat
 *
 */
public interface AIGameSceneListener {

	/**
	 * Stop training
	 */
	void stop();
	
	/**
	 * Saves network
	 * @param json content of network
	 */
	void saveGP(Tree bestTree);
	
	/**
	 * Saves elman neural network
	 * @param nn
	 */
	void saveElman(NeuralNetwork nn);
	
	/**
	 * Save genetic algorithm
	 * @param nn
	 */
	void saveGen(NeuralNetwork nn);
	
	/**
	 * Load genetic programming tree
	 */
	void loadGP();
	
	/**
	 * Load elman neural network
	 */
	void loadElman();
	
	/**
	 * Load genetic neural network
	 */
	void loadGen();
	
}
