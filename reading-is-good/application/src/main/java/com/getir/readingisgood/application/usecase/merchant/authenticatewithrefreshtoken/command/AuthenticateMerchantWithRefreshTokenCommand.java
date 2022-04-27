package com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command;

import com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command.result.AuthenticateMerchantWithRefreshTokenCommandResult;
import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateMerchantWithRefreshTokenCommand implements Command<AuthenticateMerchantWithRefreshTokenCommandResult> {

    private String refreshToken;
}