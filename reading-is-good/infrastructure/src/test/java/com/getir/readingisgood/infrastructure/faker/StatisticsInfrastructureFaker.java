package com.getir.readingisgood.infrastructure.faker;

import com.getir.readingisgood.infrastructure.persistence.entity.OrderStatisticsEntity;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StatisticsInfrastructureFaker extends AbstractInfrastructureFaker {

    public OrderStatisticsEntity orderStatisticsEntity() {
        final OrderStatisticsEntity orderStatisticsEntity = OrderStatisticsEntity.builder()
                .month(month())
                .year(year())
                .customerId(id())
                .totalOrderAmount(price())
                .totalBookCount(totalBookCount())
                .totalOrderCount(totalOrderCount())
                .build();
        loadAbstractPropertiesForCreation(orderStatisticsEntity);
        return orderStatisticsEntity;
    }

    public OrderStatisticsEntity orderStatisticsEntity(final UUID customerId, final int year, final Month month) {
        final OrderStatisticsEntity orderStatisticsEntity = OrderStatisticsEntity.builder()
                .month(month)
                .year(year)
                .customerId(customerId)
                .totalOrderAmount(price())
                .totalBookCount(totalBookCount())
                .totalOrderCount(totalOrderCount())
                .build();
        loadAbstractPropertiesForCreation(orderStatisticsEntity);
        return orderStatisticsEntity;
    }

    public List<OrderStatisticsEntity> orderStatisticsEntities(final UUID customerId, final int year) {
        return Arrays.stream(Month.values()).map(month -> orderStatisticsEntity(customerId, year, month)).collect(Collectors.toList());
    }

    public int totalBookCount() {
        return faker.number().numberBetween(11, 100);
    }

    public int totalOrderCount() {
        return faker.number().numberBetween(1, 10);
    }

    public int year() {
        return Year.now().getValue();
    }

    public Month month() {
        return faker.options().option(Month.class);
    }
}
