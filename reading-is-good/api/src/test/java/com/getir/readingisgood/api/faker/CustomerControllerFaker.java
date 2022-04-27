package com.getir.readingisgood.api.faker;

import com.getir.framework.jwt.faker.JwtFaker;
import com.getir.framework.test.faker.AbstractFaker;
import com.getir.readingisgood.api.model.customer.request.AuthenticateCustomerWithCredentialsRequest;
import com.getir.readingisgood.api.model.customer.request.AuthenticateCustomerWithRefreshTokenRequest;
import com.getir.readingisgood.api.model.customer.request.RegisterCustomerRequest;
import com.getir.readingisgood.infrastructure.faker.CredentialsInfrastructureFaker;

public class CustomerControllerFaker extends AbstractFaker {

    private final JwtFaker jwtFaker;

    private final CredentialsInfrastructureFaker credentialsInfrastructureFaker;

    public CustomerControllerFaker() {
        jwtFaker = new JwtFaker();
        credentialsInfrastructureFaker = new CredentialsInfrastructureFaker();
    }

    public AuthenticateCustomerWithCredentialsRequest authenticateCustomerWithCredentialRequest() {
        return AuthenticateCustomerWithCredentialsRequest.builder()
                .email(faker.internet().emailAddress())
                .password(faker.code().ean8())
                .build();
    }

    public AuthenticateCustomerWithRefreshTokenRequest authenticateCustomerWithRefreshTokenRequest() {
        return AuthenticateCustomerWithRefreshTokenRequest.builder()
                .refreshToken(jwtFaker.token())
                .build();
    }

    public AuthenticateCustomerWithRefreshTokenRequest authenticateCustomerWithRefreshTokenRequest(final String refreshToken) {
        return AuthenticateCustomerWithRefreshTokenRequest.builder()
                .refreshToken(refreshToken)
                .build();
    }

    public RegisterCustomerRequest registerCustomerRequest() {
        return RegisterCustomerRequest.builder()
                .email(credentialsInfrastructureFaker.email())
                .password(credentialsInfrastructureFaker.password())
                .build();
    }
}
