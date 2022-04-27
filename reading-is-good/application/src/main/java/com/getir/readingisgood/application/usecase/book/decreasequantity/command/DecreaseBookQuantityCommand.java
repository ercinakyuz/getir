package com.getir.readingisgood.application.usecase.book.decreasequantity.command;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DecreaseBookQuantityCommand implements Command<Void> {

    private UUID id;

    private int quantity;

    private UUID merchantId;
}
