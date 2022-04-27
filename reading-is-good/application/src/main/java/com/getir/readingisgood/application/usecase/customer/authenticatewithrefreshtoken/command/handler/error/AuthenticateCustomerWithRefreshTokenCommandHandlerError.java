package com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.handler.error;

import com.getir.framewok.application.exception.ApplicationException;
import com.getir.framework.core.model.exception.ExceptionState;

public final class AuthenticateCustomerWithRefreshTokenCommandHandlerError {

    private AuthenticateCustomerWithRefreshTokenCommandHandlerError(){}

    public static ApplicationException CUSTOMER_NOT_FOUND = new ApplicationException(ExceptionState.AUTHORIZATION_FAILED, "ACWRTCHE-1", "Customer not found");
}
