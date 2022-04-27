package com.getir.readingisgood.domain.customer.error;


import com.getir.framework.domain.model.error.DomainError;
import com.getir.framework.domain.model.error.dto.LoadDomainErrorDto;

public final class CustomerError {

    private CustomerError(){}

    public static final DomainError NOT_FOUND = DomainError.load(LoadDomainErrorDto.builder().code("CUSDE-1").message("Customer not found").build());

    public static final DomainError INVALID_EMAIL = DomainError.load(LoadDomainErrorDto.builder().code("CUSDE-2").message("Invalid customer email").build());

    public static final DomainError INVALID_PASSWORD = DomainError.load(LoadDomainErrorDto.builder().code("CUSDE-3").message("Invalid customer password").build());
}
