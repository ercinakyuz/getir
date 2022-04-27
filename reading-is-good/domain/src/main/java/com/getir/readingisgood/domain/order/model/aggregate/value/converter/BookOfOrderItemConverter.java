package com.getir.readingisgood.domain.order.model.aggregate.value.converter;

import com.getir.readingisgood.domain.order.model.aggregate.value.BookOfOrderItem;
import com.getir.readingisgood.infrastructure.persistence.entity.order.OrderedBookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookOfOrderItemConverter implements Converter<BookOfOrderItem, OrderedBookEntity> {

    private final MerchantOfBookOrderItemConverter merchantOfBookConverter;

    @Override
    public OrderedBookEntity convert(final BookOfOrderItem bookOfOrderItem) {
        return OrderedBookEntity.builder()
                .id(bookOfOrderItem.getId())
                .name(bookOfOrderItem.getName())
                .author(bookOfOrderItem.getAuthor())
                .price(bookOfOrderItem.getPrice())
                .merchant(merchantOfBookConverter.convert(bookOfOrderItem.getMerchant()))
                .build();
    }
}
