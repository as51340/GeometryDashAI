package hr.fer.zemris.project.geometry.dash.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.DoubleUnaryOperator;

public class InputNeuron extends Neuron {
    double input = Math.random() * 2 - 1;


    public InputNeuron() {
        super();
    }

    public InputNeuron(int id) {
        super(id);
    }

    public InputNeuron(int id, DoubleUnaryOperator activationFunction) {
        super(id, activationFunction);
    }

    @Override
    public Double calculateOutput() {
        if (hasOutput())
            return getOutput();

        setOutput(getInputWeight() * input);
        setHasOutput(true);

        return getOutput();
    }

    public void setInput(double input) {
        this.input = input;
        setHasOutput(false);
    }

    public Double getInputWeight() {
        return getPrevNeuronWeights().get(0);
    }

    public void setInputWeight(Double inputWeight) {
        setPrevNeuronWeights(new ArrayList<>(Collections.singleton(inputWeight)));
        setHasOutput(false);
    }
}
