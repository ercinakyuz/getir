package com.getir.readingisgood.application.usecase.merchant.register.command;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterMerchantCommand implements Command<Void> {

    private String email;

    private String password;
}
