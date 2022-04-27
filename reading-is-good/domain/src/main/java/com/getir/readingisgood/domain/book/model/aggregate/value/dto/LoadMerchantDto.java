package com.getir.readingisgood.domain.book.model.aggregate.value.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoadMerchantDto {

    private UUID id;

    private String email;
}
