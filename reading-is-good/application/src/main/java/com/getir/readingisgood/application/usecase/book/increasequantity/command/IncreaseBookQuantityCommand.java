package com.getir.readingisgood.application.usecase.book.increasequantity.command;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class IncreaseBookQuantityCommand implements Command<Void> {

    private UUID merchantId;

    private UUID id;

    private int quantity;
}
