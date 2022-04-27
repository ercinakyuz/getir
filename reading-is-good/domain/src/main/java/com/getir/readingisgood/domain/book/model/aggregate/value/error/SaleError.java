package com.getir.readingisgood.domain.book.model.aggregate.value.error;

import com.getir.framework.domain.model.error.DomainError;
import com.getir.framework.domain.model.error.dto.LoadDomainErrorDto;

public final class SaleError {

    private SaleError(){}

    public static final DomainError INVALID_QUANTITY = DomainError.load(LoadDomainErrorDto.builder().code("SALEDE-1").message("Invalid book quantity").build());
}
