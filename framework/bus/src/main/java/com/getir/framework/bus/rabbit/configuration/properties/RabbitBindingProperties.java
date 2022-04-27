package com.getir.framework.bus.rabbit.configuration.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RabbitBindingProperties {
    private String routingKey;
    private String deadLetterRoutingKey;
    private String queue;
    private String deadLetterQueue;
}
