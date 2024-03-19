package com.getir.readingisgood.domain.order.model.aggregate;

import com.getir.framework.domain.model.aggregate.GenericAggregateRoot;
import com.getir.readingisgood.domain.order.model.aggregate.dto.CreateOrderDto;
import com.getir.readingisgood.domain.order.model.aggregate.dto.LoadOrderDto;
import com.getir.readingisgood.domain.order.model.aggregate.event.OrderCompleted;
import com.getir.readingisgood.domain.order.model.aggregate.state.OrderState;
import com.getir.readingisgood.domain.order.model.aggregate.value.CustomerOfOrder;
import com.getir.readingisgood.domain.order.model.aggregate.value.OrderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.getir.readingisgood.domain.order.model.aggregate.state.OrderState.*;
import static java.math.BigDecimal.ZERO;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Order extends GenericAggregateRoot<Order, UUID, OrderState> {

    private CustomerOfOrder customer;

    private List<OrderItem> items;

    public static Order load(final LoadOrderDto loadDto) {

        validateLoadDto(loadDto);

        final Order order = Order.builder()
                .customer(loadDto.getCustomer())
                .items(loadDto.getItems())
                .build();

        order.id = loadDto.getId();
        order.createdAt = loadDto.getCreatedAt();
        order.createdBy = loadDto.getCreatedBy();
        order.modifiedAt = loadDto.getModifiedAt();
        order.modifiedBy = loadDto.getModifiedBy();

        return order.changeState(LOADED);
    }

    public static Order create(final CreateOrderDto createDto) {

        validateCreateDto(createDto);

        final Order order = Order.builder()
                .customer(createDto.getCustomer())
                .items(createDto.getItems())
                .build();

        order.id = UUID.randomUUID();
        order.createdAt = Instant.now();
        order.createdBy = String.format("customerId: %s", order.getCustomer().getId());

        return order.changeState(CREATED);
    }

    public int getQuantity() {
        return items.stream().map(OrderItem::getQuantity).reduce(0, Integer::sum);
    }

    public BigDecimal getPrice() {
        return items.stream().map(OrderItem::getPrice).reduce(ZERO, BigDecimal::add);
    }

    public Order validateOwnership(final UUID customerId) {
        customer.validate(customerId);
        return this;
    }

    private static void validateCreateDto(final CreateOrderDto createDto) {
    }

    private static void validateLoadDto(final LoadOrderDto loadDto) {

    }

    @Override
    protected Order applyEvents() {
        switch (state) {
            case CREATED:
                addEvent(OrderCompleted.builder()
                        .id(id)
                        .build());
        }
        return this;
    }
}
