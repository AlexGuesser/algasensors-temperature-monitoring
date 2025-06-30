package dev.alexguesser.temperaturemonitoring.infra.rabbitmq;

import static dev.alexguesser.temperaturemonitoring.infra.rabbitmq.RabbitMQConfig.ALERTING_QUEUE;
import static dev.alexguesser.temperaturemonitoring.infra.rabbitmq.RabbitMQConfig.PROCESS_TEMPERATURE_QUEUE;

import java.time.Duration;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import dev.alexguesser.temperaturemonitoring.api.model.TemperatureLogData;
import dev.alexguesser.temperaturemonitoring.domain.service.SensorAlertService;
import dev.alexguesser.temperaturemonitoring.domain.service.TemperatureMonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService;
    private final SensorAlertService sensorAlertService;


    @RabbitListener(queues = PROCESS_TEMPERATURE_QUEUE, concurrency = "2-3")
    @SneakyThrows
    public void handleTemperatureMonitoringMessage(@Payload TemperatureLogData message, @Headers Map<String, Object> headers) {
        temperatureMonitoringService.processTemperatureData(message);
        Thread.sleep(Duration.ofSeconds(5));
    }

    @RabbitListener(queues = ALERTING_QUEUE, concurrency = "2-3")
    @SneakyThrows
    public void handleAlerting(@Payload TemperatureLogData message, @Headers Map<String, Object> headers) {
        sensorAlertService.handleAlert(message);
        Thread.sleep(Duration.ofSeconds(5));
    }

}
