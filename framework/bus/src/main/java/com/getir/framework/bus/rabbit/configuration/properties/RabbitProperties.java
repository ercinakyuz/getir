package com.getir.framework.bus.rabbit.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq.custom")
public class RabbitProperties {

    private RabbitArguments args;
    private RabbitExchangeProperties exchange;
    private RabbitBindingProperties orderCompleted;

}


