package com.getir.readingisgood.application.usecase.customer.authenticatewithcredentials.command;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.customer.authenticatewithcredentials.command.result.AuthenticateCustomerWithCredentialsCommandResult;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateCustomerWithCredentialsCommand implements Command<AuthenticateCustomerWithCredentialsCommandResult> {

    private String email;

    private String password;

}