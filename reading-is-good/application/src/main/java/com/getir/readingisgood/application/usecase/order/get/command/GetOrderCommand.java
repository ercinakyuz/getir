package com.getir.readingisgood.application.usecase.order.get.command;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.order.get.command.result.GetOrderCommandResult;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetOrderCommand implements Command<GetOrderCommandResult> {

    private UUID id;

    private UUID customerId;
}

