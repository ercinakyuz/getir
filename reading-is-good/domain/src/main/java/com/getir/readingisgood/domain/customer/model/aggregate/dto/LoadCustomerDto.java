package com.getir.readingisgood.domain.customer.model.aggregate.dto;

import com.getir.framework.domain.model.aggregate.dto.AbstractLoadAggregateDto;
import com.getir.readingisgood.domain.model.value.Credentials;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class LoadCustomerDto extends AbstractLoadAggregateDto {

    private UUID id;

    private Credentials credential;
}
