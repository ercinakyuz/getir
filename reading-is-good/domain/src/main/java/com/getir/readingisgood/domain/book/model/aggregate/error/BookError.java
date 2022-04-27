package com.getir.readingisgood.domain.book.model.aggregate.error;

import com.getir.framework.domain.model.error.DomainError;
import com.getir.framework.domain.model.error.dto.LoadDomainErrorDto;

public final class BookError {

    private BookError(){}

    public static final DomainError NOT_FOUND = DomainError.load(LoadDomainErrorDto.builder().code("BOOKDE-1").message("Book not found").build());
}
