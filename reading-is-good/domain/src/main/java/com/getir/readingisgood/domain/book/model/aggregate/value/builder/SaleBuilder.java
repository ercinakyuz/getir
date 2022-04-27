package com.getir.readingisgood.domain.book.model.aggregate.value.builder;

import com.getir.readingisgood.domain.book.model.aggregate.value.Sale;
import com.getir.readingisgood.domain.book.model.aggregate.value.builder.args.SaleBuilderArgs;
import com.getir.readingisgood.domain.book.model.aggregate.value.dto.LoadSaleDto;
import com.getir.readingisgood.infrastructure.persistence.entity.book.SaleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleBuilder {

    public Sale build(final SaleBuilderArgs args) {
        return Sale.load(LoadSaleDto.builder()
                .price(args.getPrice())
                .quantity(args.getQuantity())
                .build());
    }

    public Sale buildFromEntity(final SaleEntity saleEntity) {
        return Sale.load(LoadSaleDto.builder()
                .quantity(saleEntity.getQuantity())
                .price(saleEntity.getPrice())
                .build());
    }
}
