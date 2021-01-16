package hr.fer.zemris.project.geometry.dash.ai;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.project.geometry.dash.ai.neurons.ElmanHiddenNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.ElmanOutputNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.InputNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.Neuron;

public class TestingAI {

	public static void main(String[] args) {
		// test1();
		// test2();
		//test3();
		test4();
	}

	private static void test1() {
		int id = 1;

		ArrayList<InputNeuron> input = new ArrayList<>();
		ArrayList<Neuron> hidden = new ArrayList<>();

		for (int i = 0; i < 4; i++)
			input.add(new InputNeuron(id++));

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

	private static void test2() {
		int id = 1;

		ArrayList<InputNeuron> input = new ArrayList<>();
		ArrayList<ElmanHiddenNeuron> hidden = new ArrayList<>();

		for (int i = 0; i < 4; i++)
			input.add(new InputNeuron(id++));

		for (int i = 0; i < 2; i++) {
			hidden.add(new ElmanHiddenNeuron(id));
			id += 2;
		}

		Neuron output = new Neuron(id);

		for (Neuron inputNeuron : input)
			for (Neuron hiddenNeuron : hidden)
				hiddenNeuron.addConnectionFromOtherToThis(inputNeuron);

		for (Neuron hiddenNeuron : hidden)
			output.addConnectionFromOtherToThis(hiddenNeuron);

		System.out.println(output.calculateOutput());
	}

	private static void test3() {
		GeneticNeuralNetwork neuralNetwork = new GeneticNeuralNetwork(4, 1, 2);

//	      Setting the new inputLayer
		ArrayList<Neuron> input = new ArrayList<>();
		int id = 1;
		for (int i = 0; i < 4; i++)
			input.add(new InputNeuron(id++));
		//neuralNetwork.createInputLayer(input);

//	      Setting the new hiddenLayer
		ArrayList<List<Neuron>> hidden = new ArrayList<>();
		ArrayList<Neuron> hidden1 = new ArrayList<>();
		for (int i = 0; i < 4; i++)
			hidden1.add(new Neuron(id++));
		hidden.add(hidden1);

		ArrayList<Neuron> hidden2 = new ArrayList<>();
		for (int i = 0; i < 4; i++)
			hidden2.add(new Neuron(id++));
		hidden.add(hidden2);

		//neuralNetwork.createHiddenLayer(hidden);

//	      Inserting a new hiddenLayer
		ArrayList<Neuron> hidden3 = new ArrayList<>();
		for (int i = 0; i < 4; i++)
			hidden3.add(new Neuron(id++));

		//neuralNetwork.insertHiddenLayer(hidden3, 1);

		System.out.println(neuralNetwork.output.calculateOutput());
	}
	
	private static void test4() {
		ElmanNeuralNetwork neuralNetwork = new ElmanNeuralNetwork(2, 2);

//	      Setting the new inputLayer
		ArrayList<Neuron> input = new ArrayList<>();
		int id = 1;
		for (int i = 0; i < 2; i++)
			input.add(new InputNeuron(id++));
		neuralNetwork.createInputLayer(input);

//	      Setting the new hiddenLayer
		ArrayList<List<Neuron>> hidden = new ArrayList<>();
		ArrayList<Neuron> hidden1 = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			hidden1.add(new ElmanHiddenNeuron(id));
			id += 2;
		}
		hidden.add(hidden1);

		neuralNetwork.createHiddenLayer(hidden);
		
		neuralNetwork.createOutput(new ElmanOutputNeuron(-1));

		System.out.println(neuralNetwork.output.calculateOutput());
		System.out.println();
		System.out.println(neuralNetwork.output.calculateOutput());
	}

}
