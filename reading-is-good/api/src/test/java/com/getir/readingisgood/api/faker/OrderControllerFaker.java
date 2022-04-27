package com.getir.readingisgood.api.faker;

import com.getir.framework.test.faker.AbstractFaker;
import com.getir.readingisgood.api.event.OrderCompletedEvent;
import com.getir.readingisgood.api.model.order.request.CompleteOrderRequest;
import com.getir.readingisgood.application.faker.OrderApplicationFaker;
import com.getir.readingisgood.infrastructure.persistence.entity.book.BookEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderControllerFaker extends AbstractFaker {

    private final OrderApplicationFaker orderApplicationFaker;

    public OrderControllerFaker() {
        orderApplicationFaker = new OrderApplicationFaker();
    }

    public CompleteOrderRequest completeOrderRequest(final List<BookEntity> bookEntities) {
        return CompleteOrderRequest.builder()
                .items(bookEntities.stream().map(orderApplicationFaker::completeOrderItemCommand).collect(Collectors.toList()))
                .build();
    }
}
