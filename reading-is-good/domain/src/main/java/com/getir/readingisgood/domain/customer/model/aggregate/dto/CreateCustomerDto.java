package com.getir.readingisgood.domain.customer.model.aggregate.dto;

import com.getir.readingisgood.domain.model.value.Credentials;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCustomerDto {
    private Credentials credential;
}
