package com.getir.readingisgood.domain.book.model.aggregate.unitofwork;


import com.getir.framework.domain.unitofwork.AggregateOfWork;
import com.getir.readingisgood.domain.book.model.aggregate.Book;
import com.getir.readingisgood.domain.book.model.aggregate.converter.BookConverter;
import com.getir.readingisgood.infrastructure.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookOfWork implements AggregateOfWork<Book> {

    private final BookConverter bookConverter;

    private final BookRepository bookRepository;

    @Override
    public Book Insert(final Book aggregate) {
        bookRepository.save(bookConverter.convertAll(aggregate));
        return aggregate;
    }

    @Override
    public Book Update(final Book aggregate) {
        bookRepository.save(bookConverter.convertAll(aggregate));
        return aggregate;
    }

    @Override
    public Book Delete(final Book aggregate) {
        bookRepository.deleteById(aggregate.getId());
        return aggregate;
    }
}


