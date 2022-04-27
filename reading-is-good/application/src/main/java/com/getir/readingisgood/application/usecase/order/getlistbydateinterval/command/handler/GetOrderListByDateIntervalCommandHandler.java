package com.getir.readingisgood.application.usecase.order.getlistbydateinterval.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.contract.mapper.OrderContractMapper;
import com.getir.readingisgood.application.usecase.order.getlistbydateinterval.command.GetOrderListByDateIntervalCommand;
import com.getir.readingisgood.application.usecase.order.getlistbydateinterval.command.result.GetOrderListByDateIntervalCommandResult;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import com.getir.readingisgood.domain.order.model.aggregate.builder.OrderBuilder;
import com.getir.readingisgood.domain.order.model.aggregate.builder.args.PageableOrderByDateIntervalBuilderArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderListByDateIntervalCommandHandler implements Command.Handler<GetOrderListByDateIntervalCommand, GetOrderListByDateIntervalCommandResult> {

    private final OrderBuilder orderBuilder;

    private final OrderContractMapper orderContractMapper;

    @Override
    public GetOrderListByDateIntervalCommandResult handle(final GetOrderListByDateIntervalCommand getOrderListByDateIntervalCommand) {
        final Page<Order> pageableOrder = orderBuilder.buildPageableByDateInterval(PageableOrderByDateIntervalBuilderArgs.builder()
                .customerId(getOrderListByDateIntervalCommand.getCustomerId())
                .start(getOrderListByDateIntervalCommand.getStart())
                .end(getOrderListByDateIntervalCommand.getEnd())
                .build());
        return GetOrderListByDateIntervalCommandResult.builder()
                .orders(orderContractMapper.mapPageable(pageableOrder))
                .build();
    }
}
