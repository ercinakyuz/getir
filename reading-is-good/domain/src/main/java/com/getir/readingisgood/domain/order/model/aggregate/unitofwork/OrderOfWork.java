package com.getir.readingisgood.domain.order.model.aggregate.unitofwork;

import com.getir.framework.domain.unitofwork.AggregateOfWork;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import com.getir.readingisgood.domain.order.model.aggregate.converter.OrderConverter;
import com.getir.readingisgood.infrastructure.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderOfWork implements AggregateOfWork<Order> {

    private final OrderConverter orderConverter;

    private final OrderRepository orderRepository;

    @Override
    public Order insert(final Order order) {
        orderRepository.save(orderConverter.convertAll(order));
        return order;
    }

    @Override
    public Order update(final Order order) {
        orderRepository.save(orderConverter.convertAll(order));
        return order;
    }

    @Override
    public Order delete(final Order order) {
        orderRepository.deleteById(order.getId());
        return order;
    }
}
