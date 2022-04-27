package com.getir.readingisgood.domain.book.model.aggregate.value.converter;


import com.getir.readingisgood.domain.book.model.aggregate.value.Sale;
import com.getir.readingisgood.infrastructure.persistence.entity.book.SaleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleConverter implements Converter<Sale, SaleEntity> {

    @Override
    public SaleEntity convert(final Sale sale) {
        return SaleEntity.builder()
                .price(sale.getPrice())
                .quantity(sale.getQuantity())
                .build();
    }
}
