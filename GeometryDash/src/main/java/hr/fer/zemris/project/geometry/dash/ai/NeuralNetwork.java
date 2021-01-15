package hr.fer.zemris.project.geometry.dash.ai;

import hr.fer.zemris.project.geometry.dash.ai.neurons.InputNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.Neuron;
import hr.fer.zemris.project.geometry.dash.model.drawables.environment.Obstacle;
import hr.fer.zemris.project.geometry.dash.model.drawables.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

import com.google.gson.annotations.Expose;

/**
 * Models a simple neural network where each neuron is connected to every neuron of the next layer
 */
public abstract class NeuralNetwork {
    /**
     * Neurons of the input layer
     */
	@Expose
    protected List<Neuron> inputLayer;
    /**
     * List of all hidden layers - the number of lists is equal to the number of hidden layers
     */
	@Expose
    protected List<List<Neuron>> hiddenLayers;
    /**
     * Single output neuron
     */
	@Expose
    protected Neuron output;
    /**
     * used for assigning ids to neurons
     */
    protected int lastId;
    /**
     * Activation function of all neurons
     */
    @Expose
    protected DoubleUnaryOperator activationFunction = AIConstants.activationFunction;

    /**
     * Creates a new NeuralNetwork
     */
    public NeuralNetwork() {
        inputLayer = new ArrayList<>();
        hiddenLayers = new ArrayList<>();
        output = new Neuron(-1, activationFunction);
        lastId = 0;
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
     * Sets the activation function of all neurons to given activation function
     *
     * @param activationFunction given activation function
     */
    public NeuralNetwork(DoubleUnaryOperator activationFunction) {
        this();
        this.activationFunction = activationFunction;
        output = new Neuron(-1, activationFunction);
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
     * Creates a new NeuralNetwork from given parameters
     *
     * @param numberOfInputNeurons    number of input neurons
     * @param numberOfHiddenLayers    number of hidden layers
     * @param numberOfNeuronsPerLayer number of neurons per hidden layer
     * @param activationFunction      activation function of all neurons
     */
    public NeuralNetwork(int numberOfInputNeurons, int numberOfHiddenLayers, int numberOfNeuronsPerLayer, DoubleUnaryOperator activationFunction) {
        this(activationFunction);
        createInputLayer(numberOfInputNeurons);
        createHiddenLayers(numberOfHiddenLayers, numberOfNeuronsPerLayer);
    }

    /**
     * Creates an input layer with given number as the number of neurons
     *
     * @param numberOfNeurons number of neurons in input layer
     */
    public void createInputLayer(int numberOfNeurons) {
        for (int i = 0; i < numberOfNeurons; i++) {
            InputNeuron neuron = new InputNeuron(lastId++, activationFunction);
            inputLayer.add(neuron);
        }
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


    //TODO check if biases are set right

    /**
     * Method which creates an input layer which is three times bigger than the given list if there is no input layer
     * and sets biases to obstacle position x, obstacle position y, and obstacle type decoded
     * <p>
     * note: if there already exists an input layer, it will not create a new input layer but it will set the biases.
     * It is assumed that the input layer size is three times the number of obstacles
     *
     * @param obstacles given list of obstacles
     */
    public void inputObstacles(List<Obstacle> obstacles, Player player) {
        if (inputLayer.size() == 0) createInputLayer(obstacles.size() * 3 + 1);

        int index = 0, size = obstacles.size() * 3;
        ((InputNeuron) inputLayer.get(index++)).setInput(player.getCurrentPosition().getY());

        for (Obstacle obstacle : obstacles) {
            ((InputNeuron) inputLayer.get(index++)).setInput(obstacle.getCurrentPosition().getX() - player.getCurrentPosition().getX());
            ((InputNeuron) inputLayer.get(index++)).setInput(obstacle.getCurrentPosition().getY() - player.getCurrentPosition().getY());
            ((InputNeuron) inputLayer.get(index++)).setInput(Obstacle.decodeObstacleType(obstacle));
        }

        while(index < size){
            ((InputNeuron) inputLayer.get(index++)).setInput(0);
            ((InputNeuron) inputLayer.get(index++)).setInput(0);
            ((InputNeuron) inputLayer.get(index++)).setInput(0);
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
    public abstract void createHiddenLayers(int numberOfLayers, int numberOfNeuronsPerLayer);
    
    /**
     * Creates hidden layer from given neurons (connections are also created)
     *
     * @param neurons given neurons
     */
    public abstract void createHiddenLayer(List<List<Neuron>> neurons);

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
     * Sets all neuron biases to given biases.
     * note: the order for which the biases are set is: input layer, hidden layers, output layer
     *
     * @param biases  given biases
     */
    public void setBiases(List<Double> biases) {
        if (biases.size() != inputLayer.size() + 1 + hiddenLayers.size() * hiddenLayers.get(0).size())
            throw new IllegalArgumentException("Number of biases has to equal the sum of input, output and hidden neurons.");

        int index = 0;
        for (Neuron neuron : inputLayer)
            neuron.setBias(biases.get(index));

        for (List<Neuron> hiddenLayer : hiddenLayers)
            for (Neuron neuron : hiddenLayer)
                neuron.setBias(biases.get(index));

        output.setBias(biases.get(index));
    }

    /**
     * Private helper function which connects given neuron to the previous layer
     *
     * @param neuron    given neuron
     * @param prevLayer previous layer
     */
    protected void createConnectionToPrevLayer(Neuron neuron, List<Neuron> prevLayer) {
        for (Neuron input : prevLayer)
            neuron.addConnectionFromOtherToThis(input);
    }

    /**
     * Private helper function which removes all connections from given neuron to the previous layer
     *
     * @param neuron    given neuron
     * @param prevLayer previous layer
     */
    protected void removeConnectionToPrevLayer(Neuron neuron, List<Neuron> prevLayer) {
        for (Neuron input : prevLayer)
            neuron.removeConnectionFromOtherToThis(input);
    }
    
    /**
     * Sets all neuron weights for given weights.
     * note: the order for which the weights are set is: input layer, hidden layers, output layer
     *
     * @param weights given weights
     */
    public abstract void setWeights(List<List<Double>> weights);
    
    /**
     * Gets all neuron weights.
     * note: the order for which the weights are returned is: input layer, hidden layers, output layer
     *
     * @return weights
     */
    public abstract List<List<Double>> getWeights();
    
    public List<Double> getBiases() {
        ArrayList<Double> biases = new ArrayList<>();

        for (Neuron neuron : inputLayer)
            biases.add(neuron.getBias());

        for (List<Neuron> hiddenLayer : hiddenLayers) {
            for (Neuron neuron : hiddenLayer) {
                biases.add(neuron.getBias());
            }
        }

        biases.add(output.getBias());
        return biases;
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

    public int numberOfHiddenLayers() {
        return hiddenLayers.size();
    }

    public int numberOfNeuronsPerHiddenLayer() {
        return hiddenLayers.get(0).size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeuralNetwork that = (NeuralNetwork) o;
        return Objects.equals(inputLayer, that.inputLayer) &&
                Objects.equals(hiddenLayers, that.hiddenLayers) &&
                Objects.equals(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputLayer, hiddenLayers, output);
    }

}
