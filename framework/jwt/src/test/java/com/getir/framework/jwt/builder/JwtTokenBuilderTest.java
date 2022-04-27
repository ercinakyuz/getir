package com.getir.framework.jwt.builder;

import com.getir.framework.jwt.AbstractJwtTest;
import com.getir.framework.jwt.configuration.JwtProperties;
import com.getir.framework.jwt.configuration.TokenProperties;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import io.jsonwebtoken.JwtBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Date;
import java.util.Map;

import static com.getir.framework.jwt.model.GetirJwtClaimKeys.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

public class JwtTokenBuilderTest extends AbstractJwtTest {

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private JwtBuilder accessTokenBuilder;

    @Mock
    private JwtBuilder refreshTokenBuilder;

    @Captor
    private ArgumentCaptor<Map> accessTokenClaimsArgumentCaptor;

    @Captor
    private ArgumentCaptor<Map> refreshTokenClaimsArgumentCaptor;

    private JwtTokenBuilder jwtTokenBuilder;

    @BeforeEach
    public void beforeEach() {
        jwtTokenBuilder = new JwtTokenBuilder(jwtProperties, accessTokenBuilder, refreshTokenBuilder);
    }

    @Test
    public void should_build() {
        //given
        final GetirJwtClaims getirJwtClaims = jwtFaker.getirJwtClaims();
        final TokenProperties accessTokenProperties = jwtFaker.tokenProperties();
        final TokenProperties refreshTokenProperties = jwtFaker.tokenProperties();
        final String issuer = jwtFaker.issuer();
        final String accessToken = jwtFaker.token();
        final String refreshToken = jwtFaker.token();

        //when
        when(jwtProperties.getAccess()).thenReturn(accessTokenProperties);
        when(jwtProperties.getRefresh()).thenReturn(refreshTokenProperties);
        when(jwtProperties.getIssuer()).thenReturn(issuer);
        when(accessTokenBuilder.setIssuer(issuer)).thenReturn(accessTokenBuilder);
        when(accessTokenBuilder.setIssuedAt(any(Date.class))).thenReturn(accessTokenBuilder);
        when(accessTokenBuilder.setExpiration(any(Date.class))).thenReturn(accessTokenBuilder);
        when(accessTokenBuilder.addClaims(any(Map.class))).thenReturn(accessTokenBuilder);
        when(accessTokenBuilder.compact()).thenReturn(accessToken);
        when(refreshTokenBuilder.setIssuer(issuer)).thenReturn(refreshTokenBuilder);
        when(refreshTokenBuilder.setIssuedAt(any(Date.class))).thenReturn(refreshTokenBuilder);
        when(refreshTokenBuilder.setExpiration(any(Date.class))).thenReturn(refreshTokenBuilder);
        when(refreshTokenBuilder.addClaims(any(Map.class))).thenReturn(refreshTokenBuilder);
        when(refreshTokenBuilder.compact()).thenReturn(refreshToken);

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(getirJwtClaims);

        //then
        InOrder inOrder = inOrder(accessTokenBuilder, refreshTokenBuilder);
        inOrder.verify(accessTokenBuilder).setIssuer(issuer);
        inOrder.verify(accessTokenBuilder).addClaims(accessTokenClaimsArgumentCaptor.capture());
        inOrder.verify(refreshTokenBuilder).setIssuer(issuer);
        inOrder.verify(refreshTokenBuilder).addClaims(refreshTokenClaimsArgumentCaptor.capture());

        final Map accessTokenClaims = accessTokenClaimsArgumentCaptor.getValue();
        assertThat(accessTokenClaims).isNotNull();
        assertThat(accessTokenClaims.get(MEMBER_ID)).isEqualTo(getirJwtClaims.getMemberId());

        final Map refreshTokenClaims = refreshTokenClaimsArgumentCaptor.getValue();
        assertThat(refreshTokenClaims).isNotNull();
        assertThat(refreshTokenClaims.get(MEMBER_ID)).isEqualTo(getirJwtClaims.getMemberId());


        assertThat(getirJwtTokens).isNotNull();
        assertThat(getirJwtTokens.getAccessToken()).isEqualTo(accessToken);
        assertThat(getirJwtTokens.getRefreshToken()).isEqualTo(refreshToken);
    }
}
