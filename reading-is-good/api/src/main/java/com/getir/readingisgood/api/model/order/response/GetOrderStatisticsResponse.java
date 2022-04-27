package com.getir.readingisgood.api.model.order.response;

import com.getir.framework.core.model.response.AbstractResponse;
import com.getir.readingisgood.application.contract.OrderStatisticsContract;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class GetOrderStatisticsResponse extends AbstractResponse<List<OrderStatisticsContract>> {
}
