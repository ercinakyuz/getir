package com.getir.readingisgood.application.usecase.customer.register.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.customer.register.command.RegisterCustomerCommand;
import com.getir.readingisgood.domain.customer.model.aggregate.builder.CustomerBuilder;
import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import com.getir.readingisgood.domain.customer.model.aggregate.builder.args.BuildNewCustomerArgs;
import com.getir.readingisgood.domain.customer.model.aggregate.Customer;
import com.getir.readingisgood.domain.customer.model.aggregate.unitofwork.CustomerOfWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterCustomerCommandHandler implements Command.Handler<RegisterCustomerCommand, Void> {

    private final CustomerBuilder customerBuilder;

    private final CustomerOfWork customerOfWork;

    @Override
    public Void handle(final RegisterCustomerCommand registerCustomerCommand) {
        final Customer customer = customerBuilder.buildNew(BuildNewCustomerArgs.builder()
                .credentials(BuildCredentialsArgs.builder()
                        .email(registerCustomerCommand.getEmail())
                        .password(registerCustomerCommand.getPassword())
                        .build())
                .build());

        customerOfWork.insert(customer);

        return null;
    }
}
