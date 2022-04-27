package com.getir.readingisgood.domain.customer.model.aggregate;

import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.aggregate.GenericAggregateRoot;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.customer.model.aggregate.dto.CreateCustomerDto;
import com.getir.readingisgood.domain.customer.model.aggregate.dto.LoadCustomerDto;
import com.getir.readingisgood.domain.customer.model.aggregate.state.CustomerState;
import com.getir.readingisgood.domain.model.value.Credentials;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static com.getir.framework.core.model.exception.ExceptionState.INVALID;
import static com.getir.readingisgood.domain.customer.error.CustomerError.INVALID_PASSWORD;
import static com.getir.readingisgood.domain.customer.model.aggregate.state.CustomerState.LOADED;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Customer extends GenericAggregateRoot<Customer, UUID, CustomerState> {

    private final Credentials credentials;

    public static Customer load(final LoadCustomerDto loadCustomerDto) {

        validateLoadCustomerDto(loadCustomerDto);

        final Customer customer = Customer.builder()
                .credentials(loadCustomerDto.getCredential())
                .build();

        customer.id = loadCustomerDto.getId();
        customer.createdAt = loadCustomerDto.getCreatedAt();
        customer.createdBy = loadCustomerDto.getCreatedBy();
        customer.modifiedAt = loadCustomerDto.getModifiedAt();
        customer.modifiedBy = loadCustomerDto.getModifiedBy();

        return customer.changeState(LOADED);
    }

    public static Customer create(final CreateCustomerDto createCustomerDto) {

        validateCreateCustomerDto(createCustomerDto);

        final Customer customer = Customer.builder()
                .credentials(createCustomerDto.getCredential())
                .build();

        customer.id = UUID.randomUUID();
        customer.createdAt = Instant.now();
        customer.createdBy = "Creator";

        return customer.changeState(LOADED);
    }

    private static void validateCreateCustomerDto(final CreateCustomerDto createCustomerDto) {
    }

    private static void validateLoadCustomerDto(final LoadCustomerDto loadCustomerDto) {

    }

    public Customer validatePassword(final String password) {
        if (!credentials.isValidPassword(password))
            throw new DomainException(INVALID, INVALID_PASSWORD);
        return this;
    }
}
