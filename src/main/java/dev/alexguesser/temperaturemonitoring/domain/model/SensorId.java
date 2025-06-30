package dev.alexguesser.temperaturemonitoring.domain.model;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SensorId implements Serializable {


    private TSID value;

    public SensorId(TSID value) {
        requireNonNull(value, "SensorId cannot be null");
        this.value = value;
    }

    public SensorId(String value) {
        requireNonNull(value, "SensorId cannot be null");
        this.value = TSID.from(value);
    }

    public SensorId(Long value) {
        requireNonNull(value, "SensorId cannot be null");
        this.value = TSID.from(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
