package com.getir.readingisgood.application.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookOfOrderItemContract {

    private UUID id;

    private MerchantOfBookOrderItemContract merchant;

    private String name;

    private String author;

    private BigDecimal price;
}
