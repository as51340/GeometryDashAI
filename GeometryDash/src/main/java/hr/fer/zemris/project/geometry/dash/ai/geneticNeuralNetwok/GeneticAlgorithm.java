package hr.fer.zemris.project.geometry.dash.ai.geneticNeuralNetwok;

import hr.fer.zemris.project.geometry.dash.ai.GeneticNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.model.GameEngine;
import hr.fer.zemris.project.geometry.dash.model.PlayingMode;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import hr.fer.zemris.project.geometry.dash.model.math.Vector2D;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;

import java.util.*;
import java.util.function.DoubleUnaryOperator;

public class GeneticAlgorithm {
    public static final int POPULATION_SIZE = 300;
    public static final int REPEAT = 500;
    public static final double MUTATION_RATE = 0.1;
    public static final int INPUT_LAYER_SIZE = 13;
    public final int numberOfHiddenLayers;
    public final int numberPerHiddenLayer;
    public DoubleUnaryOperator activationFunction = (v -> 1 / (1 + Math.exp(-v)));

    public Map<Player, NeuralNetwork> playerNeuralNetworkMap;
    public int sumOfAllFinesses;

    public GeneticAlgorithm(int numberOfHiddenLayers, int numberPerHiddenLayer) {
        playerNeuralNetworkMap = new LinkedHashMap<>();
        this.numberOfHiddenLayers = numberOfHiddenLayers;
        this.numberPerHiddenLayer = numberPerHiddenLayer;

        runAlgorithm();
    }

    public GeneticAlgorithm(int numberOfHiddenLayers, int numberPerHiddenLayer, DoubleUnaryOperator activationFunction) {
        playerNeuralNetworkMap = new LinkedHashMap<>();
        this.numberOfHiddenLayers = numberOfHiddenLayers;
        this.numberPerHiddenLayer = numberPerHiddenLayer;
        this.activationFunction = activationFunction;
        runAlgorithm();
    }

    public void runAlgorithm() {
        initialize();
        for (int i = 0; i < REPEAT; i++) {
            sumOfAllFinesses = 0;
            selection();
            reproduction();
        }
    }

    public void initialize() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Player player = new Player(new Vector2D(i * 20, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
                    new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), PlayingMode.NEURAL_NETWORK);

            GeneticNeuralNetwork neuralNetwork = new GeneticNeuralNetwork(INPUT_LAYER_SIZE, numberOfHiddenLayers, numberPerHiddenLayer, activationFunction);
            playerNeuralNetworkMap.put(player, neuralNetwork);
        }
    }

    public void selection() {
        //nekako u gameworld staviti playere, cekati da zavrse igrati da im se napravi fitness
        GameEngine.getInstance().getGameWorld().setPlayers(playerNeuralNetworkMap.keySet());
        //idk kako ovo radi ali cekamo valjda da zavrse igrat??
        //tu bi trebali i ako smo zadovoljni s fitnesom stat? myb idk
        Thread thread = new Thread(() -> {
        	//wait i notify
        });
        //JAVAFX application thread
        
        for (Player player : playerNeuralNetworkMap.keySet())
            sumOfAllFinesses += player.getGoodness_value();

    }

    public void reproduction() {
        Map<Player, NeuralNetwork> playerNeuralNetworkMap = new HashMap<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            Player player = new Player(new Vector2D(i * 20, GameConstants.floorPosition_Y - GameConstants.iconHeight - 5),
                    new Vector2D(GameConstants.playerSpeed_X, GameConstants.playerSpeed_Y), PlayingMode.NEURAL_NETWORK);

            NeuralNetwork parent1 = getRandomParent();
            NeuralNetwork parent2 = getRandomParent();

            while (parent2.equals(parent1))
                parent2 = getRandomParent();

            NeuralNetwork child = crossover(parent1, parent2);
            child = mutation(child);
            playerNeuralNetworkMap.put(player, child);
        }

        this.playerNeuralNetworkMap = playerNeuralNetworkMap;
    }

    public NeuralNetwork getRandomParent() {
        int sum = 0;
        int randomSum = (int) Math.ceil(Math.random() * sumOfAllFinesses);

        NeuralNetwork parent = null;

        for (Map.Entry<Player, NeuralNetwork> entry : playerNeuralNetworkMap.entrySet()) {
            if (sum >= randomSum)
                break;
            sum += entry.getKey().getGoodness_value();
            parent = entry.getValue();
        }

        return parent;
    }

    public NeuralNetwork crossover(NeuralNetwork parent1, NeuralNetwork parent2) {
        List<List<Double>> weights1 = parent1.getWeights(), weights2 = parent2.getWeights(), weightsChild;
        weightsChild = new ArrayList<>();

        for (int i = 0; i < weights1.size(); i++) {
            List<Double> list1 = weights1.get(i), list2 = weights2.get(i);
            List<Double> child1 = new ArrayList<>();

            for (int j = 0; j < list1.size(); j++) {
                child1.add((list1.get(j) + list2.get(j)) / 2);
            }
            weightsChild.add(child1);
        }

        List<Double> biases1 = parent1.getBiases(), biases2 = parent2.getBiases(), biasesChild;
        biasesChild = new ArrayList<>();

        for (int i = 0; i < biases1.size(); i++) {
            biasesChild.add((biases1.get(i) + biases2.get(i)) / 2);
        }

        NeuralNetwork childNetwork = new GeneticNeuralNetwork(INPUT_LAYER_SIZE, numberOfHiddenLayers, numberPerHiddenLayer, activationFunction);
        childNetwork.setWeights(weightsChild);
        childNetwork.setBiases(biasesChild);
        return childNetwork;
    }

    public NeuralNetwork mutation(NeuralNetwork child) {
        List<List<Double>> weights = child.getWeights();
        List<List<Double>> newWeights = child.getWeights();
        List<Double> biases = child.getBiases();
        List<Double> newBiases = child.getBiases();

        for (List<Double> list : weights) {
            List<Integer> indexesToRemove = new ArrayList<>();

            for (int j = 0; j < list.size(); j++) {
                boolean doMutation = shouldIMutate();
                if (doMutation) {
                    indexesToRemove.add(j);
                }
            }

            if (indexesToRemove.size() != 0) {
                for (int index : indexesToRemove) {
                    list.remove(index);
                    list.add(index, Math.random() * 2 - 1);
                }
            }

            newWeights.add(list);
        }

        for (Double bias : biases) {
            boolean doMutation = shouldIMutate();
            if (doMutation) {
                bias = Math.random() * 2 - 1;
            }
            newBiases.add(bias);
        }

        if (!newBiases.equals(biases) || !newWeights.equals(weights)) {
        	child.setWeights(newWeights);
        	child.setBiases(newBiases);
        }

        return child;
    }

    public boolean shouldIMutate() {
        return Math.random() <= MUTATION_RATE;
    }
}
