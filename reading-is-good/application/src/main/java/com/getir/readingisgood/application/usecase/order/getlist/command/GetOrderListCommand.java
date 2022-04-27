package com.getir.readingisgood.application.usecase.order.getlist.command;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.order.getlist.command.result.GetOrderListCommandResult;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Data
@Builder
public class GetOrderListCommand implements Command<GetOrderListCommandResult> {

    private UUID customerId;

    private Pageable pageable;
}

