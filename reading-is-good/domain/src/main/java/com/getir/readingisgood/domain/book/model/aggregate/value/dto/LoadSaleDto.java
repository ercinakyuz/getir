package com.getir.readingisgood.domain.book.model.aggregate.value.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LoadSaleDto {

    private BigDecimal price;

    private int quantity;
}
