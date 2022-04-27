package com.getir.readingisgood.domain.book.model.aggregate.value.builder.args;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SaleBuilderArgs {

    private BigDecimal price;

    private int quantity;
}
