package dev.alexguesser.temperaturemonitoring.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.alexguesser.temperaturemonitoring.api.model.SensorAlertInput;
import dev.alexguesser.temperaturemonitoring.api.model.SensorAlertOutput;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorAlert;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorId;
import dev.alexguesser.temperaturemonitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/sensors/{sensorId}/alert")
@RestController
@RequiredArgsConstructor
public class SensorAlertController {

    private final SensorAlertRepository sensorAlertRepository;

    @GetMapping
    public SensorAlertOutput getSensorAlert(@PathVariable(value = "sensorId") TSID sensorId) {
        SensorAlert sensorAlert = sensorAlertRepository.findById(new SensorId(sensorId))
                                          .orElseThrow(() -> new ResponseStatusException(
                                                  HttpStatus.NOT_FOUND,
                                                  "Sensor alert not found for sensor ID: " + sensorId));
        return SensorAlertOutput.from(sensorAlert);
    }

    @PutMapping
    public SensorAlertOutput upsertSensorAlert(
            @PathVariable(value = "sensorId") TSID sensorId,
            @RequestBody SensorAlertInput sensorAlertInput
    ) {
        SensorAlert sensorAlert = sensorAlertRepository.findById(new SensorId(sensorId))
                                          .map(
                                                  existingAlert -> {
                                                      existingAlert.setMaxTemperature(sensorAlertInput.maxTemperature());
                                                      existingAlert.setMinTemperature(sensorAlertInput.minTemperature());
                                                      return existingAlert;
                                                  }
                                          )
                                          .orElse(
                                                  new SensorAlert(
                                                          new SensorId(sensorId),
                                                          sensorAlertInput.maxTemperature(),
                                                          sensorAlertInput.minTemperature()
                                                  )
                                          );

        sensorAlertRepository.save(sensorAlert);

        return SensorAlertOutput.from(sensorAlert);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensorAlert(@PathVariable(value = "sensorId") TSID sensorId) {
        SensorAlert sensorAlert = sensorAlertRepository.findById(new SensorId(sensorId))
                                          .orElseThrow(() -> new ResponseStatusException(
                                                  HttpStatus.NOT_FOUND,
                                                  "Sensor alert not found for sensor ID: " + sensorId));
        sensorAlertRepository.delete(sensorAlert);
    }


}
