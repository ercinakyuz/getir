package com.getir.readingisgood.application.usecase.statistics.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.statistics.command.SyncOrderStatisticsCommand;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import com.getir.readingisgood.domain.order.model.aggregate.builder.OrderBuilder;
import com.getir.readingisgood.infrastructure.persistence.entity.OrderStatisticsEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.OrderStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;

import static com.getir.readingisgood.infrastructure.DateUtil.toSystemLocalDateTime;
import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
public class SyncOrderStatisticsCommandHandler implements Command.Handler<SyncOrderStatisticsCommand, Void> {

    private final OrderBuilder orderBuilder;
    private final OrderStatisticsRepository orderStatisticsRepository;

    @Override
    public Void handle(final SyncOrderStatisticsCommand applyOnStatisticsCommand) {

        final Order order = orderBuilder.build(applyOnStatisticsCommand.getOrderId());
        final UUID customerId = order.getCustomer().getId();

        final LocalDateTime orderCreatedAtAsLocalDateTime = toSystemLocalDateTime(order.getCreatedAt());
        final int year = orderCreatedAtAsLocalDateTime.getYear();
        final Month month = orderCreatedAtAsLocalDateTime.getMonth();

        final Optional<OrderStatisticsEntity> optionalOrderStatisticsEntity = orderStatisticsRepository.findByCustomerIdAndYearAndMonth(customerId, year, month);

        final OrderStatisticsEntity orderStatisticsEntity = optionalOrderStatisticsEntity.orElseGet(() -> OrderStatisticsEntity.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .year(year)
                .month(month)
                .totalOrderAmount(ZERO)
                .build());

        orderStatisticsEntity.setTotalOrderCount(orderStatisticsEntity.getTotalOrderCount() + 1);
        orderStatisticsEntity.setTotalBookCount(orderStatisticsEntity.getTotalBookCount() + order.getQuantity());
        orderStatisticsEntity.setTotalOrderAmount(orderStatisticsEntity.getTotalOrderAmount().add(order.getPrice()));

        orderStatisticsRepository.save(orderStatisticsEntity);

        return null;
    }
}
