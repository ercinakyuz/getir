package com.getir.readingisgood.domain.book.model.aggregate.converter;

import com.getir.framework.domain.model.aggregate.converter.GenericAggregateConverter;
import com.getir.readingisgood.domain.book.model.aggregate.Book;
import com.getir.readingisgood.domain.book.model.aggregate.value.converter.MerchantOfBookConverter;
import com.getir.readingisgood.domain.book.model.aggregate.value.converter.SaleConverter;
import com.getir.readingisgood.infrastructure.persistence.entity.book.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookConverter extends GenericAggregateConverter<Book, BookEntity> {

    private final MerchantOfBookConverter merchantOfBookConverter;

    private final SaleConverter saleConverter;

    @Override
    public BookEntity convert(final Book book) {
        return BookEntity.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .merchant(merchantOfBookConverter.convert(book.getMerchant()))
                .sale(saleConverter.convert(book.getSale()))
                .build();
    }
}

