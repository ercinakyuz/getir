package com.getir.readingisgood.application.usecase.order.get.command.result;

import com.getir.readingisgood.application.contract.OrderContract;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetOrderCommandResult {
    private OrderContract order;
}
