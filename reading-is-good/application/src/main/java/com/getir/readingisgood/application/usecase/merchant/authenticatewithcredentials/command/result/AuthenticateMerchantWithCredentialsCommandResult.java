package com.getir.readingisgood.application.usecase.merchant.authenticatewithcredentials.command.result;

import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateMerchantWithCredentialsCommandResult {

    private AuthenticationContract authenticationContract;

}