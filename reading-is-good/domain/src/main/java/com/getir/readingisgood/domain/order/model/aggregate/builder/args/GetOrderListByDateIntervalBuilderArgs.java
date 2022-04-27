package com.getir.readingisgood.domain.order.model.aggregate.builder.args;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class GetOrderListByDateIntervalBuilderArgs {

    private UUID customerId;

    private Instant start;

    private Instant end;
}
