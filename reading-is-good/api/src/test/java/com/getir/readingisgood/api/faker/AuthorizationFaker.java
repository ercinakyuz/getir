package com.getir.readingisgood.api.faker;

import com.getir.framework.test.faker.AbstractFaker;

public class AuthorizationFaker extends AbstractFaker {

    public final static String AUTHORIZATION_HEADER_KEY = "Authorization";

    public String authorizationToken(final String accessToken){
        return String.format("Bearer %s", accessToken);
    }
}
