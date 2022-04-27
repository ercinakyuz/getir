package com.getir.readingisgood.application.usecase.order.getstatistics.command;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.order.getstatistics.command.result.GetStatisticsCommandResult;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetStatisticsCommand implements Command<GetStatisticsCommandResult> {

    private UUID customerId;

    private int year;
}
