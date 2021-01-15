package hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.function.DoubleUnaryOperator;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.ElmanNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.GeneticNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.GameSceneController;
import javafx.application.Platform;

public class AIAlgorithm {

	private static final int POPULATION_SIZE = 300;
	private static final int REPEAT = 500;
	private static final double MUTATION_RATE = 0.1;
	private static final int INPUT_LAYER_SIZE = AIConstants.numOfClosestObstacles * 3 + 1;
	private final int numberOfHiddenLayers;
	private final int numberPerHiddenLayer;
	private DoubleUnaryOperator activationFunction = AIConstants.activationFunction;

	private Map<Player, NeuralNetwork> playerNeuralNetworkMap;
	private int sumOfAllFitnesses;

	private Object lockObj;

	private Object generationLockObject;

	/**
	 * @return the generationLockObject
	 */
	public Object getGenerationLockObject() {
		return generationLockObject;
	}

	/**
	 * @param generationLockObject the generationLockObject to set
	 */
	public void setGenerationLockObject(Object generationLockObject) {
		this.generationLockObject = generationLockObject;
	}

	/**
	 * Which algorithm to run - Genetic or Elman.
	 */
	private PlayingMode mode;

	/**
	 * If playing mode is set to ELMAN_NEURAL_NETWORK, numberOfHiddenLayers is
	 * ignored and silently set to 1
	 * 
	 * @param numberOfHiddenLayers
	 * @param numberPerHiddenLayer
	 * @param mode
	 */
	public AIAlgorithm(int numberOfHiddenLayers, int numberPerHiddenLayer, PlayingMode mode) {
		if (!(mode == PlayingMode.NEURAL_NETWORK || mode == PlayingMode.ELMAN_NEURAL_NETWORK))
			throw new IllegalArgumentException("Mode has to be NEURAL_NETWORK or ELMAN_NEURAL_NETWORK");
		if (mode == PlayingMode.ELMAN_NEURAL_NETWORK) {
			this.numberOfHiddenLayers = 1;
		} else {
			this.numberOfHiddenLayers = numberOfHiddenLayers;
		}

		playerNeuralNetworkMap = new LinkedHashMap<>();
		this.numberPerHiddenLayer = numberPerHiddenLayer;
		this.mode = mode;
	}

	/**
	 * @return the playerNeuralNetworkMap
	 */
	public Map<Player, NeuralNetwork> getPlayerNeuralNetworkMap() {
		return playerNeuralNetworkMap;
	}

	/**
	 * @param playerNeuralNetworkMap the playerNeuralNetworkMap to set
	 */
	public void setPlayerNeuralNetworkMap(Map<Player, NeuralNetwork> playerNeuralNetworkMap) {
		this.playerNeuralNetworkMap = playerNeuralNetworkMap;
	}

	public AIAlgorithm(int numberOfHiddenLayers, int numberPerHiddenLayer, DoubleUnaryOperator activationFunction,
			PlayingMode mode) {
		this(numberOfHiddenLayers, numberPerHiddenLayer, mode);
		this.activationFunction = activationFunction;
	}

	public void runAlgorithm() throws InterruptedException {
		initialize(); // zasto sada stvara nove mreze
		for (int i = 0; i < REPEAT; i++) {
//			System.out.println("Inicijalizacija gotova " + (i + 1) + "-ti put");
			sumOfAllFitnesses = 0;
			GameEngine.getInstance().getGameWorld().setUnlockingCondition(false);
			GameEngine.getInstance().getGameWorld().setLevelPassed(false);
			selection();
			System.out.println("Selekcija gotova " + (i + 1) + "-ti put");
			reproduction();
			System.out.println("Reprodukcija gotova " + (i + 1) + "-ti put");
			// isti princip uporabe kao u game world
			GameEngine.getInstance().stop();
			GameEngine.getInstance().reset(); // to je vec resetirano
		}
	}

	private void initialize() {
		Set<Player> players = playerNeuralNetworkMap.keySet();
		for (Player player : players) {
			NeuralNetwork neuralNetwork = mode == PlayingMode.NEURAL_NETWORK
					? new GeneticNeuralNetwork(INPUT_LAYER_SIZE, numberOfHiddenLayers, numberPerHiddenLayer,
							activationFunction)
					: new ElmanNeuralNetwork(INPUT_LAYER_SIZE, numberPerHiddenLayer, activationFunction);
//					System.out.println("Player id u algoritmu " + player.getId());
			playerNeuralNetworkMap.replace(player, neuralNetwork);
		}
	}

	private void selection() throws InterruptedException {
		GameEngine.getInstance().getGameStateListener().AITrainingModePlayingStarted();
		synchronized (lockObj) {
			while (!GameEngine.getInstance().getGameWorld().isUnlockingCondition()) {
//				System.out.println("Čekam!");
				try {
					lockObj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
//		System.out.println("Odblokiram!");
		if (GameEngine.getInstance().getGameWorld().isLevelPassed()) {
			System.out.println(playerNeuralNetworkMap.keySet().size());
//			for (Player player : playerNeuralNetworkMap.keySet()) {
//				if (!player.isDead())
//					System.out.println(playerNeuralNetworkMap.get(player));
//			}
			throw new IllegalStateException("You trained enough");
		}

		for (Player player : playerNeuralNetworkMap.keySet()) {
//			System.out.println(player.getGoodness_value());
			sumOfAllFitnesses += player.getGoodness_value();
		}
	}

	private void reproduction() {

		for (Player player: this.playerNeuralNetworkMap.keySet()) {

			NeuralNetwork parent1 = getRandomParent();
			NeuralNetwork parent2 = getRandomParent();

			while (parent2.equals(parent1))
				parent2 = getRandomParent();

			NeuralNetwork child = crossover(parent1, parent2);
			child = mutation(child);
			this.playerNeuralNetworkMap.replace(player, child);
		}

		GameEngine.getInstance().getGameWorld().getAlgorithm().setPlayerNeuralNetworkMap(this.playerNeuralNetworkMap);
//			linija iznad po meni visak
//		System.out.println(this.playerNeuralNetworkMap.keySet().size()); okej
//		GameEngine.getInstance().getGameWorld().setNewGeneration(this.playerNeuralNetworkMap.keySet());

	}

	private NeuralNetwork getRandomParent() {
		int sum = 0;
		int randomSum = (int) Math.ceil(Math.random() * sumOfAllFitnesses);

//		System.out.println("Suma svih fitnessa je " + sumOfAllFitnesses);

		NeuralNetwork parent = null;

		for (Map.Entry<Player, NeuralNetwork> entry : playerNeuralNetworkMap.entrySet()) {
			if (sum >= randomSum)
				break;
			sum += entry.getKey().getGoodness_value();
			parent = entry.getValue();
		}

		return parent;
	}

	private NeuralNetwork crossover(NeuralNetwork parent1, NeuralNetwork parent2) {
		List<List<Double>> weights1 = parent1.getWeights(), weights2 = parent2.getWeights();
		List<List<Double>> weightsChild = new ArrayList<>();

		for (int i = 0; i < weights1.size(); i++) {
			List<Double> list1 = weights1.get(i), list2 = weights2.get(i);
			List<Double> child1 = new ArrayList<>();

			for (int j = 0; j < list1.size(); j++) {
				child1.add((list1.get(j) + list2.get(j)) / 2);
			}
			weightsChild.add(child1);
		}

		List<Double> biases1 = parent1.getBiases(), biases2 = parent2.getBiases();
		List<Double> biasesChild = new ArrayList<>();

		for (int i = 0; i < biases1.size(); i++) {
			biasesChild.add((biases1.get(i) + biases2.get(i)) / 2);
		}

		NeuralNetwork childNetwork = mode == PlayingMode.NEURAL_NETWORK
				? new GeneticNeuralNetwork(INPUT_LAYER_SIZE, numberOfHiddenLayers, numberPerHiddenLayer,
						activationFunction)
				: new ElmanNeuralNetwork(INPUT_LAYER_SIZE, numberPerHiddenLayer, activationFunction);
		childNetwork.setWeights(weightsChild);
		childNetwork.setBiases(biasesChild);
		return childNetwork;
	}

	private NeuralNetwork mutation(NeuralNetwork child) {
		List<List<Double>> weights = child.getWeights();
		List<List<Double>> newWeights = new ArrayList<>();
		List<Double> biases = child.getBiases();
		List<Double> newBiases = new ArrayList<>();

		for (List<Double> list : weights) {
			for (int j = 0; j < list.size(); j++) {
				if (shouldIMutate())
					list.set(j, Math.random() * 2 - 1);
			}
			newWeights.add(list);
		}

		for (Double bias : biases) {
			if (shouldIMutate())
				bias = Math.random() * 2 - 1;
			newBiases.add(bias);
		}

		child.setWeights(newWeights);
		child.setBiases(newBiases);

		return child;
	}

	private boolean shouldIMutate() {
		return Math.random() <= MUTATION_RATE;
	}

	/**
	 * @return the lockObj
	 */
	public Object getLockObj() {
		return lockObj;
	}

	/**
	 * @param lockObj the lockObj to set
	 */
	public void setLockObj(Object lockObj) {
		this.lockObj = lockObj;
	}
}
