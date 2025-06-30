package dev.alexguesser.temperaturemonitoring.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import dev.alexguesser.temperaturemonitoring.api.model.TemperatureLogData;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorId;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorMonitoring;
import dev.alexguesser.temperaturemonitoring.domain.model.TemperatureLog;
import dev.alexguesser.temperaturemonitoring.domain.model.TemperatureLogId;
import dev.alexguesser.temperaturemonitoring.domain.repository.SensorMonitoringRepository;
import dev.alexguesser.temperaturemonitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureMonitoringService {

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    @Transactional
    public void processTemperatureData(TemperatureLogData temperatureLogData) {

        sensorMonitoringRepository.findById(new SensorId(temperatureLogData.sensorId()))
                .ifPresentOrElse(
                        sensorMonitoring -> handleSensorMonitoring(sensorMonitoring, temperatureLogData),
                        () -> logNotFoundSensor(temperatureLogData)
                );


    }

    private void logNotFoundSensor(TemperatureLogData temperatureLogData) {
        log.info("Temperature data received for sensor with ID {} but no monitoring found. Data: {}",
                temperatureLogData.sensorId(), temperatureLogData);

    }

    private void handleSensorMonitoring(SensorMonitoring sensorMonitoring, TemperatureLogData temperatureLogData) {
        if (!sensorMonitoring.isEnabled()) {
            log.info("Sensor monitoring is disabled for sensor with ID {}. Data: {}",
                    temperatureLogData.sensorId(), temperatureLogData);
            return;
        }
        sensorMonitoring.setLastTemperature(temperatureLogData.value());
        sensorMonitoring.setUpdatedAt(OffsetDateTime.now());
        sensorMonitoringRepository.save(sensorMonitoring);

        temperatureLogRepository.save(
                TemperatureLog.builder()
                        .id(new TemperatureLogId(temperatureLogData.id()))
                        .registeredAt(temperatureLogData.registeredAt())
                        .value(temperatureLogData.value())
                        .sensorId(new SensorId(temperatureLogData.sensorId()))
                        .build()
        );

        log.info("Temperature data processed for sensor with ID {}. Data: {}",
                temperatureLogData.sensorId(), temperatureLogData);
    }

}
