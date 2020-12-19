package hr.fer.zemris.project.geometry.dash.ai;

import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Obstacle;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a simple neural network where each neuron is connected to every neuron of the next layer
 */
public class NeuralNetwork {
    /**
     * Neurons of the input layer
     */
    private List<Neuron> inputLayer;
    /**
     * List of all hidden layers - the number of lists is eqal to the number of hidden layers
     */
    private List<List<Neuron>> hiddenLayers;
    /**
     * Single output neuron
     */
    private Neuron output;

    /**
     * used for assigning ids to neurons
     */
    private int lastId;

    /**
     * Creates a new NeuralNetwork
     */
    public NeuralNetwork() {
        inputLayer = new ArrayList<>();
        hiddenLayers = new ArrayList<>();
        output = new Neuron(-1);
        lastId = 0;
    }

    /**
     * Creates a new NeuralNetwork with the given parameters
     *
     * @param numberOfInputNeurons    number of input neurons
     * @param numberOfHiddenLayers    number of hidden layers
     * @param numberOfNeuronsPerLayer number of neurons per hidden layer
     */
    public NeuralNetwork(int numberOfInputNeurons, int numberOfHiddenLayers, int numberOfNeuronsPerLayer) {
        this(numberOfInputNeurons);
        createHiddenLayers(numberOfHiddenLayers, numberOfNeuronsPerLayer);
    }

    /**
     * Creates a new NeuralNetwork with the number of input neurons set to given value
     *
     * @param numberOfInputNeurons number of input neurons
     */
    public NeuralNetwork(int numberOfInputNeurons) {
        this();
        createInputLayer(numberOfInputNeurons);
    }

    /**
     * Creates an input layer with given number as the number of neurons
     *
     * @param numberOfNeurons number of neurons in input layer
     */
    public void createInputLayer(int numberOfNeurons) {
        for (int i = 0; i < numberOfNeurons; i++)
            inputLayer.add(new Neuron(lastId++));
    }

    /**
     * Creates a new input layer from given neurons and if a hidden layer already exists connects it to this input layer
     *
     * @param neurons given neurons
     */
    public void createInputLayer(List<Neuron> neurons) {
        if (hiddenLayers.size() != 0) {
            for (Neuron neuron : hiddenLayers.get(0))
                removeConnectionToPrevLayer(neuron, inputLayer);
        }

        this.inputLayer = neurons;
        createHiddenLayer(this.hiddenLayers);
    }

    /**
     * Method which creates an input layer which is three times bigger than the given list if there is no input layer
     * and sets biases to obstacle position x, obstacle position y, and obstacle type decoded
     * <p>
     * note: if there already exists an input layer, it will not create a new input layer but it will set the biases.
     * It is assumed that the łinput layer size is three times the number of obstacles
     *
     * @param obstacles given list of obstacles
     */
    public void inputObstacles(List<Obstacle> obstacles) {
        if (inputLayer.size() == 0) createInputLayer(obstacles.size() * 3);
        int index = 0;
        for (Obstacle obstacle : obstacles) {
            inputLayer.get(index++).setBias(obstacle.getCurrentPosition().getX());
            inputLayer.get(index++).setBias(obstacle.getCurrentPosition().getY());
            inputLayer.get(index++).setBias(decodeObstacleType(obstacle));
        }

    }

    /**
     * Creates hidden layers from given parameters and connects it to previous layers
     * note: input layers already have to be defined to be connected to - you can do that with the method inputObstacles
     * or createInputLayer. This method will not throw an exception if there is no input layer!
     *
     * @param numberOfLayers          number of layers
     * @param numberOfNeuronsPerLayer number of neurons per layer
     */
    public void createHiddenLayers(int numberOfLayers, int numberOfNeuronsPerLayer) {
        for (int i = 0; i < numberOfLayers; i++) {
            ArrayList<Neuron> layer = new ArrayList<>();
            for (int j = 0; j < numberOfNeuronsPerLayer; j++) {
                Neuron neuron = new Neuron(lastId++);

                if (hiddenLayers.size() == 0) {
                    createConnectionToPrevLayer(neuron, inputLayer);
                } else
                    createConnectionToPrevLayer(neuron, hiddenLayers.get(hiddenLayers.size() - 1));

                layer.add(neuron);
            }

            hiddenLayers.add(layer);
        }

        createConnectionToPrevLayer(output, hiddenLayers.get(hiddenLayers.size() - 1));
    }

    /**
     * Creates hidden layer from given neurons (connections are also created)
     *
     * @param neurons given neurons
     */
    public void createHiddenLayer(List<List<Neuron>> neurons) {
        if (hiddenLayers.size() != 0) {
            removeConnectionToPrevLayer(output, hiddenLayers.get(hiddenLayers.size() - 1));
        }

        this.hiddenLayers = neurons;

        for (List<Neuron> hiddenLayer : hiddenLayers) {
            for (Neuron neuron : hiddenLayer) {
                int indexOfThis = hiddenLayers.indexOf(hiddenLayer);
                if (indexOfThis == 0) {
                    createConnectionToPrevLayer(neuron, inputLayer);
                } else
                    createConnectionToPrevLayer(neuron, hiddenLayers.get(indexOfThis - 1));
            }
        }
        createConnectionToPrevLayer(output, hiddenLayers.get(hiddenLayers.size() - 1));
    }

    /**
     * Sets given neuron as output neuron and connects previous layer to it
     *
     * @param neuron given neuron
     */
    public void createOutput(Neuron neuron) {
        this.output = neuron;
        createConnectionToPrevLayer(output, hiddenLayers.get(hiddenLayers.size() - 1));
    }

    /**
     * Sets all neuron weights for given weights.
     * note: the order for which the weights are set is: input layer, hidden layers, output layer
     *
     * @param weights given weights
     */
    public void setWeights(List<List<Double>> weights) {
        if (weights.size() != inputLayer.size() + 1 + hiddenLayers.size() * hiddenLayers.get(0).size())
            throw new IllegalArgumentException("");

        int index = 0;
        for (Neuron neuron : inputLayer)
            neuron.setPrevNeuronWeights(weights.get(index++));

        for (List<Neuron> hiddenLayer : hiddenLayers)
            for (Neuron neuron : hiddenLayer)
                neuron.setPrevNeuronWeights(weights.get(index++));

        output.setPrevNeuronWeights(weights.get(index));
    }

    /**
     * Private helper function which connects given neuron to the previous layer
     *
     * @param neuron    given neuron
     * @param prevLayer previous layer
     */
    private void createConnectionToPrevLayer(Neuron neuron, List<Neuron> prevLayer) {
        for (Neuron input : prevLayer)
            neuron.addConnectionFromOtherToThis(input);
    }

    /**
     * Private helper function which removes all connections from given neuron to the previous layer
     *
     * @param neuron    given neuron
     * @param prevLayer previous layer
     */
    private void removeConnectionToPrevLayer(Neuron neuron, List<Neuron> prevLayer) {
        for (Neuron input : prevLayer)
            neuron.removeConnectionFromOtherToThis(input);
    }

    //TODO should probably change how obstacles are decoded

    /**
     * Decodes obstacle type to a Double
     *
     * @param obstacle given obstacle
     * @return Double representation of the obstacle
     */
    public double decodeObstacleType(Obstacle obstacle) {
        return switch (obstacle.getName()) {
            case "Block" -> 0;
            case "Floor" -> 1;
            case "GrassSpike" -> 2;
            case "LeftSpike" -> 3;
            case "Platform" -> 4;
            case "RightSpike" -> 5;
            case "Spike" -> 6;
            default -> throw new IllegalArgumentException("Not a valid obstacle");
        };
    }

    public List<List<Double>> getWeights() {
        ArrayList<List<Double>> retList = new ArrayList<>();

        for (Neuron input : inputLayer)
            retList.add(input.getPrevNeuronWeights());

        for (List<Neuron> hiddenLayer : hiddenLayers)
            for (Neuron hidden : hiddenLayer)
                retList.add(hidden.getPrevNeuronWeights());

        retList.add(output.getPrevNeuronWeights());
        return retList;
    }

    public List<Neuron> getInputLayer() {
        return inputLayer;
    }

    public List<List<Neuron>> getHiddenLayers() {
        return hiddenLayers;
    }

    public Neuron getOutput() {
        return output;
    }

    public static void main(String[] args) {
        //input 4
        //hidden 2
        //output 1
        NeuralNetwork neuralNetwork = new NeuralNetwork(4, 1, 2);

        /*
//      Setting the new inputLayer
        ArrayList<Neuron> input = new ArrayList<>();
        int id = 123;
        for (int i = 0; i < 4; i++)
            input.add(new Neuron(id++));
        neuralNetwork.createInputLayer(input);
        */

        /*
//      Setting the new hiddenLayer
        ArrayList<List<Neuron>> hidden = new ArrayList<>();
        ArrayList<Neuron> hidden1 = new ArrayList<>();
        int id = 123;
        for (int i = 0; i < 4; i++)
            hidden1.add(new Neuron(id++));
        hidden.add(hidden1);

        ArrayList<Neuron> hidden2 = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            hidden2.add(new Neuron(id++));
        hidden.add(hidden2);

        neuralNetwork.createHiddenLayer(hidden);
        */

        System.out.println(neuralNetwork.output.calculateOutput());
    }
}
