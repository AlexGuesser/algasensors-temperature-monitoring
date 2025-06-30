package dev.alexguesser.temperaturemonitoring.domain.model;

import java.time.OffsetDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TemperatureLog {

    @Id
    @AttributeOverride(
            name = "value",
            column = @Column(name = "id", columnDefinition = "uuid")
    )
    private TemperatureLogId id;

    @Column(name = "\"value\"")
    private double value;

    private OffsetDateTime registeredAt;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "sensor_id", columnDefinition = "bigint")
    )
    private SensorId sensorId;





}
