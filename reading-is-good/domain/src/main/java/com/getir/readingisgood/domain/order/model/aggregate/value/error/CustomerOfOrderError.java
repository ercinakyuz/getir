package com.getir.readingisgood.domain.order.model.aggregate.value.error;

import com.getir.framework.domain.model.error.DomainError;
import com.getir.framework.domain.model.error.dto.LoadDomainErrorDto;

public final class CustomerOfOrderError {

    private CustomerOfOrderError(){}

    public static final DomainError DOES_NOT_BELONG_TO_CUSTOMER = DomainError.load(LoadDomainErrorDto.builder().code("COO-1").message("Order does not belong to customer").build());
}
