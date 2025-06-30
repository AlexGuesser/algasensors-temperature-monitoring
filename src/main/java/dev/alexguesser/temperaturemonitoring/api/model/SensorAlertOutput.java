package dev.alexguesser.temperaturemonitoring.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dev.alexguesser.temperaturemonitoring.domain.model.SensorAlert;
import io.hypersistence.tsid.TSID;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record SensorAlertOutput(
        TSID id,
        Double maxTemperature,
        Double minTemperature
) {
    public static SensorAlertOutput from(SensorAlert sensorAlert) {
        return SensorAlertOutput.builder()
                       .id(sensorAlert.getId().getValue())
                       .maxTemperature(sensorAlert.getMaxTemperature())
                       .minTemperature(sensorAlert.getMinTemperature())
                       .build();
    }
}
