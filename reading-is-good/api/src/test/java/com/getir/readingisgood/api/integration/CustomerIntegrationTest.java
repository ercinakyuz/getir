package com.getir.readingisgood.api.integration;

import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.faker.JwtFaker;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.framework.jwt.resolver.AccessTokenResolver;
import com.getir.framework.jwt.resolver.RefreshTokenResolver;
import com.getir.readingisgood.api.faker.CustomerControllerFaker;
import com.getir.readingisgood.api.model.customer.request.AuthenticateCustomerWithCredentialsRequest;
import com.getir.readingisgood.api.model.customer.request.AuthenticateCustomerWithRefreshTokenRequest;
import com.getir.readingisgood.api.model.customer.request.RegisterCustomerRequest;
import com.getir.readingisgood.api.model.customer.response.AuthenticateCustomerWithCredentialResponse;
import com.getir.readingisgood.api.model.customer.response.AuthenticateCustomerWithRefreshTokenResponse;
import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import com.getir.readingisgood.infrastructure.persistence.entity.CustomerEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.CustomerRepository;
import com.getir.readingisgood.infrastructure.faker.CredentialsInfrastructureFaker;
import com.getir.readingisgood.infrastructure.faker.CustomerInfrastructureFaker;
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

public class CustomerIntegrationTest extends AbstractIntegrationTest {

    private static CustomerControllerFaker customerControllerFaker;

    private static CustomerInfrastructureFaker customerInfrastructureFaker;

    private static CredentialsInfrastructureFaker credentialsInfrastructureFaker;

    private static JwtFaker jwtFaker;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    @Autowired
    private AccessTokenResolver accessTokenResolver;

    @Autowired
    private RefreshTokenResolver refreshTokenResolver;

    private static final String CUSTOMER_URL = "/customer";

    @BeforeAll
    public static void beforeAll() {
        customerControllerFaker = new CustomerControllerFaker();
        customerInfrastructureFaker = new CustomerInfrastructureFaker();
        credentialsInfrastructureFaker = new CredentialsInfrastructureFaker();
        jwtFaker = new JwtFaker();
    }

    @AfterEach
    public void afterEach() {
        customerRepository.deleteAll();
    }

    @Test
    public void should_authenticate_with_credentials() throws Exception {
        // given
        final AuthenticateCustomerWithCredentialsRequest request = customerControllerFaker.authenticateCustomerWithCredentialRequest();

        final CustomerEntity customerEntity = customerInfrastructureFaker.customerEntity();
        customerEntity.getCredentials().setEmail(request.getEmail());
        customerEntity.getCredentials().setPassword(credentialsInfrastructureFaker.hashPassword(request.getPassword()));
        customerRepository.save(customerEntity);

        // when
        final ResultActions resultActions = mockMvc.perform(post(String.format("%s/authentication/credentials", CUSTOMER_URL))
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final AuthenticateCustomerWithCredentialResponse response = objectMapperContext.mvcResultToResponse(AuthenticateCustomerWithCredentialResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final AuthenticationContract authenticationContract = response.getResult();
        assertThat(authenticationContract).isNotNull();

        //Access Token
        final String accessToken = authenticationContract.getAccessToken();
        assertThat(accessToken).isNotBlank();
        final GetirJwtClaims accessTokenClaims = accessTokenResolver.resolve(accessToken);
        assertThat(accessTokenClaims).isNotNull();
        assertThat(accessTokenClaims.getMemberId()).isEqualTo(customerEntity.getId());

        //Refresh Token
        final String refreshToken = authenticationContract.getRefreshToken();
        assertThat(refreshToken).isNotBlank();
        final GetirJwtClaims refreshTokenClaims = refreshTokenResolver.resolve(refreshToken);
        assertThat(refreshTokenClaims).isNotNull();
        assertThat(refreshTokenClaims.getMemberId()).isEqualTo(customerEntity.getId());
    }

    @Test
    public void should_authenticate_with_refresh_token() throws Exception {
        // given
        final GetirJwtClaims getirJwtClaims = jwtFaker.getirJwtClaims();
        final UUID customerId = getirJwtClaims.getMemberId();
        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(getirJwtClaims);
        final String givenRefreshToken = getirJwtTokens.getRefreshToken();

        final CustomerEntity customerEntity = customerInfrastructureFaker.customerEntity();
        customerEntity.setId(customerId);
        customerRepository.save(customerEntity);

        final AuthenticateCustomerWithRefreshTokenRequest request = customerControllerFaker.authenticateCustomerWithRefreshTokenRequest(givenRefreshToken);

        // when
        final ResultActions resultActions = mockMvc.perform(post(String.format("%s/authentication/refresh-token", CUSTOMER_URL))
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final AuthenticateCustomerWithRefreshTokenResponse response = objectMapperContext.mvcResultToResponse(AuthenticateCustomerWithRefreshTokenResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final AuthenticationContract authenticationContract = response.getResult();
        assertThat(authenticationContract).isNotNull();

        //Access Token
        final String accessToken = authenticationContract.getAccessToken();
        assertThat(accessToken).isNotBlank();
        final GetirJwtClaims accessTokenClaims = accessTokenResolver.resolve(accessToken);
        assertThat(accessTokenClaims).isNotNull();
        assertThat(accessTokenClaims.getMemberId()).isEqualTo(customerEntity.getId());

        //Refresh Token
        final String refreshToken = authenticationContract.getRefreshToken();
        assertThat(refreshToken).isNotBlank();
        final GetirJwtClaims refreshTokenClaims = refreshTokenResolver.resolve(refreshToken);
        assertThat(refreshTokenClaims).isNotNull();
        assertThat(refreshTokenClaims.getMemberId()).isEqualTo(customerEntity.getId());
    }

    @Test
    public void should_register() throws Exception {
        // given
        final RegisterCustomerRequest request = customerControllerFaker.registerCustomerRequest();

        // when
        final ResultActions resultActions = mockMvc.perform(post(String.format("%s/registration", CUSTOMER_URL))
                .contentType(APPLICATION_JSON)
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isCreated());

        final Optional<CustomerEntity> customerEntityOptional = customerRepository.findByCredentialsEmail(request.getEmail());
        assertThat(customerEntityOptional.isPresent()).isTrue();

        final CustomerEntity customerEntity = customerEntityOptional.get();
        assertThat(customerEntity.getId()).isNotNull();
        assertThat(customerEntity.getCredentials().getPassword()).isEqualTo(credentialsInfrastructureFaker.hashPassword(request.getPassword()));
    }
}