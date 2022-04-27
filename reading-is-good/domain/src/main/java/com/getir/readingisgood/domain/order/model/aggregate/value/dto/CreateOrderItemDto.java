package com.getir.readingisgood.domain.order.model.aggregate.value.dto;

import com.getir.readingisgood.domain.order.model.aggregate.value.BookOfOrderItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderItemDto {

    private BookOfOrderItem book;

    private int quantity;
}
