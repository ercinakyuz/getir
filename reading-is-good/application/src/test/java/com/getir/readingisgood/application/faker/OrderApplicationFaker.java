package com.getir.readingisgood.application.faker;

import com.getir.framework.test.faker.AbstractFaker;
import com.getir.readingisgood.application.usecase.order.completeorder.command.CompleteOrderItemCommand;
import com.getir.readingisgood.infrastructure.persistence.entity.book.BookEntity;

public class OrderApplicationFaker extends AbstractFaker {

    public CompleteOrderItemCommand completeOrderItemCommand() {
        return CompleteOrderItemCommand.builder().build();
    }

    public CompleteOrderItemCommand completeOrderItemCommand(final BookEntity bookEntity) {
        return CompleteOrderItemCommand.builder()
                .bookId(bookEntity.getId())
                .quantity(faker.number().numberBetween(1, bookEntity.getSale().getQuantity()))
                .build();
    }
}
