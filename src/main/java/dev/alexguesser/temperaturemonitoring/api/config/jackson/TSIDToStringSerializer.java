package dev.alexguesser.temperaturemonitoring.api.config.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.hypersistence.tsid.TSID;

public class TSIDToStringSerializer extends JsonSerializer<TSID> {

    @Override
    public void serialize(TSID value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }

}
