package com.getir.readingisgood.domain.book.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DecreaseBookQuantityServiceDto {

    private UUID id;

    private UUID customerId;

    private int quantity;

}

