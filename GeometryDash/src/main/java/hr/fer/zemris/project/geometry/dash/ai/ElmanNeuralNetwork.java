package hr.fer.zemris.project.geometry.dash.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import hr.fer.zemris.project.geometry.dash.ai.neurons.ElmanHiddenNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.Neuron;

public class ElmanNeuralNetwork extends NeuralNetwork {
	
	public ElmanNeuralNetwork() {
		super();
	}
	
	public ElmanNeuralNetwork(DoubleUnaryOperator activationFunction) {
		super(activationFunction);
	}
	
	public ElmanNeuralNetwork(int numberOfInputNeurons) {
		super(numberOfInputNeurons);
	}
	
	public ElmanNeuralNetwork(int numberOfInputNeurons, int numberOfNeuronsPerLayer) {
		this(numberOfInputNeurons, numberOfNeuronsPerLayer, AIConstants.activationFunction);
	}
	
	public ElmanNeuralNetwork(int numberOfInputNeurons, int numberOfNeuronsPerLayer, DoubleUnaryOperator activationFunction) {
		super(numberOfInputNeurons, 1, numberOfNeuronsPerLayer, activationFunction);
	}

	@Override
	public void createHiddenLayers(int numberOfLayers, int numberOfNeuronsPerLayer) {
		if(numberOfLayers != 1)
			throw new IllegalArgumentException("Number of layers has to be 1");
		
		ArrayList<Neuron> layer = new ArrayList<>();
		for(int i = 0; i < numberOfNeuronsPerLayer; i++) {
			ElmanHiddenNeuron neuron = new ElmanHiddenNeuron(lastId, activationFunction);
			
			createConnectionToPrevLayer(neuron, inputLayer);
			layer.add(neuron);
			lastId += 2;
		}
		hiddenLayers = Collections.singletonList(layer);
		
		createConnectionToPrevLayer(output, hiddenLayers.get(0));
	}
	
	@Override
	public void createHiddenLayer(List<List<Neuron>> neurons) {
		if(neurons.size() != 1)
			throw new IllegalArgumentException("Number of layers has to be 1");
		
        if (hiddenLayers.size() == 1)
            removeConnectionToPrevLayer(output, hiddenLayers.get(0));

        hiddenLayers = Collections.singletonList(neurons.get(0));

        for (Neuron neuron : hiddenLayers.get(0))
        	createConnectionToPrevLayer(neuron, inputLayer);
        createConnectionToPrevLayer(output, hiddenLayers.get(0));
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Weights for hidden layer have to be given as W(hidden1), W(context1), ..., W(hiddenN), W(contextN)
	 */
	@Override
	public void setWeights(List<List<Double>> weights) {
        if (weights.size() != inputLayer.size() + 1 + hiddenLayers.get(0).size() * 2)
            throw new IllegalArgumentException("Number of weights has to equal the sum of input, output, hidden and context neurons.");

        int index = 0;
        for (Neuron neuron : inputLayer)
            neuron.setPrevNeuronWeights(weights.get(index++));

        for (Neuron neuron : hiddenLayers.get(0)) {
        	ElmanHiddenNeuron hiddenNeuron = (ElmanHiddenNeuron) neuron;
        	hiddenNeuron.setPrevNeuronWeights(weights.get(index++));
        	hiddenNeuron.setContextNeuronWeight(weights.get(index++).get(0));
        }

        output.setPrevNeuronWeights(weights.get(index));
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Weights for hidden layer are returned as W(hidden1), W(context1), ..., W(hiddenN), W(contextN)
	 */
	@Override
	public List<List<Double>> getWeights() {
        ArrayList<List<Double>> retList = new ArrayList<>();

        for (Neuron input : inputLayer)
            retList.add(input.getPrevNeuronWeights());

        for (Neuron neuron : hiddenLayers.get(0)) {
        	ElmanHiddenNeuron hiddenNeuron = (ElmanHiddenNeuron) neuron;
        	retList.add(hiddenNeuron.getPrevNeuronWeights());
        	retList.add(Collections.singletonList(hiddenNeuron.getContextNeuronWeight()));
        }       

        retList.add(output.getPrevNeuronWeights());
        return retList;
	}
	
	public List<Neuron> getHiddenLayer() {
		List<List<Neuron>> layers = getHiddenLayers();
		return layers != null ? layers.get(0) : null;
	}

}
