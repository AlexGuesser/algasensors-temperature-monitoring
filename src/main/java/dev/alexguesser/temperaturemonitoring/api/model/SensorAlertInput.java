package dev.alexguesser.temperaturemonitoring.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SensorAlertInput(
        Double maxTemperature,
        Double minTemperature
) {
}
