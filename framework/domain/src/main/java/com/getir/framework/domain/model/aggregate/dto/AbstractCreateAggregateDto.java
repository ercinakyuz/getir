package com.getir.framework.domain.model.aggregate.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AbstractCreateAggregateDto {
    private String createdBy;
}
