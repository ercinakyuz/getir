package com.getir.readingisgood.domain.order.model.aggregate.builder.args;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Data
@Builder
public class PageableOrderBuilderArgs {

    private UUID customerId;

    private Pageable pageable;
}
