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
		connectContextNeuron(null);
	}

	public ElmanHiddenNeuron(int id) {
		super(id);
		connectContextNeuron(id+1);
	}

	public ElmanHiddenNeuron(double bias) {
		super(bias);
		connectContextNeuron(null);
	}

	public ElmanHiddenNeuron(int id, DoubleUnaryOperator activationFunction) {
		super(id, activationFunction);
		connectContextNeuron(id+1);
	}

	public ElmanHiddenNeuron(double bias, double output, int id, ContextNeuron contextNeuron, double contextNeuronWeight) {
		super(bias, output, id);
		this.contextNeuron = contextNeuron;
		this.contextNeuronWeight = contextNeuronWeight;
	}
	
	private void connectContextNeuron(Integer id) {
		contextNeuron = id == null ? new ContextNeuron() : new ContextNeuron(id);
		contextNeuron.addConnectionFromOtherToThis(this);
		contextNeuronWeight = Math.random() * 2 - 1;
	}

	@Override
	public Double calculateOutput() {
		//System.out.println(toString() + " entered calculateOutput()");

		double sum = getBias();

		int index = 0;
		for (Neuron neuron : prevNeurons) {
			sum += neuron.calculateOutput() * prevNeuronWeights.get(index++);
		}
		
		//System.out.println(contextNeuron.toString());
		sum += contextNeuron.hasOutput() ? contextNeuron.getOutput() * contextNeuronWeight : 0;
		sum = applyActivationFunction(sum);

		contextNeuron.setOutput(sum);
		contextNeuron.setHasOutput(true);
		setOutput(sum);

		//System.out.println(toString() + ", output: " + getOutput());
		
		return getOutput();
	}

	public double getContextNeuronWeight() {
		return contextNeuronWeight;
	}

	public void setContextNeuronWeight(double contextNeuronWeight) {
		this.contextNeuronWeight = contextNeuronWeight;
	}

	public ContextNeuron getContextNeuron() {
		return contextNeuron;
	}
	
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ElmanHiddenNeuron(id: ").append(getId()).append(", bias: ").append(getBias()).append(", neuronID-weight: ");

        for (int i = 0; i < prevNeurons.size(); i++)
            sb.append("{id: ").append(prevNeurons.get(i).getId()).append(", weight: ").append(prevNeuronWeights.get(i)).append("}");
        
        sb.append(", contextNeuron: ").append(contextNeuron);

        sb.append(")");
        return sb.toString();
    }


}
