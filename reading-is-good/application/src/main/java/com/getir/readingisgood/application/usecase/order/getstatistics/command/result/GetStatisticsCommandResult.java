package com.getir.readingisgood.application.usecase.order.getstatistics.command.result;

import com.getir.readingisgood.application.contract.OrderStatisticsContract;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetStatisticsCommandResult {

    private List<OrderStatisticsContract> orderStatistics;
}
