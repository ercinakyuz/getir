package com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.result.AuthenticateCustomerWithRefreshTokenCommandResult;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateCustomerWithRefreshTokenCommand implements Command<AuthenticateCustomerWithRefreshTokenCommandResult> {

    private String refreshToken;
}