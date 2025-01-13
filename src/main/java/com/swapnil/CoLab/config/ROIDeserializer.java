package com.swapnil.CoLab.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ROIDeserializer extends JsonDeserializer<Double>

{
    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.isNumber()) {
            return node.asDouble();
        } else if (node.isObject()) {
            return node.get("percentage").asDouble(); // Adjust based on your JSON structure
        }
        return null;
    }
}
