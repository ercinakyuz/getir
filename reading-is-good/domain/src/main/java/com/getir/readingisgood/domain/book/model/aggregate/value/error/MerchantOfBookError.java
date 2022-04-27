package com.getir.readingisgood.domain.book.model.aggregate.value.error;


import com.getir.framework.domain.model.error.DomainError;
import com.getir.framework.domain.model.error.dto.LoadDomainErrorDto;

public final class MerchantOfBookError {

    private MerchantOfBookError(){}

    public static final DomainError DOES_NOT_BELONG_TO_MERCHANT = DomainError.load(LoadDomainErrorDto.builder().code("MOBDE-1").message("Book does not belong to merchant").build());
}
