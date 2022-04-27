package com.getir.framework.jwt.configuration;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
//@Builder
public class TokenProperties {

    private Duration expiration;
    private JwtSecret secret;
}
