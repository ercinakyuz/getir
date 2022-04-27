package com.getir.readingisgood.domain.order.model.aggregate.value.builder;

import com.getir.readingisgood.domain.book.model.aggregate.Book;
import com.getir.readingisgood.domain.book.model.aggregate.builder.BookBuilder;
import com.getir.readingisgood.domain.book.model.aggregate.value.builder.MerchantOfBookBuilder;
import com.getir.readingisgood.domain.order.model.aggregate.value.BookOfOrderItem;
import com.getir.readingisgood.domain.order.model.aggregate.value.dto.LoadBookOfOrderItemDto;
import com.getir.readingisgood.infrastructure.persistence.entity.order.OrderedBookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookOfOrderItemBuilder {

    private final BookBuilder bookBuilder;

    private final MerchantOfBookBuilder merchantOfBookBuilder;

    public BookOfOrderItem buildFromEntity(final OrderedBookEntity bookEntity) {
        return BookOfOrderItem.load(LoadBookOfOrderItemDto.builder()
                .id(bookEntity.getId())
                .name(bookEntity.getName())
                .author(bookEntity.getAuthor())
                .price(bookEntity.getPrice())
                .merchant(merchantOfBookBuilder.buildFromEntity(bookEntity.getMerchant()))
                .build());
    }

    public BookOfOrderItem buildNew(final UUID bookId){
        final Book book = bookBuilder.build(bookId);
        return BookOfOrderItem.load(LoadBookOfOrderItemDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .price(book.getSale().getPrice())
                .merchant(book.getMerchant())
                .build());
    }


}
