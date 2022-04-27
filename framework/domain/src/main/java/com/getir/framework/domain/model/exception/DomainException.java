package com.getir.framework.domain.model.exception;


import com.getir.framework.core.model.exception.CoreException;
import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.error.DomainError;
import lombok.Getter;

@Getter
public class DomainException extends CoreException {

    private DomainError domainError;

    public DomainException(final ExceptionState state, final DomainError domainError) {
        this(state, domainError.getCode(), domainError.getMessage(), null);
        this.domainError = domainError;
    }

    public DomainException(final ExceptionState state, final String code, final String message) {
        this(state, code, message, null);
    }

    public DomainException(final ExceptionState state, final String code, final String message, final String userMessage) {
        super(state, code, message, userMessage);
    }
}
