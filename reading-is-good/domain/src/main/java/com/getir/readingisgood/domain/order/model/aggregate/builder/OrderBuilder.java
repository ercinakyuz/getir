package com.getir.readingisgood.domain.order.model.aggregate.builder;

import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.aggregate.builder.AbstractAggregateBuilder;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import com.getir.readingisgood.domain.order.model.aggregate.builder.args.NewOrderBuilderArgs;
import com.getir.readingisgood.domain.order.model.aggregate.builder.args.PageableOrderBuilderArgs;
import com.getir.readingisgood.domain.order.model.aggregate.builder.args.PageableOrderByDateIntervalBuilderArgs;
import com.getir.readingisgood.domain.order.model.aggregate.dto.CreateOrderDto;
import com.getir.readingisgood.domain.order.model.aggregate.dto.LoadOrderDto;
import com.getir.readingisgood.domain.order.model.aggregate.error.OrderError;
import com.getir.readingisgood.domain.order.model.aggregate.value.builder.CustomerOfOrderBuilder;
import com.getir.readingisgood.domain.order.model.aggregate.value.builder.OrderItemBuilder;
import com.getir.readingisgood.domain.order.model.aggregate.value.builder.args.NewOrderItemBuilderArgs;
import com.getir.readingisgood.infrastructure.persistence.entity.order.OrderEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderBuilder extends AbstractAggregateBuilder {

    private final CustomerOfOrderBuilder customerOfOrderBuilder;

    private final OrderItemBuilder orderItemBuilder;

    private final OrderRepository orderRepository;

    public Order buildNew(final NewOrderBuilderArgs args) {
        return Order.create(CreateOrderDto.builder()
                .customer(customerOfOrderBuilder.buildNew(args.getCustomerId()))
                .items(args.getItems().stream().map(orderItem -> orderItemBuilder.buildNew(
                        NewOrderItemBuilderArgs.builder()
                                .bookId(orderItem.getBookId())
                                .quantity(orderItem.getQuantity())
                                .build()
                )).collect(Collectors.toList()))
                .build());
    }


    public Order build(final UUID id) {
        final OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new DomainException(ExceptionState.DOES_NOT_EXIST, OrderError.NOT_FOUND));
        return buildFromEntity(orderEntity);
    }

    public Page<Order> buildPageable(final PageableOrderBuilderArgs args) {
        final Page<OrderEntity> pageableOrderEntity = orderRepository.findAllByCustomerId(args.getCustomerId(), args.getPageable());
        return pageableOrderEntity.map(this::buildFromEntity);
    }

    public Page<Order> buildPageableByDateInterval(final PageableOrderByDateIntervalBuilderArgs args) {
        final Page<OrderEntity> pageableOrderEntity = orderRepository.findAllByCustomerIdAndCreatedAtBetween(args.getCustomerId(), args.getStart(), args.getEnd(), args.getPageable());
        return pageableOrderEntity.map(this::buildFromEntity);
    }

    private Order buildFromEntity(final OrderEntity orderEntity) {
        final LoadOrderDto loadOrderDto = LoadOrderDto.builder()
                .id(orderEntity.getId())
                .customer(customerOfOrderBuilder.buildFromEntity(orderEntity.getCustomer()))
                .items(orderEntity.getItems().stream().map(orderItemBuilder::buildFromEntity).collect(Collectors.toList()))
                .build();
        loadAbstractProperties(loadOrderDto, orderEntity);
        return Order.load(loadOrderDto);
    }
}
