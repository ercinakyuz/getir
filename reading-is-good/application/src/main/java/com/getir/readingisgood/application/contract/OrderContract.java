package com.getir.readingisgood.application.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderContract {

    private UUID id;

    private CustomerOfOrderContract customer;

    private List<OrderItemContract> items;

    private int quantity;

    private BigDecimal price;

}

