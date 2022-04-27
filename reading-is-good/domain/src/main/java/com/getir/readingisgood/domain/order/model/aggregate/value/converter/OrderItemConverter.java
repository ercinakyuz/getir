package com.getir.readingisgood.domain.order.model.aggregate.value.converter;

import com.getir.readingisgood.domain.order.model.aggregate.value.OrderItem;
import com.getir.readingisgood.infrastructure.persistence.entity.order.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemConverter implements Converter<OrderItem, OrderItemEntity> {

    private final BookOfOrderItemConverter bookOfOrderItemConverter;

    @Override
    public OrderItemEntity convert(final OrderItem orderItem) {
        return OrderItemEntity.builder()
                .id(orderItem.getId())
                .book(bookOfOrderItemConverter.convert(orderItem.getBook()))
                .quantity(orderItem.getQuantity())
                .build();
    }
}
