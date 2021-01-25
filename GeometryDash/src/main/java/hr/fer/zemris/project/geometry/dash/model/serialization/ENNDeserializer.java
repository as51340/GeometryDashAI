package hr.fer.zemris.project.geometry.dash.model.serialization;

import com.google.gson.*;
import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.ElmanNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.GeneticNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.neurons.ContextNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.ElmanHiddenNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.InputNeuron;
import hr.fer.zemris.project.geometry.dash.ai.neurons.Neuron;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class ENNDeserializer implements JsonDeserializer<ElmanNeuralNetwork> {
    @Override
    public ElmanNeuralNetwork deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject root = json.getAsJsonObject();

        DoubleUnaryOperator activation;
        switch (root.get("activationFunction").getAsString()) {
            case "sigmoid":
                activation = AIConstants.activationFunction;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + root.getAsJsonObject("activationFunction").getAsString());
        }


        List<Neuron> inputLayer = new ArrayList<>();
        JsonArray inputLayerJson = root.getAsJsonArray("inputLayer");
        for (int i = 0; i < inputLayerJson.size(); i++) {
            JsonObject inputLayerNeuron = inputLayerJson.get(i).getAsJsonObject();
            InputNeuron inputNeuron = new InputNeuron(
                    inputLayerNeuron.get("bias").getAsDouble(),
                    inputLayerNeuron.get("output").getAsDouble(),
                    inputLayerNeuron.get("id").getAsInt()
            );
            inputNeuron.setInput(inputLayerNeuron.get("input").getAsDouble());
            inputNeuron.setInputWeight(inputLayerNeuron.get("inputWeight").getAsDouble());
            inputLayer.add(inputNeuron);
        }

        List<List<Neuron>> hiddenLayers = new ArrayList<>();
        JsonArray hiddenLayersJson = root.getAsJsonArray("hiddenLayers");
        List<Neuron> previousLayer = new ArrayList<>();
        for (int i = 0; i < hiddenLayersJson.size(); i++) {
            JsonArray hiddenLayerJson = hiddenLayersJson.get(i).getAsJsonArray();
            List<Neuron> hiddenLayer = new ArrayList<>();
            for (int j = 0; j < hiddenLayerJson.size(); j++) {
                JsonObject hiddenLayerNeuron = hiddenLayerJson.get(j).getAsJsonObject();
                ContextNeuron contextNeuron = new ContextNeuron(hiddenLayerNeuron.get("contextId").getAsInt());
                ElmanHiddenNeuron neuron = new ElmanHiddenNeuron(
                        hiddenLayerNeuron.get("bias").getAsDouble(),
                        hiddenLayerNeuron.get("output").getAsDouble(),
                        hiddenLayerNeuron.get("id").getAsInt(),
                        contextNeuron,
                        hiddenLayerNeuron.get("contextWeight").getAsDouble()
                );
                contextNeuron.addConnectionFromOtherToThis(neuron);
                hiddenLayer.add(neuron);
                JsonArray previousNeuronsJson = hiddenLayerNeuron.getAsJsonArray("prevNeurons");
                for (int k = 0; k < previousNeuronsJson.size(); k++) {
                    JsonObject connection = previousNeuronsJson.get(k).getAsJsonObject();
                    /*double outputD = connection.getAsJsonObject("neuron").get("output").getAsDouble();
                    Double biasD = connection.getAsJsonObject("neuron").get("bias").getAsDouble();
                    */

                    int id = connection.getAsJsonObject("neuron").get("id").getAsInt();
                    double weight = connection.get("weight").getAsDouble();
                    for (Neuron n : previousLayer)
                        if (id == n.getId())
                            neuron.addConnectionFromOtherToThis(n, weight);
                }

            }
            hiddenLayers.add(hiddenLayer);
            previousLayer = hiddenLayer;
        }


        JsonObject outputJson = root.getAsJsonObject("output");
        ContextNeuron contextNeuron = new ContextNeuron(outputJson.get("contextId").getAsInt());
        ElmanHiddenNeuron output = new ElmanHiddenNeuron(
                outputJson.get("bias").getAsDouble(),
                outputJson.get("output").getAsDouble(),
                outputJson.get("id").getAsInt(),
                contextNeuron,
                outputJson.get("contextWeight").getAsDouble()
        );
        //output.calculateOutput();

        contextNeuron.addConnectionFromOtherToThis(output);

        JsonArray previousNeuronsJson = outputJson.getAsJsonArray("prevNeurons");
        for (int k = 0; k < previousNeuronsJson.size(); k++) {
            JsonObject connection = previousNeuronsJson.get(k).getAsJsonObject();
                    /*double outputD = connection.getAsJsonObject("neuron").get("output").getAsDouble();
                    Double biasD = connection.getAsJsonObject("neuron").get("bias").getAsDouble();
                    */

            int id = connection.getAsJsonObject("neuron").get("id").getAsInt();
            double weight = connection.get("weight").getAsDouble();
            for (Neuron n : previousLayer)
                if (id == n.getId())
                    output.addConnectionFromOtherToThis(n, weight);
        }



        ElmanNeuralNetwork nn = new ElmanNeuralNetwork(output, inputLayer, hiddenLayers, activation);


        return nn;
    }
}
