package com.getir.readingisgood.domain.order.model.aggregate.error;

import com.getir.framework.domain.model.error.DomainError;
import com.getir.framework.domain.model.error.dto.LoadDomainErrorDto;

public final class OrderError {

    private OrderError(){}

    public static final DomainError NOT_FOUND = DomainError.load(LoadDomainErrorDto.builder().code("ORDE-1").message("Order not found").build());
}
