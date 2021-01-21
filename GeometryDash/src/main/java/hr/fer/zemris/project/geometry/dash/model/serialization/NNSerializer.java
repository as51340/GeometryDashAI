package hr.fer.zemris.project.geometry.dash.model.serialization;

import com.google.gson.*;
import hr.fer.zemris.project.geometry.dash.ai.AIConstants;
import hr.fer.zemris.project.geometry.dash.ai.ElmanNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.GeneticNeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;
import hr.fer.zemris.project.geometry.dash.ai.neurons.Neuron;

import java.lang.reflect.Type;
import java.util.List;

public class NNSerializer implements JsonSerializer<NeuralNetwork> {
    @Override
    public JsonElement serialize(NeuralNetwork src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject nnJson = new JsonObject();
        nnJson.add("output", getJsonNeuronBasedOnPrevNeuron(src.getOutput(), false));

        JsonArray inputLayer = new JsonArray();
        for(Neuron n : src.getInputLayer()) {
            inputLayer.add(getJsonNeuronBasedOnPrevNeuron(n, false));
        }
        nnJson.add("inputLayer", inputLayer);

        JsonArray hiddenLayersJson = new JsonArray();
        for(List<Neuron> hiddenLayer : src.getHiddenLayers()) {
            JsonArray hiddenLayerJson = new JsonArray();
            for(Neuron n : hiddenLayer) {
                hiddenLayerJson.add(getJsonNeuronBasedOnPrevNeuron(n, true));
            }
            hiddenLayersJson.add(hiddenLayerJson);
        }
        nnJson.add("hiddenLayers", hiddenLayersJson);

        if (AIConstants.activationFunction.equals(src.getActivationFunction())) {
            nnJson.addProperty("activationFunction", "sigmoid");
        } else {} /*dodati po potrebi*/


        if(src.getClass() == GeneticNeuralNetwork.class) {
            nnJson.addProperty("type", "genetic");
        } else if(src.getClass() == ElmanNeuralNetwork.class) {
            nnJson.addProperty("type", "elman");
        }

        return nnJson;
    }

    /**
     *
     * @param neuron
     * @param prevNeurons if true, adds list of previous neurons
     * @return
     */
    private JsonElement getJsonNeuronBasedOnPrevNeuron(Neuron neuron, boolean prevNeurons) {
        JsonObject outputNeuron = new JsonObject();
        outputNeuron.addProperty("output", neuron.getOutput());
        outputNeuron.addProperty("bias", neuron.getBias());

        if(prevNeurons) {
            JsonArray previous = new JsonArray();
            List<Neuron> previousNeurons = neuron.getPrevNeurons();
            List<Double> previousWeights = neuron.getPrevNeuronWeights();
            for (int i = 0; i < previousNeurons.size(); i++) {
                JsonObject previousNeuron = new JsonObject();
                previousNeuron.add("neuron", getJsonNeuronBasedOnPrevNeuron(previousNeurons.get(i), false));
                previousNeuron.addProperty("weight", previousWeights.get(i));
                previous.add(previousNeuron);
            }
            outputNeuron.add("prevNeurons", previous);
        }

        return outputNeuron;
    }
}
