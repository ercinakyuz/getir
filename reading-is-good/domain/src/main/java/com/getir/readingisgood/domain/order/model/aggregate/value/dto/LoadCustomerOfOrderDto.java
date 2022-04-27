package com.getir.readingisgood.domain.order.model.aggregate.value.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoadCustomerOfOrderDto {

    private UUID id;

    private String email;
}
