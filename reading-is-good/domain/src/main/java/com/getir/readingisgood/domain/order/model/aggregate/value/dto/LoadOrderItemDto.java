package com.getir.readingisgood.domain.order.model.aggregate.value.dto;

import com.getir.readingisgood.domain.order.model.aggregate.value.BookOfOrderItem;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoadOrderItemDto {

    private UUID id;

    private BookOfOrderItem book;

    private int quantity;

}
