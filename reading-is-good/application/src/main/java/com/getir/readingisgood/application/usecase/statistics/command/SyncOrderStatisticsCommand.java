package com.getir.readingisgood.application.usecase.statistics.command;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SyncOrderStatisticsCommand implements Command<Void> {

    private UUID orderId;

}


