package com.getir.readingisgood.application.usecase.customer.register.command;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterCustomerCommand implements Command<Void> {

    private String email;

    private String password;
}
