package com.getir.readingisgood.domain.customer.model.aggregate.converter;

import com.getir.framework.domain.model.aggregate.converter.GenericAggregateConverter;
import com.getir.readingisgood.domain.customer.model.aggregate.Customer;
import com.getir.readingisgood.domain.model.value.converter.CredentialsConverter;
import com.getir.readingisgood.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerConverter extends GenericAggregateConverter<Customer, CustomerEntity> {

    private final CredentialsConverter credentialsConverter;

    @Override
    public CustomerEntity convert(final Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .credentials(credentialsConverter.convert(customer.getCredentials()))
                .build();
    }
}

