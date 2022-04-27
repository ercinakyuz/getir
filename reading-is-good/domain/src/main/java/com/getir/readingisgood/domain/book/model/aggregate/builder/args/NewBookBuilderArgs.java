package com.getir.readingisgood.domain.book.model.aggregate.builder.args;

import com.getir.readingisgood.domain.book.model.aggregate.value.builder.args.SaleBuilderArgs;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class NewBookBuilderArgs {

    private UUID merchantId;

    private String name;

    private String author;

    private SaleBuilderArgs sale;

}
