package com.getir.readingisgood.domain.order.model.aggregate.builder.args;

import com.getir.readingisgood.domain.order.model.aggregate.value.builder.args.NewOrderItemBuilderArgs;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class NewOrderBuilderArgs {

    private UUID customerId;

    private List<NewOrderItemBuilderArgs> items;

}
