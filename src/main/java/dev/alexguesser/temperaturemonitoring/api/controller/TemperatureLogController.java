package dev.alexguesser.temperaturemonitoring.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.alexguesser.temperaturemonitoring.api.model.TemperatureLogData;
import dev.alexguesser.temperaturemonitoring.domain.model.SensorId;
import dev.alexguesser.temperaturemonitoring.domain.model.TemperatureLog;
import dev.alexguesser.temperaturemonitoring.domain.repository.TemperatureLogRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures")
@RequiredArgsConstructor
public class TemperatureLogController {

    private final TemperatureLogRepository temperatureLogRepository;

    @GetMapping
    public Page<TemperatureLogData> searchBySensorId(
            @PathVariable("sensorId") TSID sensorId,
            @PageableDefault Pageable pageable
    ) {
        Page<TemperatureLog> temperatureLogs = temperatureLogRepository.findAllBySensorId(new SensorId(sensorId), pageable);
        return temperatureLogs.map(temperatureLog -> TemperatureLogData.builder()
                                                             .id(temperatureLog.getId().getValue())
                                                             .sensorId(temperatureLog.getSensorId().getValue())
                                                             .registeredAt(temperatureLog.getRegisteredAt())
                                                             .value(temperatureLog.getValue())
                                                             .build());
    }

}
