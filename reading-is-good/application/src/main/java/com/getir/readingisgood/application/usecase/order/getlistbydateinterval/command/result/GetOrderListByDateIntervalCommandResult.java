package com.getir.readingisgood.application.usecase.order.getlistbydateinterval.command.result;

import com.getir.readingisgood.application.contract.OrderContract;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class GetOrderListByDateIntervalCommandResult {

    private Page<OrderContract> orders;
}
