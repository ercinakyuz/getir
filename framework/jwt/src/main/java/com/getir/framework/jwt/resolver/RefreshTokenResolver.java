package com.getir.framework.jwt.resolver;


import com.getir.framework.jwt.model.GetirJwtClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.getir.framework.jwt.model.GetirJwtClaimKeys.MEMBER_ID;

@Component
public class RefreshTokenResolver {

    private final JwtParser jwtParser;

    public RefreshTokenResolver(final @Qualifier("refreshTokenParser") JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }
    //TODO: Check issuedAt before now
    public GetirJwtClaims resolve(final String refreshToken) {
        final Claims claims = jwtParser.parseClaimsJws(refreshToken).getBody();
        return GetirJwtClaims.builder()
                .memberId(UUID.fromString(claims.get(MEMBER_ID, String.class)))
                .build();
    }
}
