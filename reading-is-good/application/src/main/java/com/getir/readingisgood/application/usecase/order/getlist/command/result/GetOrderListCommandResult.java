package com.getir.readingisgood.application.usecase.order.getlist.command.result;

import com.getir.readingisgood.application.contract.OrderContract;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class GetOrderListCommandResult {
    private Page<OrderContract> pagedOrders;
}
