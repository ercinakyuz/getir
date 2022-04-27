package com.getir.readingisgood.domain.order.model.aggregate.dto;

import com.getir.readingisgood.domain.order.model.aggregate.value.CustomerOfOrder;
import com.getir.readingisgood.domain.order.model.aggregate.value.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateOrderDto {

    private CustomerOfOrder customer;

    private List<OrderItem> items;
}
