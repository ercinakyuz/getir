package com.getir.framework.jwt.configuration;

import io.jsonwebtoken.security.Keys;
import lombok.Builder;

import java.nio.charset.StandardCharsets;
import java.security.Key;

//@Builder
public class JwtSecret {

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public Key getKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
}
