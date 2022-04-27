package com.getir.readingisgood.domain.order.model.aggregate.converter;

import com.getir.framework.domain.model.aggregate.converter.GenericAggregateConverter;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import com.getir.readingisgood.domain.order.model.aggregate.value.converter.CustomerOfOrderConverter;
import com.getir.readingisgood.domain.order.model.aggregate.value.converter.OrderItemConverter;
import com.getir.readingisgood.infrastructure.persistence.entity.order.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter extends GenericAggregateConverter<Order, OrderEntity> {

    private final CustomerOfOrderConverter customerOfOrderConverter;

    private final OrderItemConverter orderItemConverter;

    @Override
    public OrderEntity convert(final Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .customer(customerOfOrderConverter.convert(order.getCustomer()))
                .items(order.getItems().stream().map(orderItemConverter::convert).collect(Collectors.toList()))
                .build();
    }
}
