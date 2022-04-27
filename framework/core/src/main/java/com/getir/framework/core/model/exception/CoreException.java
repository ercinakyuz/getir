package com.getir.framework.core.model.exception;

import lombok.Getter;


@Getter
public abstract class CoreException extends RuntimeException {

    private ExceptionState state;

    private String code;

    private String userMessage;

    protected CoreException(final ExceptionState state, final String code, final String message) {
        this(state, code, message, null);
    }

    protected CoreException(final ExceptionState state, final String code, final String message, final String userMessage) {
        super(message);
        this.state = state;
        this.code = code;
        this.userMessage = userMessage;
    }
}
