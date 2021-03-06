package hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.function.DoubleUnaryOperator;

import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.AIGameSceneListenerImpl;
import hr.fer.zemris.project.geometry.dash.ai.ElmanNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.GeneticNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.listeners.AIGameSceneListener;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.visualization.GameSceneController;
import javafx.application.Platform;

public class AIAlgorithm {

	private static final int POPULATION_SIZE = 20;
	private static final int REPEAT = 500;
	private static final double MUTATION_RATE = 0.1;
	private static final int INPUT_LAYER_SIZE = AIConstants.obstForAI * 3 + 1;
	private final int numberOfHiddenLayers;
	private final int numberPerHiddenLayer;
	private DoubleUnaryOperator activationFunction = AIConstants.activationFunction;

	private Map<Player, NeuralNetwork> playerNeuralNetworkMap;
	private int sumOfAllFitnesses;

	private Object lockObj;

	private Map.Entry<Player, NeuralNetwork> bestInGeneration;
	private Map.Entry<Player, NeuralNetwork> bestSoFar;

	/**
	 * Locking on continue object
	 */
	private Object continueLockingObject;

	private GameSceneController controller;

	/**
	 * Game scene controller
	 */
	private AIGameSceneListener gameSceneListener;

	/**
	 * @return the controller
	 */
	public GameSceneController getController() {
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(GameSceneController controller) {
		this.controller = controller;
	}


	/**
	 * Is pause pressed
	 */
	private boolean pausePressed = false;

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
		gameSceneListener = new AIGameSceneListenerImpl();
	}
	
	/**
	 * @return the pausePressed
	 */
	public boolean isPausePressed() {
		return pausePressed;
	}

	/**
	 * @param pausePressed the pausePressed to set
	 */
	public void setPausePressed(boolean pausePressed) {
		this.pausePressed = pausePressed;
	}

	/**
	 * @return the continueLockingObject
	 */
	public Object getContinueLockingObject() {
		return continueLockingObject;
	}

	/**
	 * @param continueLockingObject the continueLockingObject to set
	 */
	public void setContinueLockingObject(Object continueLockingObject) {
		this.continueLockingObject = continueLockingObject;
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
		for (int i = 0; i < REPEAT; i++) {
			Platform.runLater(() -> {
				controller.updateLabel();
			});
			sumOfAllFitnesses = 0;
			GameEngine.getInstance().getGameStateListener().AITrainingModePlayingStarted();
			GameEngine.getInstance().getGameWorld().setUnlockingCondition(false);
			GameEngine.getInstance().getGameWorld().setLevelPassed(false);

			updateBestSoFar();

			selection();
			reproduction();
			GameEngine.getInstance().getGameWorld().createAIScene(); // na kraju svake generacije


		}
	}

	public void initialize() {
		for (int i = 0; i < POPULATION_SIZE; i++) {
			Player player = new Player(new Vector2D(i, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
					new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), PlayingMode.NEURAL_NETWORK);
			NeuralNetwork neuralNetwork = mode == PlayingMode.NEURAL_NETWORK
					? new GeneticNeuralNetwork(INPUT_LAYER_SIZE, numberOfHiddenLayers, numberPerHiddenLayer,
							activationFunction)
					: new ElmanNeuralNetwork(INPUT_LAYER_SIZE, numberPerHiddenLayer, activationFunction);
			playerNeuralNetworkMap.put(player, neuralNetwork);

		}

		Iterator<Map.Entry<Player, NeuralNetwork>> i = playerNeuralNetworkMap.entrySet().iterator();
		bestSoFar = i.next();
	}

	private void selection() throws InterruptedException {
		synchronized (lockObj) {
			while (!GameEngine.getInstance().getGameWorld().isUnlockingCondition()) {
				try {
					lockObj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (GameEngine.getInstance().getGameWorld().isLevelPassed()) {

			// probably on javafx application thread
			Platform.runLater(() -> {
				controller.interruptTraining(
						mode,
						bestSoFar.getValue(),
						GameEngine.getInstance().getGameWorld().isLevelPassed()); // handlaj
																					// fail
			});
			if (continueLockingObject != null) {
				synchronized (continueLockingObject) { // only one thread so we shouldn't need while loop
					try {
						continueLockingObject.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				Thread.currentThread().stop();
			}
		}
		if (pausePressed) {
			synchronized (continueLockingObject) { // only one thread so we shouldn't need while loop
				try {
					continueLockingObject.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		for (Player player : playerNeuralNetworkMap.keySet()) {
			sumOfAllFitnesses += player.getGoodness_value();
		}
	}

	private void reproduction() {
		Map<Player, NeuralNetwork> newGeneration = new HashMap<>();

		for (Player player : this.playerNeuralNetworkMap.keySet()) {

			NeuralNetwork parent1 = getRandomParent();
			NeuralNetwork parent2 = getRandomParent();

			while (parent2.equals(parent1))
				parent2 = getRandomParent();

			NeuralNetwork child = crossover(parent1, parent2);
			mutation(child);
			newGeneration.put(player, child);
		}

		this.playerNeuralNetworkMap = newGeneration;
		GameEngine.getInstance().getGameWorld().setAlgorithm(this);

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

	/**
	 * @return best in current generation
	 */
	public Map.Entry<Player, NeuralNetwork> getBestInGeneration() {
		return bestInGeneration;
	}

	/**
	 * @return best so far
	 */
	public Map.Entry<Player, NeuralNetwork> getBestSoFar() {
		return bestSoFar;
	}

	/**
	 * Sets best in current generation
	 * @param bestInGeneration
	 */
	public void setBestInGeneration(Map.Entry<Player, NeuralNetwork> bestInGeneration) {
		this.bestInGeneration = bestInGeneration;
	}

	/**
	 * Sets best so far
	 * @param bestSoFar
	 */
	public void setBestSoFar(Map.Entry<Player, NeuralNetwork> bestSoFar) {
		this.bestSoFar = bestSoFar;
	}

	private void updateBestSoFar() {
		double genGoodnessValue = -1;
		for(Map.Entry<Player, NeuralNetwork> me: playerNeuralNetworkMap.entrySet()) {
			if(me.getKey().getGoodness_value() > genGoodnessValue)
				bestInGeneration = me;
		}

		if(bestSoFar == null)
			bestSoFar = bestInGeneration;
		else
			bestSoFar = bestSoFar.getKey().getGoodness_value() < bestInGeneration.getKey().getGoodness_value() ?
					bestInGeneration :
					bestSoFar;

	}

}
