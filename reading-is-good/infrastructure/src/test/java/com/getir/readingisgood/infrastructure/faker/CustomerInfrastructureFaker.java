package com.getir.readingisgood.infrastructure.faker;

import com.getir.readingisgood.infrastructure.persistence.entity.CustomerEntity;

public class CustomerInfrastructureFaker extends AbstractInfrastructureFaker {

    private final CredentialsInfrastructureFaker credentialsInfrastructureFaker;

    public CustomerInfrastructureFaker() {
        credentialsInfrastructureFaker = new CredentialsInfrastructureFaker();
    }

    public CustomerEntity customerEntity() {
        final CustomerEntity customerEntity = CustomerEntity.builder()
                .credentials(credentialsInfrastructureFaker.credentialsEntity())
                .build();
        loadAbstractPropertiesForCreation(customerEntity);
        return customerEntity;
    }
}

