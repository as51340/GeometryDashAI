package hr.fer.zemris.project.geometry.dash.ai.neurons;

import java.util.function.DoubleUnaryOperator;

public class ElmanOutputNeuron extends Neuron {

	public ElmanOutputNeuron() {
		super();
	}

	public ElmanOutputNeuron(double bias) {
		super(bias);
	}

	public ElmanOutputNeuron(int id, DoubleUnaryOperator activationFunction) {
		super(id, activationFunction);
	}

	public ElmanOutputNeuron(int id) {
		super(id);
	}
	
	@Override
	public Double calculateOutput() {
        //System.out.println(this.toString() + " entering calculateOutput()");
        double sum = getBias();

        int index = 0;
        for (Neuron neuron : prevNeurons) {
            sum += neuron.calculateOutput() * prevNeuronWeights.get(index++);
        }
        sum = applyActivationFunction(sum);

        setOutput(sum);
        //System.out.println(this.toString() + ", output: " + getOutput());
        return sum;
	}

	
}
