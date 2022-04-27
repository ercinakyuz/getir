package com.getir.framewok.application.exception;

import com.getir.framework.core.model.exception.CoreException;
import com.getir.framework.core.model.exception.ExceptionState;
import lombok.Getter;

@Getter
public class ApplicationException extends CoreException {

    public ApplicationException(final ExceptionState state, final String code, final String message) {
        this(state, code, message, null);
    }

    public ApplicationException(final ExceptionState state, final String code, final String message, final String userMessage) {
        super(state, code, message, userMessage);
    }
}