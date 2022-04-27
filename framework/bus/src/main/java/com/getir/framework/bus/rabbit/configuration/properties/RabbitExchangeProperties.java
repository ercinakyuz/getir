package com.getir.framework.bus.rabbit.configuration.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RabbitExchangeProperties {

    private String name;

    private String deadLetter;
}
