package com.getir.readingisgood.application.usecase.book.create.command;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CreateBookCommand implements Command<UUID> {

    private UUID merchantId;
    private String name;
    private String author;
    private BigDecimal price;
    private int quantity;
}
