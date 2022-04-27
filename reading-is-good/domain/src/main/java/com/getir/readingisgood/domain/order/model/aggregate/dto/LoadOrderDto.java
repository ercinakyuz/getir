package com.getir.readingisgood.domain.order.model.aggregate.dto;

import com.getir.framework.domain.model.aggregate.dto.AbstractLoadAggregateDto;
import com.getir.readingisgood.domain.order.model.aggregate.value.CustomerOfOrder;
import com.getir.readingisgood.domain.order.model.aggregate.value.OrderItem;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
public class LoadOrderDto extends AbstractLoadAggregateDto {

    private UUID id;

    private CustomerOfOrder customer;

    private List<OrderItem> items;

}
