package dev.alexguesser.temperaturemonitoring.domain.model;

import java.time.OffsetDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class SensorMonitoring {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "bigint"))
    private SensorId id;

    private double lastTemperature;

    private OffsetDateTime updatedAt;

    private boolean enabled;

}
