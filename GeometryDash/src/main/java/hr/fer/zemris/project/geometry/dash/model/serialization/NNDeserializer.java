package hr.fer.zemris.project.geometry.dash.model.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import hr.fer.zemris.project.geometry.dash.ai.NeuralNetwork;

import java.lang.reflect.Type;

public class NNDeserializer implements JsonDeserializer<NeuralNetwork> {

    @Override
    public NeuralNetwork deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
