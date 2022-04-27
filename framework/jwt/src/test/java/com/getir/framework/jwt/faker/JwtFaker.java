package com.getir.framework.jwt.faker;

import com.getir.framework.jwt.configuration.JwtSecret;
import com.getir.framework.jwt.configuration.TokenProperties;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.framework.test.faker.AbstractFaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import static com.getir.framework.jwt.model.GetirJwtClaimKeys.MEMBER_ID;

public class JwtFaker extends AbstractFaker {

    public UUID memberId(){
        return UUID.randomUUID();
    }

    public String stringifiedMemberId(){
        return memberId().toString();
    }
    public GetirJwtClaims getirJwtClaims() {
        return GetirJwtClaims.builder()
                .memberId(memberId())
                .build();
    }

    public GetirJwtTokens getirJwtTokens(){
        return GetirJwtTokens.builder()
                .accessToken(faker.code().ean13())
                .refreshToken(faker.code().ean13())
                .build();
    }

    public TokenProperties tokenProperties() {
        var tokenProperties = new TokenProperties();
        tokenProperties.setExpiration(Duration.ofMinutes(faker.number().numberBetween(1, 5)));
        tokenProperties.setSecret(jwtSecret());
        return tokenProperties;
    }

    private JwtSecret jwtSecret() {
        var jwtSecret = new JwtSecret();
        jwtSecret.setKey(faker.code().ean13());
        return jwtSecret;
    }

    public String issuer() {
        return faker.company().name();
    }

    public String token() {
        return faker.lorem().fixedString(30);
    }

    public Jws<Claims> accessTokenJwsClaims() {
        return new DefaultJws<>(null, accessTokenClaims(), null);
    }

    public Jws<Claims> refreshTokenJwsClaims() {
        return new DefaultJws<>(null, refreshTokenClaims(), null);
    }

    private Claims accessTokenClaims() {
        return new DefaultClaims(
                Map.of(
                        MEMBER_ID, stringifiedMemberId()
                )
        );
    }

    private Claims refreshTokenClaims() {
        return new DefaultClaims(
                Map.of(
                        MEMBER_ID, stringifiedMemberId()
                )
        );
    }
}
