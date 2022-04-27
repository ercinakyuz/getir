package com.getir.readingisgood.application.contract.mapper;

import com.getir.readingisgood.application.contract.OrderContract;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderContractMapper {

    private final CustomerOfOrderContractMapper customerOfOrderContractMapper;

    private final OrderItemContractMapper orderItemContractMapper;

    public OrderContract map(final Order order) {
        return OrderContract.builder()
                .id(order.getId())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .customer(customerOfOrderContractMapper.map(order.getCustomer()))
                .items(orderItemContractMapper.mapList(order.getItems()))
                .build();
    }

    public Page<OrderContract> mapPageable(final Page<Order> pageableOrder) {
        return pageableOrder.map(this::map);
    }
}
