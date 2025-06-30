package dev.alexguesser.temperaturemonitoring.api.config.jackson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.hypersistence.tsid.TSID;

@Configuration
public class JacksonConfig {

    @Bean
    public Module tsidModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(TSID.class, new TSIDToStringSerializer());
        simpleModule.addDeserializer(TSID.class, new StringToTSIDDeserializer());
        return simpleModule;
    }

}
