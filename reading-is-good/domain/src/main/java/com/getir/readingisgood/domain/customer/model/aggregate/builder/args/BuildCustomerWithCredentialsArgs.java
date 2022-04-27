package com.getir.readingisgood.domain.customer.model.aggregate.builder.args;

import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuildCustomerWithCredentialsArgs {
    private BuildCredentialsArgs credentials;
}
