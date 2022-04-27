package com.getir.framework.jwt.builder;


import com.getir.framework.jwt.configuration.JwtProperties;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static com.getir.framework.jwt.model.GetirJwtClaimKeys.MEMBER_ID;

@Component
public class JwtTokenBuilder {

    private final JwtProperties jwtProperties;
    private final JwtBuilder accessTokenBuilder;
    private final JwtBuilder refreshTokenBuilder;

    public JwtTokenBuilder(final JwtProperties jwtProperties,
                           final @Qualifier("accessTokenBuilder") JwtBuilder accessTokenBuilder,
                           final @Qualifier("refreshTokenBuilder") JwtBuilder refreshTokenBuilder) {
        this.jwtProperties = jwtProperties;
        this.accessTokenBuilder = accessTokenBuilder;
        this.refreshTokenBuilder = refreshTokenBuilder;
    }

    public GetirJwtTokens build(final GetirJwtClaims getirJwtClaims) {
        var issuedAtMillis = System.currentTimeMillis();
        return GetirJwtTokens.builder()
                .accessToken(buildAccessToken(getirJwtClaims, issuedAtMillis))
                .refreshToken(buildRefreshToken(getirJwtClaims, issuedAtMillis))
                .build();
    }

    private String buildAccessToken(final GetirJwtClaims getirJwtClaims, final long issuedAtMillis) {
        var accessTokenProperties = jwtProperties.getAccess();
        return accessTokenBuilder
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date(issuedAtMillis))
                .setExpiration(afterDuration(issuedAtMillis, accessTokenProperties.getExpiration()))
                .addClaims(Map.of(
                        MEMBER_ID, getirJwtClaims.getMemberId()
                        ))
                .compact();
    }

    private String buildRefreshToken(final GetirJwtClaims getirJwtClaims, final long issuedAtMillis) {
        var accessTokenProperties = jwtProperties.getAccess();
        var refreshTokenProperties = jwtProperties.getRefresh();
        return refreshTokenBuilder
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(afterDuration(issuedAtMillis, accessTokenProperties.getExpiration()))
                .setExpiration(afterDuration(issuedAtMillis, refreshTokenProperties.getExpiration()))
                .addClaims(Map.of(
                        MEMBER_ID, getirJwtClaims.getMemberId()
                        )
                ).compact();
    }

    private Date afterDuration(final long timeMillis, final Duration after) {
        return new Date(timeMillis + after.toMillis());
    }
}
