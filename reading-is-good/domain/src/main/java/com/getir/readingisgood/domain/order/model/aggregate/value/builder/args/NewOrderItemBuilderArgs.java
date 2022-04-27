package com.getir.readingisgood.domain.order.model.aggregate.value.builder.args;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NewOrderItemBuilderArgs {

    private UUID bookId;

    private int quantity;
}
