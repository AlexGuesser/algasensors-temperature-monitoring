package dev.alexguesser.temperaturemonitoring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alexguesser.temperaturemonitoring.domain.model.SensorId;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorMonitoring;

public interface SensorMonitoringRepository extends JpaRepository<SensorMonitoring, SensorId> {
}
