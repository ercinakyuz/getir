package com.getir.readingisgood.domain.book.model.aggregate.builder;

import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.aggregate.builder.AbstractAggregateBuilder;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.book.model.aggregate.Book;
import com.getir.readingisgood.domain.book.model.aggregate.builder.args.BookBuilderWithOwnershipArgs;
import com.getir.readingisgood.domain.book.model.aggregate.builder.args.NewBookBuilderArgs;
import com.getir.readingisgood.domain.book.model.aggregate.dto.CreateBookDto;
import com.getir.readingisgood.domain.book.model.aggregate.dto.LoadBookDto;
import com.getir.readingisgood.domain.book.model.aggregate.error.BookError;
import com.getir.readingisgood.domain.book.model.aggregate.value.builder.MerchantOfBookBuilder;
import com.getir.readingisgood.domain.book.model.aggregate.value.builder.SaleBuilder;
import com.getir.readingisgood.infrastructure.persistence.entity.book.BookEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookBuilder extends AbstractAggregateBuilder {

    private final BookRepository bookRepository;

    private final MerchantOfBookBuilder merchantOfBookBuilder;

    private final SaleBuilder saleBuilder;

    public Optional<Book> buildOptional(final UUID id) {
        return bookRepository.findById(id).map(this::buildFromEntity);
    }

    public Book build(final UUID id) {
        return buildOptional(id).orElseThrow(() -> new DomainException(ExceptionState.UNPROCESSABLE, BookError.NOT_FOUND));
    }

    public Book buildWithOwnership(final BookBuilderWithOwnershipArgs args) {
        return build(args.getId()).validateOwnership(args.getMerchantId());
    }

    public Book buildNew(final NewBookBuilderArgs args) {
        return Book.create(CreateBookDto.builder()
                .merchant(merchantOfBookBuilder.build(args.getMerchantId()))
                .name(args.getName())
                .author(args.getAuthor())
                .sale(saleBuilder.build(args.getSale()))
                .build());
    }

    public Book buildFromEntity(final BookEntity bookEntity) {
        final LoadBookDto loadBookDto = LoadBookDto.builder()
                .id(bookEntity.getId())
                .name(bookEntity.getName())
                .author(bookEntity.getAuthor())
                .merchant(merchantOfBookBuilder.build(bookEntity.getMerchant().getId()))
                .sale(saleBuilder.buildFromEntity(bookEntity.getSale()))
                .build();
        loadAbstractProperties(loadBookDto, bookEntity);
        return Book.load(loadBookDto);
    }
}
