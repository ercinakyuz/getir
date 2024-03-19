package com.getir.readingisgood.application.usecase.merchant.register.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.merchant.register.command.RegisterMerchantCommand;
import com.getir.readingisgood.domain.merchant.model.aggregate.builder.MerchantBuilder;
import com.getir.readingisgood.domain.merchant.model.aggregate.builder.args.BuildNewMerchantArgs;
import com.getir.readingisgood.domain.merchant.model.aggregate.Merchant;
import com.getir.readingisgood.domain.merchant.model.aggregate.unitofwork.MerchantOfWork;
import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterMerchantCommandHandler implements Command.Handler<RegisterMerchantCommand, Void> {

    private final MerchantBuilder merchantBuilder;

    private final MerchantOfWork merchantOfWork;

    @Override
    public Void handle(final RegisterMerchantCommand registerMerchantCommand) {
        final Merchant merchant = merchantBuilder.buildNew(BuildNewMerchantArgs.builder()
                .credentials(BuildCredentialsArgs.builder()
                        .email(registerMerchantCommand.getEmail())
                        .password(registerMerchantCommand.getPassword())
                        .build())
                .build());

        merchantOfWork.insert(merchant);

        return null;
    }
}
