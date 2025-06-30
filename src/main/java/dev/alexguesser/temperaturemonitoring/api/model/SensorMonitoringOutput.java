package dev.alexguesser.temperaturemonitoring.api.model;

import java.time.OffsetDateTime;

import io.hypersistence.tsid.TSID;
import lombok.Builder;

@Builder
public record SensorMonitoringOutput(
        TSID id,
        double lastTemperature,
        OffsetDateTime updatedAt,
        boolean enabled
) {
}
