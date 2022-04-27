package com.getir.readingisgood.domain.order.model.aggregate.value.converter;

import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import com.getir.readingisgood.infrastructure.persistence.entity.order.MerchantOfOrderedBookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantOfBookOrderItemConverter implements Converter<MerchantOfBook, MerchantOfOrderedBookEntity> {

    @Override
    public MerchantOfOrderedBookEntity convert(final MerchantOfBook merchantOfBook) {
        return MerchantOfOrderedBookEntity.builder()
                .id(merchantOfBook.getId())
                .email(merchantOfBook.getEmail())
                .build();
    }
}
