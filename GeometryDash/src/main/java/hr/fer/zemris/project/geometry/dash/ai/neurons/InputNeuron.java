package hr.fer.zemris.project.geometry.dash.ai.neurons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.DoubleUnaryOperator;

public class InputNeuron extends Neuron {
    double input = Math.random() * 2 - 1;


    public InputNeuron() {
        super();
        setInputWeight();
    }

    public InputNeuron(int id) {
        super(id);
        setInputWeight();
    }

    public InputNeuron(int id, DoubleUnaryOperator activationFunction) {
        super(id, activationFunction);
        setInputWeight();
    }

    public InputNeuron(double bias, double output) {
        super(bias, output);
        setInputWeight();
    }

    @Override
    public Double calculateOutput() {
        //System.out.println(toString());
        if (hasOutput())
            return getOutput();
        //System.out.println(toString() + " entering calculateOutput()");

        setOutput(applyActivationFunction(input));
        //setOutput(applyActivationFunction(getInputWeight() * input));
        setHasOutput(true);

        //System.out.println(this.toString() + ", output: " + getOutput());
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

    public void setInputWeight() {
        setInputWeight(Math.random() * 2 - 1);
    }

    @Override
    public String toString() {
        return "InputNeuron(id: " + getId() + ", input: " + input + ", weight: " + getInputWeight() + ")";
    }

    public static void main(String[] args) {
        //input hidden 2 output 1
        ArrayList<InputNeuron> input = new ArrayList<>();
        ArrayList<Neuron> hidden = new ArrayList<>();
        int id = 1;

        for (int i = 0; i < 4; i++)
            input.add(new InputNeuron(id++));

        for (int i = 0; i < 2; i++)
            hidden.add(new Neuron(id++));

        Neuron output = new Neuron(id);

        for (InputNeuron inputNeuron : input)
            for (Neuron hiddenNeuron : hidden)
                hiddenNeuron.addConnectionFromOtherToThis(inputNeuron);

        for (Neuron hiddenNeuron : hidden)
            output.addConnectionFromOtherToThis(hiddenNeuron);

        System.out.println(output.calculateOutput());
    }
}
