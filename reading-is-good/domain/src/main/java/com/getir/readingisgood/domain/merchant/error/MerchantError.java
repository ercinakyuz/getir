package com.getir.readingisgood.domain.merchant.error;

import com.getir.framework.domain.model.error.DomainError;
import com.getir.framework.domain.model.error.dto.LoadDomainErrorDto;

public final class MerchantError {

    private MerchantError(){}

    public static final DomainError NOT_FOUND = DomainError.load(LoadDomainErrorDto.builder().code("MERDE-1").message("Merchant not found").build());

    public static final DomainError INVALID_EMAIL = DomainError.load(LoadDomainErrorDto.builder().code("MERDE-2").message("Invalid merchant email").build());

    public static final DomainError INVALID_PASSWORD = DomainError.load(LoadDomainErrorDto.builder().code("MERDE-3").message("Invalid merchant password").build());

}
