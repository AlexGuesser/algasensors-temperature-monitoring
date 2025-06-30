package dev.alexguesser.temperaturemonitoring.infra.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {

    public static final String PROCESS_TEMPERATURE_QUEUE = "temperature-monitoring.process-temperature.v1.q";
    public static final String ALERTING_QUEUE = "temperature-monitoring.alerting.v1.q";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public Queue queueProcessTemperature() {
        return QueueBuilder.durable(PROCESS_TEMPERATURE_QUEUE)
                       .build();
    }

    @Bean
    public Queue queueAlerting() {
        return QueueBuilder.durable(ALERTING_QUEUE)
                       .build();
    }

    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange(
                "temperature-processing.temperature-received.v1.e"
        ).build();
    }

    @Bean
    public Binding bindingProcessTemperature() {
        return BindingBuilder.bind(queueProcessTemperature())
                       .to(exchange());
    }

    @Bean
    public Binding bindingAlerting() {
        return BindingBuilder.bind(queueAlerting())
                       .to(exchange());
    }

}
