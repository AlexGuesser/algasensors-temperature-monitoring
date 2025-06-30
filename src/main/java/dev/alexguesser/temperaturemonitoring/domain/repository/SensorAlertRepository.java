package dev.alexguesser.temperaturemonitoring.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.alexguesser.temperaturemonitoring.domain.model.SensorAlert;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorId;

public interface SensorAlertRepository extends JpaRepository<SensorAlert, SensorId> {

}
