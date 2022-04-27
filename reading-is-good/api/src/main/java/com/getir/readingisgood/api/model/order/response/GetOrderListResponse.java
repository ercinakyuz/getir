package com.getir.readingisgood.api.model.order.response;


import com.getir.framework.core.model.response.AbstractResponse;
import com.getir.framework.data.pagination.impl.CustomPageImpl;
import com.getir.readingisgood.application.contract.OrderContract;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class GetOrderListResponse extends AbstractResponse<CustomPageImpl<OrderContract>> {
}
