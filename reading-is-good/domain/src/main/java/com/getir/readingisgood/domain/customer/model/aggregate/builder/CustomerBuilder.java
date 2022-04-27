package com.getir.readingisgood.domain.customer.model.aggregate.builder;

import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.aggregate.builder.AbstractAggregateBuilder;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.customer.model.aggregate.Customer;
import com.getir.readingisgood.domain.customer.model.aggregate.builder.args.BuildCustomerWithCredentialsArgs;
import com.getir.readingisgood.domain.customer.model.aggregate.builder.args.BuildNewCustomerArgs;
import com.getir.readingisgood.domain.customer.model.aggregate.dto.CreateCustomerDto;
import com.getir.readingisgood.domain.customer.model.aggregate.dto.LoadCustomerDto;
import com.getir.readingisgood.domain.model.value.builder.CredentialsBuilder;
import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import com.getir.readingisgood.infrastructure.persistence.entity.CustomerEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.getir.framework.core.model.exception.ExceptionState.AUTHORIZATION_FAILED;
import static com.getir.readingisgood.domain.customer.error.CustomerError.*;

@Component
@RequiredArgsConstructor
public class CustomerBuilder extends AbstractAggregateBuilder {

    private final CustomerRepository customerRepository;
    private final CredentialsBuilder credentialBuilder;

    public Optional<Customer> buildOptional(final UUID id) {
        return customerRepository.findById(id).map(this::buildFromEntity);
    }

    public Customer build(final UUID id) {
        return buildOptional(id).orElseThrow(() -> new DomainException(AUTHORIZATION_FAILED, NOT_FOUND));
    }

    public Customer buildWithCredentials(final BuildCustomerWithCredentialsArgs args) {
        final BuildCredentialsArgs buildCredentialsArgs = args.getCredentials();
        final CustomerEntity customerEntity = customerRepository.findByCredentialsEmail(buildCredentialsArgs.getEmail())
                .orElseThrow(() -> new DomainException(ExceptionState.INVALID, INVALID_EMAIL));
        return buildFromEntity(customerEntity).validatePassword(buildCredentialsArgs.getPassword());
    }

    public Customer buildNew(final BuildNewCustomerArgs args) {
        return Customer.create(CreateCustomerDto.builder()
                .credential(credentialBuilder.buildNewCredentials(args.getCredentials()))
                .build());
    }

    private Customer buildFromEntity(final CustomerEntity customerEntity){
        final LoadCustomerDto loadCustomerDto = LoadCustomerDto.builder()
                .id(customerEntity.getId())
                .credential(credentialBuilder.buildCredentials(customerEntity.getCredentials()))
                .build();
        loadAbstractProperties(loadCustomerDto,customerEntity);
        return Customer.load(loadCustomerDto);
    }
}
