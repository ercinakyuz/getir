package com.getir.readingisgood.domain.order.model.aggregate.builder.args;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PageableOrderByDateIntervalBuilderArgs {

    private UUID customerId;

    private Pageable pageable;

    private Instant start;

    private Instant end;
}
