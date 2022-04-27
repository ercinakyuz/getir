package com.getir.framework.jwt.configuration;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class JwtConfiguration {

    private final JwtProperties jwtProperties;

    @Bean("accessTokenParser")
    public JwtParser accessTokenParser() {
        return Jwts.parserBuilder().setSigningKey(jwtProperties.getAccess().getSecret().getKey()).build();
    }

    @Bean("refreshTokenParser")
    public JwtParser refreshTokenParser() {
        return Jwts.parserBuilder().setSigningKey(jwtProperties.getRefresh().getSecret().getKey()).build();
    }

    @Bean("accessTokenBuilder")
    public JwtBuilder jwtBuilderForAccessToken() {
        return Jwts.builder().signWith(jwtProperties.getAccess().getSecret().getKey());
    }

    @Bean("refreshTokenBuilder")
    public JwtBuilder jwtBuilderForRefreshToken() {
        return Jwts.builder().signWith(jwtProperties.getRefresh().getSecret().getKey());
    }
}
