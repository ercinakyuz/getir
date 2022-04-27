package com.getir.readingisgood.domain.merchant.model.aggregate.builder.args;

import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuildNewMerchantArgs {
    private BuildCredentialsArgs credentials;
}

