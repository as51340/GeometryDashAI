package hr.fer.zemris.project.geometry.dash.ai.neurons;

import java.util.Collections;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Class models neuron from context layer of Elman neural network.
 */
public class ContextNeuron extends Neuron {

	public ContextNeuron() {
		super();
	}
	
	public ContextNeuron(int id) {
		super(id);
	}
	
	@Override
	public void setPrevNeuronWeights(List<Double> prevNeuronWeights) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setPrevWeightsAndBias(List<Double> prevNeuronWeights, Double bias) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void addConnectionFromOtherToThis(Neuron other) {
		if(prevNeurons.size() == 1)
			throw new IllegalArgumentException("Neuron already has previous neuron");
		
		setHasOutput(false);
		prevNeurons = Collections.singletonList(other);
		prevNeuronWeights = Collections.singletonList(1.0);
	}
	
	@Override
	public void addConnectionFromOtherToThis(Neuron other, double weight) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void removeConnectionFromOtherToThis(Neuron other) {
		setHasOutput(false);
		prevNeurons.clear();
		prevNeuronWeights.clear();
	}
	
	@Override
	public Double calculateOutput() {
        throw new UnsupportedOperationException();
	}
}
