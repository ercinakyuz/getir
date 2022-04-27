package com.getir.readingisgood.application.usecase.order.get.command;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.contract.mapper.OrderContractMapper;
import com.getir.readingisgood.application.usecase.order.get.command.result.GetOrderCommandResult;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import com.getir.readingisgood.domain.order.model.aggregate.builder.OrderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderCommandHandler implements Command.Handler<GetOrderCommand, GetOrderCommandResult> {

    private final OrderBuilder orderBuilder;

    private final OrderContractMapper orderContractMapper;

    @Override
    public GetOrderCommandResult handle(final GetOrderCommand getOrderCommand) {
        final Order order = orderBuilder.build(getOrderCommand.getId()).validateOwnership(getOrderCommand.getCustomerId());
        return GetOrderCommandResult.builder()
                .order(orderContractMapper.map(order))
                .build();
    }
}
