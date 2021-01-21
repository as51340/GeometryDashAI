package hr.fer.zemris.project.geometry.dash.model.serialization;

import com.google.gson.*;
import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.ElmanNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.GeneticNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.neurons.InputNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.Neuron;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class NNDeserializer implements JsonDeserializer<NeuralNetwork> {

    @Override
    public NeuralNetwork deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject root = json.getAsJsonObject();

        DoubleUnaryOperator activation;
        switch (root.get("activationFunction").getAsString()) {
            case "sigmoid":
                activation = AIConstants.activationFunction;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + root.getAsJsonObject("activationFunction").getAsString());
        }


        JsonObject outputJson = root.getAsJsonObject("output");
        Neuron output = new Neuron(
                outputJson.get("bias").getAsDouble(),
                outputJson.get("output").getAsDouble()
        );

        List<Neuron> inputLayer = new ArrayList<>();
        JsonArray inputLayerJson = root.getAsJsonArray("inputLayer");
        for(int i = 0; i < inputLayerJson.size(); i++) {
            JsonObject inputLayerNeuron =  inputLayerJson.get(i).getAsJsonObject();
            inputLayer.add(new InputNeuron(
                    inputLayerNeuron.get("bias").getAsDouble(),
                    inputLayerNeuron.get("output").getAsDouble())
            );
        }

        List<List<Neuron>> hiddenLayers = new ArrayList<>();
        JsonArray hiddenLayersJson = root.getAsJsonArray("hiddenLayers");
        List<Neuron> previousLayer = new ArrayList<>();
        for(int i = 0; i < hiddenLayersJson.size(); i++) {
            JsonArray hiddenLayerJson = hiddenLayersJson.get(i).getAsJsonArray();
            List<Neuron> hiddenLayer = new ArrayList<>();
            for(int j = 0; j < hiddenLayerJson.size(); j++) {
                JsonObject hiddenLayerNeuron = hiddenLayerJson.get(j).getAsJsonObject();
                Neuron neuron = new Neuron(
                        hiddenLayerNeuron.get("bias").getAsDouble(),
                        hiddenLayerNeuron.get("output").getAsDouble()
                );
                hiddenLayer.add(neuron);
                JsonArray previousNeuronsJson = hiddenLayerNeuron.getAsJsonArray("prevNeurons");
                for(int k = 0; k < previousNeuronsJson.size(); k++) {
                    JsonObject connection = previousNeuronsJson.get(k).getAsJsonObject();
                    double outputD = connection.getAsJsonObject("neuron").get("output").getAsDouble();
                    Double biasD = connection.getAsJsonObject("neuron").get("bias").getAsDouble();

                    double weight = connection.get("weight").getAsDouble();
                    for(Neuron n : previousLayer)
                        if(n.getBias().equals(biasD) && Math.abs(n.getOutput() - outputD) < 1e-9)
                            neuron.addConnectionFromOtherToThis(n, weight);
                }

            }
            hiddenLayers.add(hiddenLayer);
            previousLayer = hiddenLayer;
        }

        NeuralNetwork nn;
        if(root.get("type").getAsString().equals("genetic"))
            nn = new GeneticNeuralNetwork(output, inputLayer, hiddenLayers, activation);
        else if (root.get("type").getAsString().equals("elman"))
            nn = new ElmanNeuralNetwork(output, inputLayer, hiddenLayers, activation);
        else {
            nn = null;
            throw new IllegalStateException("Wrong type");
        }

        return nn;
    }

    public static NeuralNetwork deserialize(String json) {
        return null;
    }
}
