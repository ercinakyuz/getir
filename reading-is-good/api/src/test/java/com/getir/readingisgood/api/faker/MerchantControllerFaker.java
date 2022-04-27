package com.getir.readingisgood.api.faker;

import com.getir.framework.jwt.faker.JwtFaker;
import com.getir.framework.test.faker.AbstractFaker;
import com.getir.readingisgood.api.model.merchant.request.AuthenticateMerchantWithCredentialRequest;
import com.getir.readingisgood.api.model.merchant.request.AuthenticateMerchantWithRefreshTokenRequest;
import com.getir.readingisgood.api.model.merchant.request.RegisterMerchantRequest;
import com.getir.readingisgood.infrastructure.faker.CredentialsInfrastructureFaker;

public class MerchantControllerFaker extends AbstractFaker {

    private final JwtFaker jwtFaker;

    private final CredentialsInfrastructureFaker credentialsInfrastructureFaker;

    public MerchantControllerFaker() {
        jwtFaker = new JwtFaker();
        credentialsInfrastructureFaker = new CredentialsInfrastructureFaker();
    }

    public AuthenticateMerchantWithCredentialRequest authenticateMerchantWithCredentialRequest() {
        return AuthenticateMerchantWithCredentialRequest.builder()
                .email(faker.internet().emailAddress())
                .password(faker.code().ean8())
                .build();
    }

    public AuthenticateMerchantWithRefreshTokenRequest authenticateMerchantWithRefreshTokenRequest() {
        return AuthenticateMerchantWithRefreshTokenRequest.builder()
                .refreshToken(jwtFaker.token())
                .build();
    }

    public AuthenticateMerchantWithRefreshTokenRequest authenticateMerchantWithRefreshTokenRequest(final String refreshToken) {
        return AuthenticateMerchantWithRefreshTokenRequest.builder()
                .refreshToken(refreshToken)
                .build();
    }

    public RegisterMerchantRequest registerMerchantRequest() {
        return RegisterMerchantRequest.builder()
                .email(credentialsInfrastructureFaker.email())
                .password(credentialsInfrastructureFaker.password())
                .build();
    }
}
