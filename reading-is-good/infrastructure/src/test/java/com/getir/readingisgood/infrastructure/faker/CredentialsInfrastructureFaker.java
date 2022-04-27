package com.getir.readingisgood.infrastructure.faker;

import com.getir.framework.test.faker.AbstractFaker;
import com.getir.readingisgood.infrastructure.persistence.entity.CredentialsEntity;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class CredentialsInfrastructureFaker extends AbstractFaker {

    public CredentialsEntity credentialsEntity(){
        return CredentialsEntity.builder()
                .email(email())
                .password(hashedPassword())
                .build();
    }

    public String email(){
        return faker.internet().emailAddress();
    }
    public String password(){
        return faker.code().ean8();
    }

    public String hashPassword(final String password){
        return sha256Hex(password);
    }

    public String hashedPassword(){
        return sha256Hex(password());
    }
}
