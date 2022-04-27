package com.getir.readingisgood.api.integration;

import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.faker.JwtFaker;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.framework.jwt.resolver.AccessTokenResolver;
import com.getir.framework.jwt.resolver.RefreshTokenResolver;
import com.getir.readingisgood.api.faker.MerchantControllerFaker;
import com.getir.readingisgood.api.model.merchant.request.AuthenticateMerchantWithCredentialRequest;
import com.getir.readingisgood.api.model.merchant.request.AuthenticateMerchantWithRefreshTokenRequest;
import com.getir.readingisgood.api.model.merchant.request.RegisterMerchantRequest;
import com.getir.readingisgood.api.model.merchant.response.AuthenticateMerchantWithCredentialResponse;
import com.getir.readingisgood.api.model.merchant.response.AuthenticateMerchantWithRefreshTokenResponse;
import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.MerchantRepository;
import com.getir.readingisgood.infrastructure.faker.CredentialsInfrastructureFaker;
import com.getir.readingisgood.infrastructure.faker.MerchantInfrastructureFaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MerchantIntegrationTest extends AbstractIntegrationTest {

    private static MerchantControllerFaker merchantControllerFaker;

    private static MerchantInfrastructureFaker merchantInfrastructureFaker;

    private static CredentialsInfrastructureFaker credentialsInfrastructureFaker;

    private static JwtFaker jwtFaker;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    @Autowired
    private AccessTokenResolver accessTokenResolver;

    @Autowired
    private RefreshTokenResolver refreshTokenResolver;

    private static final String MERCHANT_URL = "/merchant";

    @BeforeAll
    public static void beforeAll() {
        merchantControllerFaker = new MerchantControllerFaker();
        merchantInfrastructureFaker = new MerchantInfrastructureFaker();
        credentialsInfrastructureFaker = new CredentialsInfrastructureFaker();
        jwtFaker = new JwtFaker();
    }

    @AfterEach
    public void afterEach() {
        merchantRepository.deleteAll();
    }

    @Test
    public void should_authenticate_with_credentials() throws Exception {
        // given
        final AuthenticateMerchantWithCredentialRequest request = merchantControllerFaker.authenticateMerchantWithCredentialRequest();

        final MerchantEntity merchantEntity = merchantInfrastructureFaker.merchantEntity();
        merchantEntity.getCredentials().setEmail(request.getEmail());
        merchantEntity.getCredentials().setPassword(credentialsInfrastructureFaker.hashPassword(request.getPassword()));
        merchantRepository.save(merchantEntity);

        // when
        final ResultActions resultActions = mockMvc.perform(post(String.format("%s/authentication/credentials", MERCHANT_URL))
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final AuthenticateMerchantWithCredentialResponse response = objectMapperContext.mvcResultToResponse(AuthenticateMerchantWithCredentialResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final AuthenticationContract authenticationContract = response.getResult();
        assertThat(authenticationContract).isNotNull();

        //Access Token
        final String accessToken = authenticationContract.getAccessToken();
        assertThat(accessToken).isNotBlank();
        final GetirJwtClaims accessTokenClaims = accessTokenResolver.resolve(accessToken);
        assertThat(accessTokenClaims).isNotNull();
        assertThat(accessTokenClaims.getMemberId()).isEqualTo(merchantEntity.getId());

        //Refresh Token
        final String refreshToken = authenticationContract.getRefreshToken();
        assertThat(refreshToken).isNotBlank();
        final GetirJwtClaims refreshTokenClaims = refreshTokenResolver.resolve(refreshToken);
        assertThat(refreshTokenClaims).isNotNull();
        assertThat(refreshTokenClaims.getMemberId()).isEqualTo(merchantEntity.getId());
    }

    @Test
    public void should_authenticate_with_refresh_token() throws Exception {
        // given
        final GetirJwtClaims getirJwtClaims = jwtFaker.getirJwtClaims();
        final UUID merchantId = getirJwtClaims.getMemberId();
        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(getirJwtClaims);
        final String givenRefreshToken = getirJwtTokens.getRefreshToken();

        final MerchantEntity merchantEntity = merchantInfrastructureFaker.merchantEntity();
        merchantEntity.setId(merchantId);
        merchantRepository.save(merchantEntity);

        final AuthenticateMerchantWithRefreshTokenRequest request = merchantControllerFaker.authenticateMerchantWithRefreshTokenRequest(givenRefreshToken);

        // when
        final ResultActions resultActions = mockMvc.perform(post(String.format("%s/authentication/refresh-token", MERCHANT_URL))
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final AuthenticateMerchantWithRefreshTokenResponse response = objectMapperContext.mvcResultToResponse(AuthenticateMerchantWithRefreshTokenResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final AuthenticationContract authenticationContract = response.getResult();
        assertThat(authenticationContract).isNotNull();

        //Access Token
        final String accessToken = authenticationContract.getAccessToken();
        assertThat(accessToken).isNotBlank();
        final GetirJwtClaims accessTokenClaims = accessTokenResolver.resolve(accessToken);
        assertThat(accessTokenClaims).isNotNull();
        assertThat(accessTokenClaims.getMemberId()).isEqualTo(merchantEntity.getId());

        //Refresh Token
        final String refreshToken = authenticationContract.getRefreshToken();
        assertThat(refreshToken).isNotBlank();
        final GetirJwtClaims refreshTokenClaims = refreshTokenResolver.resolve(refreshToken);
        assertThat(refreshTokenClaims).isNotNull();
        assertThat(refreshTokenClaims.getMemberId()).isEqualTo(merchantEntity.getId());
    }

    @Test
    public void should_register() throws Exception {
        // given
        final RegisterMerchantRequest request = merchantControllerFaker.registerMerchantRequest();

        // when
        final ResultActions resultActions = mockMvc.perform(post(String.format("%s/registration", MERCHANT_URL))
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isCreated());

        final Optional<MerchantEntity> optionalMerchantEntity = merchantRepository.findByCredentialsEmail(request.getEmail());
        assertThat(optionalMerchantEntity.isPresent()).isTrue();

        final MerchantEntity merchantEntity = optionalMerchantEntity.get();
        assertThat(merchantEntity.getId()).isNotNull();
        assertThat(merchantEntity.getCredentials().getPassword()).isEqualTo(credentialsInfrastructureFaker.hashPassword(request.getPassword()));
    }
}