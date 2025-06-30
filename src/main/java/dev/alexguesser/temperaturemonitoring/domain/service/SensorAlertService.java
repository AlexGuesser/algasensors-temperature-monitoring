package dev.alexguesser.temperaturemonitoring.domain.service;

import org.springframework.stereotype.Service;

import dev.alexguesser.temperaturemonitoring.api.model.TemperatureLogData;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorAlert;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorId;
import dev.alexguesser.temperaturemonitoring.domain.repository.SensorAlertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorAlertService {


    private final SensorAlertRepository sensorAlertRepository;

    @Transactional
    public void handleAlert(TemperatureLogData temperatureLogData) {
        sensorAlertRepository.findById(new SensorId(temperatureLogData.sensorId())).ifPresentOrElse(sensorAlert -> {
            if (isCurrentTemperatureAboveThreshold(temperatureLogData, sensorAlert)) {
                log.warn("Temperature {} exceeds maximum threshold {} for sensor ID {}", temperatureLogData.value(), sensorAlert.getMaxTemperature(), temperatureLogData.sensorId());

            }

            if (isCurrentTemperatureBelowThreshold(temperatureLogData, sensorAlert)) {
                log.warn("Temperature {} is below minimum threshold {} for sensor ID {}", temperatureLogData.value(), sensorAlert.getMinTemperature(), temperatureLogData.sensorId());
            }
        }, () -> log.info("Alert ignored for sensor with ID {}. No monitoring found.", temperatureLogData.sensorId()));
    }

    private boolean isCurrentTemperatureAboveThreshold(TemperatureLogData temperatureLogData, SensorAlert sensorAlert) {
        return sensorAlert.getMaxTemperature() != null && temperatureLogData.value() > sensorAlert.getMaxTemperature();
    }

    private boolean isCurrentTemperatureBelowThreshold(TemperatureLogData temperatureLogData, SensorAlert sensorAlert) {
        return sensorAlert.getMinTemperature() != null && temperatureLogData.value() < sensorAlert.getMinTemperature();
    }

}
