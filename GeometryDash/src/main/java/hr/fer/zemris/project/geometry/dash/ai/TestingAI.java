package hr.fer.zemris.project.geometry.dash.ai;

import java.util.ArrayList;

import hr.fer.zemris.project.geometry.dash.ai.neurons.ElmanHiddenNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.InputNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.Neuron;

public class TestingAI {
	
	public static void main(String[] args) {
		//test1();
		test2();
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

}
