package com.getir.readingisgood.domain.order.model.aggregate.value;

import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.order.model.aggregate.value.dto.LoadCustomerOfOrderDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

import static com.getir.readingisgood.domain.order.model.aggregate.value.error.CustomerOfOrderError.*;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class CustomerOfOrder {

    private UUID id;

    private String email;

    public static CustomerOfOrder load(final LoadCustomerOfOrderDto loadDto) {
        validateLoadDto(loadDto);
        return CustomerOfOrder.builder()
                .id(loadDto.getId())
                .email(loadDto.getEmail())
                .build();
    }

    private static void validateLoadDto(final LoadCustomerOfOrderDto loadDto) {

    }

    public CustomerOfOrder validate(final UUID customerId) {
        if (!id.equals(customerId))
            throw new DomainException(ExceptionState.UNPROCESSABLE, DOES_NOT_BELONG_TO_CUSTOMER);
        return this;
    }
}
