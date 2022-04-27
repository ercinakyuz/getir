package com.getir.readingisgood.domain.order.model.aggregate.value.builder;

import com.getir.readingisgood.domain.order.model.aggregate.value.BookOfOrderItem;
import com.getir.readingisgood.domain.order.model.aggregate.value.OrderItem;
import com.getir.readingisgood.domain.order.model.aggregate.value.builder.args.NewOrderItemBuilderArgs;
import com.getir.readingisgood.domain.order.model.aggregate.value.dto.CreateOrderItemDto;
import com.getir.readingisgood.domain.order.model.aggregate.value.dto.LoadOrderItemDto;
import com.getir.readingisgood.infrastructure.persistence.entity.order.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemBuilder {

    private final BookOfOrderItemBuilder bookOfOrderItemBuilder;

    public OrderItem buildFromEntity(final OrderItemEntity orderItemEntity){
        return OrderItem.load(LoadOrderItemDto.builder()
                .id(orderItemEntity.getId())
                .book(bookOfOrderItemBuilder.buildFromEntity(orderItemEntity.getBook()))
                .quantity(orderItemEntity.getQuantity())
                .build());
    }

    public OrderItem buildNew(final NewOrderItemBuilderArgs args){
        final BookOfOrderItem bookOfOrderItem = bookOfOrderItemBuilder.buildNew(args.getBookId());
        return OrderItem.create(CreateOrderItemDto.builder()
                .book(bookOfOrderItem)
                .quantity(args.getQuantity())
                .build());
    }
}
