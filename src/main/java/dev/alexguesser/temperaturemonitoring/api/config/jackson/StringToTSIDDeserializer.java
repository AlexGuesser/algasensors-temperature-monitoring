package dev.alexguesser.temperaturemonitoring.api.config.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.hypersistence.tsid.TSID;

public class StringToTSIDDeserializer extends JsonDeserializer<TSID> {
    @Override
    public TSID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return TSID.from(p.getText());
    }
}
