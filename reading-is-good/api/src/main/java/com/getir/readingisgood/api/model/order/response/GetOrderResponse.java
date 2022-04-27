package com.getir.readingisgood.api.model.order.response;

import com.getir.framework.core.model.response.AbstractResponse;
import com.getir.readingisgood.application.contract.OrderContract;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class GetOrderResponse extends AbstractResponse<OrderContract> {
}
