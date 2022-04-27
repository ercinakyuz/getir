package com.getir.readingisgood.api.faker;

import com.getir.framework.test.faker.AbstractFaker;
import com.getir.readingisgood.api.model.book.request.ChangeBookQuantityRequest;
import com.getir.readingisgood.api.model.book.request.CreateBookRequest;
import com.getir.readingisgood.infrastructure.faker.BookInfrastructureFaker;

public class BookControllerFaker extends AbstractFaker {

    private final BookInfrastructureFaker bookInfrastructureFaker;

    public BookControllerFaker() {
        bookInfrastructureFaker = new BookInfrastructureFaker();
    }

    public CreateBookRequest createBookRequest() {
        return CreateBookRequest.builder()
                .name(bookInfrastructureFaker.name())
                .author(bookInfrastructureFaker.author())
                .price(bookInfrastructureFaker.price())
                .quantity(bookInfrastructureFaker.quantity())
                .build();
    }

    public ChangeBookQuantityRequest increaseBookQuantityRequest() {
        return ChangeBookQuantityRequest.builder()
                .quantity(bookInfrastructureFaker.quantity())
                .build();
    }

    public ChangeBookQuantityRequest decreaseBookQuantityRequest(final int currentQuantity) {
        return ChangeBookQuantityRequest.builder()
                .quantity(faker.number().numberBetween(1, currentQuantity - 1))
                .build();
    }
}
