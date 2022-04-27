package com.getir.readingisgood.infrastructure.faker;

import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MerchantInfrastructureFaker extends AbstractInfrastructureFaker {

    private final CredentialsInfrastructureFaker credentialsInfrastructureFaker;

    public MerchantInfrastructureFaker() {
        credentialsInfrastructureFaker = new CredentialsInfrastructureFaker();
    }

    public MerchantEntity merchantEntity() {
        final MerchantEntity merchantEntity = MerchantEntity.builder()
                .credentials(credentialsInfrastructureFaker.credentialsEntity())
                .build();
        loadAbstractPropertiesForCreation(merchantEntity);
        return merchantEntity;
    }

    public List<MerchantEntity> merchantEntities(final int count) {
        return IntStream.range(0,count).mapToObj(value -> merchantEntity()).collect(Collectors.toList());
    }
}

