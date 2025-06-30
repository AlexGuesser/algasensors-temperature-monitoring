package dev.alexguesser.temperaturemonitoring.api.config.web;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.hypersistence.tsid.TSID;

@Component
public class WebConverter implements Converter<String, TSID> {

    @Override
    public TSID convert(String source) {
        return TSID.from(source);
    }

}
