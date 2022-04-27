package com.getir.readingisgood.domain.order.model.aggregate.value.dto;

import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class LoadBookOfOrderItemDto {

    private UUID id;

    private String name;

    private String author;

    private BigDecimal price;

    private MerchantOfBook merchant;

}
