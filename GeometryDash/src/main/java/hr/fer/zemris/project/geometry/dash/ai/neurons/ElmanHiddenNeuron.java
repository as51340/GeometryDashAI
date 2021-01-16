package hr.fer.zemris.project.geometry.dash.ai.neurons;

import java.util.function.DoubleUnaryOperator;

/**
 * Class models neuron from hidden layer of Elman neural network.
 */
public class ElmanHiddenNeuron extends Neuron {

	/**
	 * Context neuron of this hidden neuron
	 */
	private ContextNeuron contextNeuron;

	/**
	 * Weight of context neuron
	 */
	private double contextNeuronWeight;

	public ElmanHiddenNeuron() {
		super();
		contextNeuron = new ContextNeuron();
	}

	public ElmanHiddenNeuron(int id) {
		super(id);
		contextNeuron = new ContextNeuron(id + 1);
	}

	public ElmanHiddenNeuron(double bias) {
		super(bias);
		contextNeuron = new ContextNeuron();
	}

	public ElmanHiddenNeuron(int id, DoubleUnaryOperator activationFunction) {
		super(id, activationFunction);
		contextNeuron = new ContextNeuron(id + 1);
	}

	@Override
	public Double calculateOutput() {
		if (hasOutput())
			return getOutput();

		double sum = getBias();

		int index = 0;
		for (Neuron neuron : prevNeurons) {
			sum += neuron.calculateOutput() * prevNeuronWeights.get(index++);
		}
		sum += contextNeuron.hasOutput() ? contextNeuron.getOutput() * contextNeuronWeight : 0;
		sum = applyActivationFunction(sum);

		contextNeuron.setOutput(sum);
		contextNeuron.setHasOutput(true);
		setOutput(sum);
		setHasOutput(true);

		return getOutput();
	}

	public double getMemoryNeuronWeight() {
		return contextNeuronWeight;
	}

	public void setMemoryNeuronWeight(double memoryNeuronWeight) {
		this.contextNeuronWeight = memoryNeuronWeight;
	}

	public ContextNeuron getMemoryNeuron() {
		return contextNeuron;
	}

}
