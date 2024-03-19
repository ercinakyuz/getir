package com.getir.readingisgood.domain.book.service;

import com.getir.readingisgood.domain.book.model.aggregate.Book;
import com.getir.readingisgood.domain.book.model.aggregate.builder.BookBuilder;
import com.getir.readingisgood.domain.book.model.aggregate.unitofwork.BookOfWork;
import com.getir.readingisgood.domain.book.service.dto.DecreaseBookQuantityServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookQuantityUpdateService {

    private final BookBuilder bookBuilder;

    private final BookOfWork bookOfWork;

    public Book decrease(final DecreaseBookQuantityServiceDto serviceDto) {
        final Book book = bookBuilder.build(serviceDto.getId())
                .decreaseQuantityForOrderCompletion(serviceDto.getQuantity(), serviceDto.getCustomerId());
        bookOfWork.update(book);
        return book;
    }
}
