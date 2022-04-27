package com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command.handler.error;

import com.getir.framewok.application.exception.ApplicationException;
import com.getir.framework.core.model.exception.ExceptionState;

public final class AuthenticateMerchantWithRefreshTokenCommandHandlerError {

    private AuthenticateMerchantWithRefreshTokenCommandHandlerError(){}

    public static ApplicationException MERCHANT_NOT_FOUND = new ApplicationException(ExceptionState.AUTHORIZATION_FAILED, "AMWRTCHE-1", "Merchant not found");
}
