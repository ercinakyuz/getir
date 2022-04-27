package com.getir.readingisgood.application.usecase.order.completeorder.command.handler.error;

import com.getir.framewok.application.exception.ApplicationException;
import com.getir.framework.core.model.exception.ExceptionState;

public final class CompleteOrderCommandHandlerError {

    private CompleteOrderCommandHandlerError(){}

    public static ApplicationException INSUFFICIENT_BOOK_STOCK = new ApplicationException(ExceptionState.UNPROCESSABLE, "COCHE-1", "Insufficient book stock");
}
