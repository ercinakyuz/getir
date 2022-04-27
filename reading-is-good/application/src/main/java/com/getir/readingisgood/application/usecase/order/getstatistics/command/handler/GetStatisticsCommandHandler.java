package com.getir.readingisgood.application.usecase.order.getstatistics.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.contract.OrderStatisticsContract;
import com.getir.readingisgood.application.usecase.order.getstatistics.command.GetStatisticsCommand;
import com.getir.readingisgood.application.usecase.order.getstatistics.command.result.GetStatisticsCommandResult;
import com.getir.readingisgood.infrastructure.persistence.entity.OrderStatisticsEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.OrderStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetStatisticsCommandHandler implements Command.Handler<GetStatisticsCommand, GetStatisticsCommandResult> {

    private final OrderStatisticsRepository orderStatisticsRepository;

    @Override
    public GetStatisticsCommandResult handle(final GetStatisticsCommand getStatisticsCommand) {

        final List<OrderStatisticsEntity> orderStatisticsEntities = orderStatisticsRepository.findAllByCustomerIdAndYear(getStatisticsCommand.getCustomerId(), Year.of(getStatisticsCommand.getYear()).getValue());

        return GetStatisticsCommandResult.builder()
                .orderStatistics(orderStatisticsEntities.stream().map(orderStatisticsEntity -> OrderStatisticsContract.builder()
                        .month(orderStatisticsEntity.getMonth())
                        .totalOrderCount(orderStatisticsEntity.getTotalOrderCount())
                        .totalBookCount(orderStatisticsEntity.getTotalBookCount())
                        .totalOrderAmount(orderStatisticsEntity.getTotalOrderAmount())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
