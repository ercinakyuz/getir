package com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.result;

import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateCustomerWithRefreshTokenCommandResult {

    private AuthenticationContract authenticationContract;

}