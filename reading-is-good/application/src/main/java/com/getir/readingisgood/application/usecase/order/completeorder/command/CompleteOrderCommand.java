package com.getir.readingisgood.application.usecase.order.completeorder.command;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CompleteOrderCommand implements Command<UUID> {

    private UUID customerId;

    private List<CompleteOrderItemCommand> items;
}
