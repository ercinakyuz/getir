package com.getir.framework.jwt.resolver;


import com.getir.framework.jwt.model.GetirJwtClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.getir.framework.jwt.model.GetirJwtClaimKeys.*;

@Component
public class AccessTokenResolver {

    private final JwtParser jwtParser;

    public AccessTokenResolver(final @Qualifier("accessTokenParser") JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    public GetirJwtClaims resolve(final String accessToken) {
        final Claims claims = jwtParser.parseClaimsJws(accessToken).getBody();

        return GetirJwtClaims.builder()
                .memberId(UUID.fromString(claims.get(MEMBER_ID, String.class)))
                .build();
    }
}
