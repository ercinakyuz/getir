package com.getir.readingisgood.application.usecase.customer.authenticatewithcredentials.command.result;

import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateCustomerWithCredentialsCommandResult {

    private AuthenticationContract authenticationContract;

}