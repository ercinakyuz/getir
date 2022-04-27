package com.getir.readingisgood.domain.book.model.aggregate.value.converter;

import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import com.getir.readingisgood.infrastructure.persistence.entity.book.MerchantOfBookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantOfBookConverter implements Converter<MerchantOfBook, MerchantOfBookEntity> {

    @Override
    public MerchantOfBookEntity convert(final MerchantOfBook merchantOfBook) {
        return MerchantOfBookEntity.builder()
                .id(merchantOfBook.getId())
                .build();
    }
}
