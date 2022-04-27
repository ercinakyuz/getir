package com.getir.framework.jwt.resolver;

import com.getir.framework.jwt.AbstractJwtTest;
import com.getir.framework.jwt.model.GetirJwtClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.UUID;

import static com.getir.framework.jwt.model.GetirJwtClaimKeys.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

public class RefreshTokenResolverTest extends AbstractJwtTest {

    @Mock
    private JwtParser jwtParser;

    private RefreshTokenResolver refreshTokenResolver;

    @BeforeEach
    public void beforeEach() {
        refreshTokenResolver = new RefreshTokenResolver(jwtParser);
    }

    @Test
    public void should_resolve() {
        //given
        final String accessToken = jwtFaker.token();
        final Jws<Claims> jwsClaims = jwtFaker.refreshTokenJwsClaims();
        final Claims claims = jwsClaims.getBody();
        final UUID fakeMemberId = UUID.fromString(claims.get(MEMBER_ID, String.class));
        //when
        when(jwtParser.parseClaimsJws(accessToken)).thenReturn(jwsClaims);

        final GetirJwtClaims getirJwtClaims = refreshTokenResolver.resolve(accessToken);

        //then
        InOrder inOrder = inOrder(jwtParser);
        inOrder.verify(jwtParser).parseClaimsJws(accessToken);
        inOrder.verifyNoMoreInteractions();

        assertThat(getirJwtClaims).isNotNull();
        assertThat(getirJwtClaims.getMemberId()).isEqualTo(fakeMemberId);

    }
}
