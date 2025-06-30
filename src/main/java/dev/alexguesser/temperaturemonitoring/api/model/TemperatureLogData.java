package dev.alexguesser.temperaturemonitoring.api.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.hypersistence.tsid.TSID;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record TemperatureLogData(
        UUID id,
        TSID sensorId,
        OffsetDateTime registeredAt,
        double value
) {

}
