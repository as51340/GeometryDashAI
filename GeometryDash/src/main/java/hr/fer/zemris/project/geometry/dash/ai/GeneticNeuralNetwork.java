package hr.fer.zemris.project.geometry.dash.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import hr.fer.zemris.project.geometry.dash.ai.neurons.ElmanOutputNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.Neuron;

/**
 * Models a genetic neural network.
 */
public class GeneticNeuralNetwork extends NeuralNetwork {

    /**
     * Creates a new GeneticNeuralNetwork
     */
    public GeneticNeuralNetwork() {
        super();
    }

    /**
     * Creates a new GeneticNeuralNetwork with the number of input neurons set to given value
     *
     * @param numberOfInputNeurons number of input neurons
     */
    public GeneticNeuralNetwork(int numberOfInputNeurons) {
        super(numberOfInputNeurons);
    }

    /**
     * Sets the activation function of all neurons to given activation function
     *
     * @param activationFunction given activation function
     */
    public GeneticNeuralNetwork(DoubleUnaryOperator activationFunction) {
        super(activationFunction);
        createOutput(new ElmanOutputNeuron(-1));
    }

    /**
     * Creates a new GeneticNeuralNetwork with the given parameters
     *
     * @param numberOfInputNeurons    number of input neurons
     * @param numberOfHiddenLayers    number of hidden layers
     * @param numberOfNeuronsPerLayer number of neurons per hidden layer
     */
    public GeneticNeuralNetwork(int numberOfInputNeurons, int numberOfHiddenLayers, int numberOfNeuronsPerLayer) {
        super(numberOfInputNeurons, numberOfHiddenLayers, numberOfNeuronsPerLayer);
    }

    /**
     * Creates a new NeuralNetwork from given parameters
     *
     * @param numberOfInputNeurons    number of input neurons
     * @param numberOfHiddenLayers    number of hidden layers
     * @param numberOfNeuronsPerLayer number of neurons per hidden layer
     * @param activationFunction      activation function of all neurons
     */
    public GeneticNeuralNetwork(int numberOfInputNeurons, int numberOfHiddenLayers, int numberOfNeuronsPerLayer, DoubleUnaryOperator activationFunction) {
        super(numberOfInputNeurons, numberOfHiddenLayers, numberOfNeuronsPerLayer, activationFunction);
    }

    /**
     * Constructs a NeuralNetwork from existing data
     * @param output output
     * @param inputLayer inputLayer
     * @param hiddenLayers hiddenLayers
     * @param activationFunction activationFunction
     */
    public GeneticNeuralNetwork(Neuron output, List<Neuron> inputLayer, List<List<Neuron>> hiddenLayers, DoubleUnaryOperator activationFunction) {
        super(output, inputLayer, hiddenLayers, activationFunction);
    }

    
    @Override
    public void createHiddenLayers(int numberOfLayers, int numberOfNeuronsPerLayer) {
        for (int i = 0; i < numberOfLayers; i++) {
            ArrayList<Neuron> layer = new ArrayList<>();
            for (int j = 0; j < numberOfNeuronsPerLayer; j++) {
                Neuron neuron = new Neuron(lastId++, activationFunction);

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

    @Override
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
     * Inserts a given layer as the "position"th hidden layer
     *
     * @param neurons  given layer
     * @param position given position
     */
    public void insertHiddenLayer(List<Neuron> neurons, int position) {
        if (position > hiddenLayers.size())
            throw new IllegalArgumentException("given position is greater than size");

        if (position == hiddenLayers.size()) {
            List<Neuron> lastHiddenLayer = hiddenLayers.get(position - 1);

            removeConnectionToPrevLayer(output, lastHiddenLayer);
            createConnectionToPrevLayer(output, neurons);
            neurons.forEach(n -> createConnectionToPrevLayer(n, lastHiddenLayer));

            hiddenLayers.add(position, neurons);

        } else {
            List<Neuron> otherHiddenLayer = hiddenLayers.get(position);
            List<Neuron> previous;
            if (position != 0)
                previous = hiddenLayers.get(position - 1);
            else previous = inputLayer;

            otherHiddenLayer.forEach(n -> removeConnectionToPrevLayer(n, previous));
            neurons.forEach(n -> createConnectionToPrevLayer(n, previous));
            otherHiddenLayer.forEach(n -> createConnectionToPrevLayer(n, neurons));

            hiddenLayers.add(position, neurons);

        }
    }
    
    @Override
    public void setWeights(List<List<Double>> weights) {
        if (weights.size() != inputLayer.size() + 1 + hiddenLayers.size() * hiddenLayers.get(0).size())
            throw new IllegalArgumentException("Number of weights has to equal the sum of input, output and hidden neurons.");

        int index = 0;
        for (Neuron neuron : inputLayer)
            neuron.setPrevNeuronWeights(weights.get(index++));

        for (List<Neuron> hiddenLayer : hiddenLayers)
            for (Neuron neuron : hiddenLayer)
                neuron.setPrevNeuronWeights(weights.get(index++));

        output.setPrevNeuronWeights(weights.get(index));
    }
    
    @Override
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

    public static void main(String[] args) {
        //input 4
        //hidden 2
        //output 1
        GeneticNeuralNetwork neuralNetwork = new GeneticNeuralNetwork(4, 1, 2);

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


//      Inserting a new hiddenLayer
        ArrayList<Neuron> hidden1 = new ArrayList<>();
        int id = 123;
        for (int i = 0; i < 4; i++)
            hidden1.add(new Neuron(id++));

        neuralNetwork.insertHiddenLayer(hidden1, 1);


        System.out.println(neuralNetwork.output.calculateOutput());
    }
}