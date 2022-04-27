package com.getir.readingisgood.application.contract.mapper;


import com.getir.readingisgood.application.contract.OrderItemContract;
import com.getir.readingisgood.domain.order.model.aggregate.value.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemContractMapper {

    private final BookOfOrderItemContractMapper bookOfOrderItemContractMapper;

    public OrderItemContract map(final OrderItem item) {
        return OrderItemContract.builder()
                .id(item.getId())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .book(bookOfOrderItemContractMapper.map(item.getBook()))
                .build();
    }

    public List<OrderItemContract> mapList(final List<OrderItem> items) {
        return items.stream().map(this::map).collect(Collectors.toList());
    }
}
