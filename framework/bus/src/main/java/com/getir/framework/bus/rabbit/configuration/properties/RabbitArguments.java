package com.getir.framework.bus.rabbit.configuration.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RabbitArguments {
    private String deadLetterRoutingKey;
    private String deadLetterExchange;
}
