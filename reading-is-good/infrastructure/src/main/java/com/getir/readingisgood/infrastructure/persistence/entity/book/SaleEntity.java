package com.getir.readingisgood.infrastructure.persistence.entity.book;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SaleEntity {

    private BigDecimal price;

    private int quantity;
}
