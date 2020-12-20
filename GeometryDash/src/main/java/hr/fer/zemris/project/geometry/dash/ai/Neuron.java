package hr.fer.zemris.project.geometry.dash.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Class which models a simple neuron for neural networks
 */
public class Neuron {
    private int id;
    /**
     * List of previous neurons
     */
    private final List<Neuron> prevNeurons;
    /**
     * List of weights for previous neurons - prevNeurons.get(0) has a weight of prevNeuronWeights.get(0)
     */
    private List<Double> prevNeuronWeights;
    /**
     * Activation function of the neuron - by default is sigmoid function
     */
    private DoubleUnaryOperator activationFunction;
    /**
     * Output of this neuron
     */
    private double output;
    /**
     * For optimisation - true if current output is valid
     */
    private boolean hasOutput;
    /**
     * Bias of this neuron
     */
    private double bias;

    /**
     * Creates a new Neuron with default activation function (sigmoid) and bias set as random number from [-1, 1]
     */
    public Neuron() {
        prevNeurons = new ArrayList<>();
        prevNeuronWeights = new ArrayList<>();
        this.activationFunction = (v -> 1 / (1 + Math.exp(-v)));
        this.bias = Math.random() * 2 - 1;
        this.output = 0;
        this.hasOutput = false;
        this.id = 0;
    }

    /**
     * Creates a new default Neuron with id set to given id
     * For testing purposes
     *
     * @param id given id
     */
    public Neuron(int id) {
        this();
        this.id = id;
    }

    /**
     * Creates a new Neuron with the given id and activation function
     *
     * @param id                 given id
     * @param activationFunction given activation function
     */
    public Neuron(int id, DoubleUnaryOperator activationFunction) {
        this(id);
        this.activationFunction = activationFunction;
    }

    /**
     * Creates a new Neuron with bias set to given bias
     *
     * @param bias given bias
     */
    public Neuron(double bias) {
        this();
        this.bias = bias;
    }

    /**
     * Calculates the output of this Neuron by calling this function of previous neurons
     * For each Neuron, calculates output and multiplies it by that Neurons bias, adds it's own bias and applies the
     * activation function
     *
     * @return result
     */
    public Double calculateOutput() {
        if (hasOutput)
            return output;
        System.out.println(this.toString());
        double sum = getBias();

        int index = 0;
        for (Neuron neuron : prevNeurons) {
            sum += neuron.calculateOutput() * prevNeuronWeights.get(index++);
        }
        sum = applyActivationFunction(sum);

        this.output = sum;
        this.hasOutput = true;
        System.out.println(this.toString() + " output: " + output);
        return sum;
    }

    /**
     * Private helper function for applying activation function to given Double
     *
     * @param sum given double
     * @return result
     */
    private Double applyActivationFunction(Double sum) {
        return activationFunction.applyAsDouble(sum);
    }

    /**
     * Creates a connection from given Neuron to this Neuron and sets its weight to a random Double from interval [-1,1]
     *
     * @param other other Neuron
     */
    public void addConnectionFromOtherToThis(Neuron other) {
        addConnectionFromOtherToThis(other, Math.random() * 2 - 1);
    }

    /**
     * Creates a connection from given Neuron to this Neuron and sets its weight given weight
     *
     * @param other  other Neuron
     * @param weight weight for that Neuron
     */
    public void addConnectionFromOtherToThis(Neuron other, double weight) {
        this.hasOutput = false;
        prevNeurons.add(other);
        prevNeuronWeights.add(weight);
    }

    /**
     * Removes a connection from given Neuron to this Neuron
     *
     * @param other other Neuron
     */
    public void removeConnectionFromOtherToThis(Neuron other) {
        hasOutput = false;
        prevNeuronWeights.remove(prevNeurons.indexOf(other));
        prevNeurons.remove(other);
    }

    public DoubleUnaryOperator getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(DoubleUnaryOperator activationFunction) {
        this.activationFunction = activationFunction;
    }

    public List<Double> getPrevNeuronWeights() {
        return prevNeuronWeights;
    }

    public void setPrevNeuronWeights(List<Double> prevNeuronWeights) {
        hasOutput = false;
        this.prevNeuronWeights = prevNeuronWeights;
    }

    public void setPrevWeightsAndBias(List<Double> prevNeuronWeights, Double bias) {
        setPrevNeuronWeights(prevNeuronWeights);
        setBias(bias);
    }

    public Double getBias() {
        return bias;
    }

    public void setBias(Double bias) {
        hasOutput = false;
        this.bias = bias;
    }

    public double getOutput() {
        return output;
    }

    public List<Neuron> getPrevNeurons() {
        return prevNeurons;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public boolean hasOutput() {
        return hasOutput;
    }

    public void setHasOutput(boolean hasOutput) {
        this.hasOutput = hasOutput;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Neuron(").append(id).append(" bias= ").append(bias).append(" neuronID-weight: ");

        for (int i = 0; i < prevNeurons.size(); i++)
            sb.append("{ ").append(prevNeurons.get(i).id).append(", ").append(prevNeuronWeights.get(i)).append("}");

        sb.append(")");
        return sb.toString();
    }

    public static void main(String[] args) {
        //input hidden 2 output 1
        ArrayList<Neuron> input = new ArrayList<>();
        ArrayList<Neuron> hidden = new ArrayList<>();
        int id = 1;

        for (int i = 0; i < 4; i++)
            input.add(new Neuron(id++));

        for (int i = 0; i < 2; i++)
            hidden.add(new Neuron(id++));

        Neuron output = new Neuron(id);

        for (Neuron inputNeuron : input)
            for (Neuron hiddenNeuron : hidden)
                hiddenNeuron.addConnectionFromOtherToThis(inputNeuron);

        for (Neuron hiddenNeuron : hidden)
            output.addConnectionFromOtherToThis(hiddenNeuron);

        System.out.println(output.calculateOutput());
    }

}
