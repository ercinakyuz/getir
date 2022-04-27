package com.getir.readingisgood.infrastructure.faker;

import com.getir.readingisgood.infrastructure.persistence.entity.order.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderInfrastructureFaker extends AbstractInfrastructureFaker {

    private final CredentialsInfrastructureFaker credentialsInfrastructureFaker;

    private final BookInfrastructureFaker bookInfrastructureFaker;

    public OrderInfrastructureFaker() {
        credentialsInfrastructureFaker = new CredentialsInfrastructureFaker();
        bookInfrastructureFaker = new BookInfrastructureFaker();
    }

    public List<OrderEntity> orderEntities(final UUID customerId, int count) {
        return IntStream.range(0, count).mapToObj(value -> orderEntity(customerId)).collect(Collectors.toList());
    }
    public OrderEntity orderEntity() {
        final OrderEntity orderEntity = OrderEntity.builder()
                .customer(customerOfOrderEntity())
                .items(orderItemEntities(faker.number().numberBetween(1, 10)))
                .build();
        loadAbstractPropertiesForCreation(orderEntity);
        return orderEntity;
    }
    public OrderEntity orderEntity(final UUID customerId) {
        final OrderEntity orderEntity = OrderEntity.builder()
                .customer(customerOfOrderEntity(customerId))
                .items(orderItemEntities(faker.number().numberBetween(1, 10)))
                .build();
        loadAbstractPropertiesForCreation(orderEntity);
        return orderEntity;
    }
    public CustomerOfOrderEntity customerOfOrderEntity() {
        return CustomerOfOrderEntity.builder()
                .id(id())
                .email(credentialsInfrastructureFaker.email())
                .build();
    }
    public CustomerOfOrderEntity customerOfOrderEntity(final UUID customerId) {
        return CustomerOfOrderEntity.builder()
                .id(customerId)
                .email(credentialsInfrastructureFaker.email())
                .build();
    }

    public List<OrderItemEntity> orderItemEntities(final int count) {
        return IntStream.range(0, count).mapToObj(value -> orderItemEntity()).collect(Collectors.toList());
    }

    public OrderItemEntity orderItemEntity() {
        return OrderItemEntity.builder()
                .id(id())
                .quantity(quantity())
                .book(orderedBookEntity())
                .build();
    }

    public OrderedBookEntity orderedBookEntity() {
        return OrderedBookEntity.builder()
                .id(id())
                .price(price())
                .author(bookInfrastructureFaker.author())
                .name(bookInfrastructureFaker.name())
                .merchant(merchantOfOrderedBookEntity())
                .build();
    }

    public MerchantOfOrderedBookEntity merchantOfOrderedBookEntity() {
        return MerchantOfOrderedBookEntity.builder()
                .id(id())
                .email(credentialsInfrastructureFaker.email())
                .build();
    }
}
