package hr.fer.zemris.project.geometry.dash.model.listeners;

import hr.fer.zemris.project.geometry.dash.ai.ElmanNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.GeneticNeuralNetwork;
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
	void saveElman(ElmanNeuralNetwork nn);
	
	/**
	 * Save genetic algorithm
	 * @param nn
	 */
	void saveGen(GeneticNeuralNetwork nn);
	
	/**
	 * Load genetic programming tree
	 */
	Tree loadGP();
	
	/**
	 * Load elman neural network
	 */
	NeuralNetwork loadElman();
	
	/**
	 * Load genetic neural network
	 */
	NeuralNetwork loadGen();
	
}
