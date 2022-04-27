package com.getir.readingisgood.application.usecase.order.getlistbydateinterval.command;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.order.getlistbydateinterval.command.result.GetOrderListByDateIntervalCommandResult;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class GetOrderListByDateIntervalCommand implements Command<GetOrderListByDateIntervalCommandResult> {

    private UUID customerId;

    private Pageable pageable;

    private Instant start;

    private Instant end;

}
