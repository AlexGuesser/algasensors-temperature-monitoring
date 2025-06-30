package dev.alexguesser.temperaturemonitoring.api.controller;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.alexguesser.temperaturemonitoring.api.model.SensorMonitoringOutput;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorId;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorMonitoring;
import dev.alexguesser.temperaturemonitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {

    private final SensorMonitoringRepository sensorMonitoringRepository;

    @GetMapping
    public SensorMonitoringOutput getDetail(@PathVariable TSID sensorId) {
        return sensorMonitoringRepository.findById(new SensorId(sensorId))
                       .map(sensorMonitoring -> new SensorMonitoringOutput(
                               sensorMonitoring.getId().getValue(),
                               sensorMonitoring.getLastTemperature(),
                               sensorMonitoring.getUpdatedAt(),
                               sensorMonitoring.isEnabled()))
                       .orElseThrow(
                               () -> new ResponseStatusException(
                                       org.springframework.http.HttpStatus.NOT_FOUND,
                                       "Sensor monitoring not found for sensor ID: " + sensorId));
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableMonitoring(@PathVariable TSID sensorId) {
        sensorMonitoringRepository.findById(new SensorId(sensorId))
                .ifPresentOrElse(sensorMonitoring -> {
                    sensorMonitoring.setEnabled(true);
                    sensorMonitoringRepository.save(sensorMonitoring);
                }, () -> {
                    SensorMonitoring.SensorMonitoringBuilder builder = SensorMonitoring.builder()
                            .id(new SensorId(sensorId))
                            .enabled(true)
                            .updatedAt(OffsetDateTime.now());
                    sensorMonitoringRepository.save(builder.build());
                });
    }

    @DeleteMapping("/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableMonitoring(@PathVariable TSID sensorId) {
        sensorMonitoringRepository.findById(new SensorId(sensorId))
                .ifPresentOrElse(sensorMonitoring -> {
                    sensorMonitoring.setEnabled(false);
                    sensorMonitoringRepository.save(sensorMonitoring);
                }, () -> {
                    throw new ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND,
                            "Sensor monitoring not found for sensor ID: " + sensorId);
                });
    }

}
