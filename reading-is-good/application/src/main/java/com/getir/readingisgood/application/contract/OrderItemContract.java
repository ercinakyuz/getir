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
public class OrderItemContract {

    private UUID id;

    private BookOfOrderItemContract book;

    private int quantity;

    private BigDecimal price;
}

