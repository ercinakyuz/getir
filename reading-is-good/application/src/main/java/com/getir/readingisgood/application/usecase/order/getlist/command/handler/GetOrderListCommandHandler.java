package com.getir.readingisgood.application.usecase.order.getlist.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.contract.mapper.OrderContractMapper;
import com.getir.readingisgood.application.usecase.order.getlist.command.GetOrderListCommand;
import com.getir.readingisgood.application.usecase.order.getlist.command.result.GetOrderListCommandResult;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import com.getir.readingisgood.domain.order.model.aggregate.builder.OrderBuilder;
import com.getir.readingisgood.domain.order.model.aggregate.builder.args.PageableOrderBuilderArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderListCommandHandler implements Command.Handler<GetOrderListCommand, GetOrderListCommandResult> {

    private final OrderBuilder orderBuilder;

    private final OrderContractMapper orderContractMapper;

    @Override
    public GetOrderListCommandResult handle(final GetOrderListCommand getOrderListCommand) {

        final Page<Order> pageableOrder = orderBuilder.buildPageable(PageableOrderBuilderArgs.builder()
                .customerId(getOrderListCommand.getCustomerId())
                .pageable(getOrderListCommand.getPageable())
                .build());

        return GetOrderListCommandResult.builder()
                .pagedOrders(orderContractMapper.mapPageable(pageableOrder))
                .build();
    }
}
