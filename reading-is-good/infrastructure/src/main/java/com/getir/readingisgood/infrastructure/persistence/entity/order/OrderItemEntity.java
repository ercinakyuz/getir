package com.getir.readingisgood.infrastructure.persistence.entity.order;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderItemEntity {

    private UUID id;

    private OrderedBookEntity book;

    private int quantity;
}
