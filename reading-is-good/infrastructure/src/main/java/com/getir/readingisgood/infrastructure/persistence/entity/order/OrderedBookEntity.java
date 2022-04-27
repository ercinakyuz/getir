package com.getir.readingisgood.infrastructure.persistence.entity.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderedBookEntity {

    private UUID id;

    private MerchantOfOrderedBookEntity merchant;

    private String name;

    private String author;

    private BigDecimal price;
}
