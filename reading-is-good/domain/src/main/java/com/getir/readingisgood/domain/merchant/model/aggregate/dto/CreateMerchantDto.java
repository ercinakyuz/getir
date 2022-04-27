package com.getir.readingisgood.domain.merchant.model.aggregate.dto;

import com.getir.readingisgood.domain.model.value.Credentials;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMerchantDto {
    private Credentials credential;
}
