package com.getir.readingisgood.application.usecase.merchant.authenticatewithcredentials.command;

import com.getir.readingisgood.application.usecase.merchant.authenticatewithcredentials.command.result.AuthenticateMerchantWithCredentialsCommandResult;
import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateMerchantWithCredentialsCommand implements Command<AuthenticateMerchantWithCredentialsCommandResult> {

    private String email;

    private String password;

}