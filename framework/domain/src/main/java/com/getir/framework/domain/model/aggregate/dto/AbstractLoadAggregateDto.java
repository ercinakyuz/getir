package com.getir.framework.domain.model.aggregate.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
public class AbstractLoadAggregateDto {
    private Instant createdAt;
    private Instant modifiedAt;
    private String createdBy;
    private String modifiedBy;
}

